package com.bhupi.spring6restmvc.bootstrap;

import com.bhupi.spring6restmvc.entities.Beer;
import com.bhupi.spring6restmvc.entities.Customer;
import com.bhupi.spring6restmvc.model.BeerStyle;
import com.bhupi.spring6restmvc.model.CustomerDTO;
import com.bhupi.spring6restmvc.repositories.BeerRepository;
import com.bhupi.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData() {

        if(beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("KARHU")
                    .beerStyle(BeerStyle.ALE)
                    .upc("123456")
                    .price(new BigDecimal("4.35"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Parlenbacher")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("7890")
                    .price(new BigDecimal("5.15"))
                    .quantityOnHand(200)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Heinkein")
                    .beerStyle(BeerStyle.PILSNER)
                    .upc("87654")
                    .price(new BigDecimal("2.75"))
                    .quantityOnHand(100)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }

    }

    private void loadCustomerData() {

        if(customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .customerName("Bhupendra Singh")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Abhijeet Rawal")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Sachin Patil")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
        }
    }
}