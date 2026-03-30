package com.yahoo_fin.scrapper.crypto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
class CryptoRestController {
    private final CryptoRepository cryptoRepository;

    public CryptoRestController(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
        populateCrypto();
    }

    @RequestMapping(value = "/crypto", method = RequestMethod.GET)
    Iterable<Crypto> getCrypto() {
        return cryptoRepository.findAll();
    }

    @RequestMapping(value = "/crypto", method = RequestMethod.POST)
    ResponseEntity<Crypto> addCrypto(@RequestBody CryptoRequest request) {
        Optional<Crypto> existing = getCryptoByName(request.getName());
        if (existing.isEmpty()) {
            Crypto crypto = new Crypto(
                    request.getName(),
                    request.getPrice(),
                    request.getCurrency()
            );
            cryptoRepository.save(crypto);
            return new ResponseEntity<>(crypto, HttpStatus.CREATED);
        }
        String[] parts = request.getPrice().split("\\.");
        int price_integer = Integer.parseInt(parts[0]);
        int price_decimal = Integer.parseInt(parts[1]);

        Crypto existingCrypto = existing.get();
        existingCrypto.setPrice(price_integer, price_decimal);
        existingCrypto.setLastUpdated(Instant.now());
        existingCrypto = cryptoRepository.save(existingCrypto);
        return new ResponseEntity<>(existingCrypto, HttpStatus.OK);
    }

    private Optional<Crypto> getCryptoByName(String name) {
        Crypto crypto = cryptoRepository.findByNameEqualsIgnoreCase(name);
        if (crypto == null) return Optional.empty();
        return Optional.of(crypto);
    }

    private void populateCrypto() {
        Crypto bitcoin = new Crypto("Bitcoin", 10000, 0, "USD");
        Crypto ethereum = new Crypto("Ethereum", 1000, 0, "USD");

        if (cryptoRepository.findByNameEqualsIgnoreCase(bitcoin.getName()) == null) {
            cryptoRepository.save(bitcoin);
        }
        if (cryptoRepository.findByNameEqualsIgnoreCase(ethereum.getName()) == null) {
            cryptoRepository.save(ethereum);
        }
    }
}
