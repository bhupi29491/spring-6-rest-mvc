package com.bhupi.spring6restmvc.mappers;

import com.bhupi.spring6restmvc.entities.Customer;
import com.bhupi.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
