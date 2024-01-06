package com.bhupi.spring6restmvc.services;

import com.bhupi.spring6restmvc.model.BeerDTO;
import com.bhupi.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("KARHU")
                .beerStyle(BeerStyle.ALE)
                .upc("123456")
                .price(new BigDecimal("4.35"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Parlenbacher")
                .beerStyle(BeerStyle.LAGER)
                .upc("7890")
                .price(new BigDecimal("5.15"))
                .quantityOnHand(200)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Heinkein")
                .beerStyle(BeerStyle.PILSNER)
                .upc("87654")
                .price(new BigDecimal("2.75"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<BeerDTO> listBeers(String beerName) {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Get Beer By ID - In Service. Id : " + id.toString());
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(2)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existingBeer = beerMap.get(beerId);
        existingBeer.setBeerName(beer.getBeerName());
        existingBeer.setVersion(beer.getVersion());
        existingBeer.setBeerStyle(beer.getBeerStyle());
        existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        existingBeer.setUpc(beer.getUpc());
        existingBeer.setPrice(beer.getPrice());
        return Optional.of(existingBeer);
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        beerMap.remove(beerId);
        return true;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existingBeer = beerMap.get(beerId);

        if(StringUtils.hasText(beer.getBeerName())) {
            existingBeer.setBeerName(beer.getBeerName());
        }

        if(beer.getBeerStyle() != null) {
            existingBeer.setBeerStyle(beer.getBeerStyle());
        }

        if(beer.getPrice() != null) {
            existingBeer.setPrice(beer.getPrice());
        }

        if(beer.getQuantityOnHand() != null) {
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if(StringUtils.hasText(beer.getUpc())) {
            existingBeer.setUpc(beer.getUpc());
        }
        return Optional.of(existingBeer);
    }

}
