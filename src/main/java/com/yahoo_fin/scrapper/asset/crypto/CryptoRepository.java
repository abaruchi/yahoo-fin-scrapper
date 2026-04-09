package com.yahoo_fin.scrapper.asset.crypto;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CryptoRepository extends JpaRepository<Crypto, Long> {
    Crypto findByNameEqualsIgnoreCase(String name);
}
