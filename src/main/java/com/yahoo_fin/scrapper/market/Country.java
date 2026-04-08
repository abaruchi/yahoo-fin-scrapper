package com.yahoo_fin.scrapper.market;


import com.yahoo_fin.scrapper.currency.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Market> markets = new ArrayList<>();

    @OneToOne(mappedBy = "country", cascade = CascadeType.ALL)
    private Currency currency;
}
