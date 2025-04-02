package edu.byui.apj.storefront.api.controller;

import edu.byui.apj.storefront.api.model.TradingCard;
import edu.byui.apj.storefront.api.service.TradingCardService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("api/cards")
public class TradingCardController {
    private final TradingCardService tradingCardService;

    @Autowired
    public TradingCardController (TradingCardService tradingCardService){
        this.tradingCardService = tradingCardService;
    }

    @GetMapping
    public List<TradingCard> getTradingCards( // This parameter syntax is so cool!!
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return tradingCardService.getTradingCardsBy(page, size);

    }
    
    @GetMapping("/filter")
    public List<TradingCard> getFilteredTradingCards(
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "specialty", required = false) String specialty,
            @RequestParam(value = "sort", required = false) String sort){
        return tradingCardService.getFilteredTradingCards(minPrice, maxPrice, specialty, sort);
    }

    @GetMapping("/search")
    public List<TradingCard> searchTradingCards(@RequestParam("query") String query){
        return tradingCardService.searchTradingCards(query);
    }


}
