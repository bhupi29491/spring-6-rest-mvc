package com.bhupi.spring6restmvc.controller;

import com.bhupi.spring6restmvc.entities.Customer;
import com.bhupi.spring6restmvc.mappers.CustomerMapper;
import com.bhupi.spring6restmvc.model.CustomerDTO;
import com.bhupi.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    CustomerMapper customerMapper;

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

    @Transactional
    @Rollback
    @Test
    void saveNewCustomerTest() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("Neeraj")
                .build();

        ResponseEntity responseEntity = customerController.saveCustomer(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));

        String[] customerLocationId = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedCustomerUUID = UUID.fromString(customerLocationId[4]);

        Customer customer = customerRepository.findById(savedCustomerUUID).get();
        assertThat(customer).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void updateCustomerByIdTest() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setCustomerId(null);
        final String customerName = "Neeraj";
        customerDTO.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.updateCustomerById(customer.getCustomerId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getCustomerId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
    }

    @Test
    void testUpdateCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void testDeleteByIdCustomer() {
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getCustomerId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customer.getCustomerId())).isEmpty();
    }

    @Test
    void testDeleteByIdCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.deleteCustomerById(UUID.randomUUID()));
    }

    @Transactional
    @Rollback
    @Test
    void patchCustomerByIdTest() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setCustomerId(null);
        final String customerName = "Neeraj";
        customerDTO.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.patchCustomerById(customer.getCustomerId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getCustomerId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
    }

    @Test
    void patchCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.patchCustomerById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }
}