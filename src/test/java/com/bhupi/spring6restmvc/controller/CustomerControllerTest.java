package com.bhupi.spring6restmvc.controller;

import com.bhupi.spring6restmvc.model.Customer;
import com.bhupi.spring6restmvc.services.CustomerService;
import com.bhupi.spring6restmvc.services.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper customerMapper;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }


    @Test
    void getCustomerById() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.getCustomerById(testCustomer.getCustomerId())).willReturn(testCustomer);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomer.getCustomerId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId", is(testCustomer.getCustomerId().toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));

    }

    @Test
    void getCustomerByIdNotFound() throws Exception {

        given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());

    }

    @Test
    void getAllCustomers() throws Exception {
        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }


    @Test
    void testCreateNewCustomer() throws Exception  {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);
        customer.setCustomerId(null);

        given(customerService.saveNewCustomer(any(Customer.class))).willReturn(customerServiceImpl.getAllCustomers().get(1));

        mockMvc.perform( post(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(customerMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateCustomerById() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customer.getCustomerId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class), any(Customer.class));

    }

    @Test
    void testDeleteCustomerById() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customer.getCustomerId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());

        assertThat(customer.getCustomerId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testPatchCustomerById() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Name");

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customer.getCustomerId())
                    .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerMapper.writeValueAsString(customerMap)))
                    .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertThat(customer.getCustomerId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(customerMap.get("customerName")).isEqualTo(customerArgumentCaptor.getValue().getCustomerName());
    }

}