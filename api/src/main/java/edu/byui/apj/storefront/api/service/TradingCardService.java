package edu.byui.apj.storefront.api.service;

import ch.qos.logback.classic.spi.ConfiguratorRank;
import edu.byui.apj.storefront.api.model.TradingCard;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.InputStreamReader;
import java.io.Reader;
import org.springframework.core.io.ClassPathResource;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;


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

    public List<TradingCard> GetTradingCards(int page, int size){
        return tradingCards.stream()
                .skip(page)
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<TradingCard> GetFilteredTradingCards(BigDecimal minPrice, BigDecimal maxPrice, String specialty, String sort){

    }

        
}
