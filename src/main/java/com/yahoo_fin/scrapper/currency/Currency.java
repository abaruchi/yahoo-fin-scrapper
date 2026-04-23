package com.yahoo_fin.scrapper.currency;

import com.yahoo_fin.scrapper.market.Country;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Table(
        name = "currency"
)
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "currency")
    private List<USDExchangeRate> exchangeRates;

    public Currency() {}

    public Currency(String name, String code, Country country) {
        this.name = name;
        this.code = code;
        this.country = country;
    }

    public void addExchangeRate(USDExchangeRate exchangeRate) {
        exchangeRates.add(exchangeRate);
    }
    public void removeExchangeRate(USDExchangeRate exchangeRate) {
        exchangeRates.remove(exchangeRate);
    }
    public void setCurrency(String currency) {
        this.code = currency;
    }

}
