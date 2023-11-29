package com.bhupi.spring6restmvc.controller;

import com.bhupi.spring6restmvc.entities.Customer;
import com.bhupi.spring6restmvc.model.CustomerDTO;
import com.bhupi.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testGetAllCustomers() {
        List<CustomerDTO> allCustomers = customerController.getAllCustomers();

        assertThat(allCustomers.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyCustomerList() {
        customerRepository.deleteAll();

        List<CustomerDTO> allCustomers = customerController.getAllCustomers();

        assertThat(allCustomers.size()).isEqualTo(0);
    }

    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerController.getCustomerById(customer.getCustomerId());

        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }
}