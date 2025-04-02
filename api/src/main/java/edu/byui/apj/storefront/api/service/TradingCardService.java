package edu.byui.apj.storefront.api.service;

import ch.qos.logback.classic.spi.ConfiguratorRank;
import edu.byui.apj.storefront.api.model.TradingCard;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.io.InputStreamReader;
import java.io.Reader;
import org.springframework.core.io.ClassPathResource;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;
import org.springframework.stereotype.Service;

@Service
public class TradingCardService {
    private final List<TradingCard> tradingCards = new ArrayList<>();

    public TradingCardService() {
        loadTradingCardFromCsv();
    }

    //ID,Name,Specialty,Contribution,Price,ImageUrl

    public void loadTradingCardFromCsv() {
        try {
            ClassPathResource resource = new ClassPathResource("pioneers.csv");
            Reader reader = new InputStreamReader(resource.getInputStream());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            for (CSVRecord record : records) {
                long id = Long.parseLong(record.get("ID"));
                String name = record.get("Name");
                String specialty = record.get("Specialty");
                String contribution = record.get("Contribution");
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(record.get("Price")));
                String ImageUrl = record.get("ImageUrl");
                tradingCards.add(new TradingCard(id, name, specialty, contribution, price, ImageUrl));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    } // Start from here: Create a method that sorts and filters based on parameters passed in and returns a list of TradingCards

    public List<TradingCard> getAllTradingCards(){
        return tradingCards;
    }

    public List<TradingCard> getTradingCardsBy (int page, int size){
        return tradingCards.stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<TradingCard> getFilteredTradingCards(BigDecimal minPrice, BigDecimal maxPrice, String specialty, String sort){
        List<TradingCard> filtered = tradingCards.stream()
                .filter(card -> { // get all cards through filter
                    boolean matches = true;
                    if (minPrice != null) {
                        matches &= card.getPrice().compareTo(minPrice) >= 0; // match &= condition -> matches = matches & condition;
                    }
                    if (maxPrice != null) {
                        matches &= card.getPrice().compareTo(maxPrice) <= 0;
                    }
                    if (specialty != null && !specialty.isBlank()) {
                        matches &= card.getSpecialty().equalsIgnoreCase(specialty); //.equalsIgnoreCase() -- Compares this String to another String, ignoring case considerations
                    }
                    return matches;
                })
                .toList();

        if (sort != null && !sort.isBlank()){ // using Comparators to sort!!! Crazy!! sort is either name or price
            if (sort.equalsIgnoreCase("name")){
                filtered.sort(Comparator.comparing(TradingCard::getName, String.CASE_INSENSITIVE_ORDER));
            }else if (sort.equalsIgnoreCase("price")){
                filtered.sort(Comparator.comparing(TradingCard::getPrice));
            }
        }
        return filtered;
    }

    public List<TradingCard> searchTradingCards(String query){
        String lowerQuery = query.toLowerCase();
        return tradingCards.stream()
                .filter(card -> card.getName().toLowerCase().contains(lowerQuery) || card.getContribution().toLowerCase().contains(lowerQuery))
                .toList();
    }
}
