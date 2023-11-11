package com.bhupi.spring6restmvc.controller;

import com.bhupi.spring6restmvc.model.Customer;
import com.bhupi.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
        log.debug("Get Customer by Id  - In Controller");
        return customerService.getCustomerById(customerId);
    }
}
