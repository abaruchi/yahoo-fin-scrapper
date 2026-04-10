package com.yahoo_fin.scrapper.asset.crypto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoPriceRepository  extends JpaRepository<CryptoPrice, Long> {
    CryptoPrice findByCryptoAndTimestamp(Crypto crypto, java.time.LocalDateTime timestamp);
    CryptoPrice findByCrypto(Crypto crypto);
    CryptoPrice findByCryptoAndTimestampLessThanEqualOrderByTimestampDesc(Crypto crypto, java.time.LocalDateTime timestamp);
}
