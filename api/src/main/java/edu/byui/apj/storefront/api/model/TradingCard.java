package edu.byui.apj.storefront.api.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradingCard {
    private Long id;
    private String name;
    private String specialty;
    private String contribution;
    private BigDecimal price;
    private String imageUrl;
}
