package com.yahoo_fin.scrapper.market;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "market"
)
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private LocalDateTime open_at;
    private LocalDateTime close_at;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
