package com.bhupi.spring6restmvc.repositories;

import com.bhupi.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveNewCustomer() {
        Customer customer = customerRepository.save(Customer.builder().customerName("Yuvraj Singh").build());

        assertThat(customer.getCustomerId()).isNotNull();
    }
}