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
}
