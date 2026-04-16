package com.yahoo_fin.scrapper.asset.crypto;

import com.yahoo_fin.scrapper.types.MonetaryValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
class CryptoRestController {
    private final CryptoRepository cryptoRepository;

    public CryptoRestController(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
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
                    new MonetaryValue(request.getPrice()),
                    request.getCurrency(),
                    LocalDateTime.now()
            );
            cryptoRepository.save(crypto);
            return new ResponseEntity<>(crypto, HttpStatus.CREATED);
        }
        Crypto existingCrypto = existing.get();
        existingCrypto.setPrice(new MonetaryValue(request.getPrice()));
        existingCrypto.setLastUpdated(LocalDateTime.now());
        existingCrypto = cryptoRepository.save(existingCrypto);
        return new ResponseEntity<>(existingCrypto, HttpStatus.OK);
    }

    private Optional<Crypto> getCryptoByName(String name) {
        Crypto crypto = cryptoRepository.findByNameEqualsIgnoreCase(name);
        if (crypto == null) return Optional.empty();
        return Optional.of(crypto);
    }
}
