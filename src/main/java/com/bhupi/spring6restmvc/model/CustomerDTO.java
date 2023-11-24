package com.bhupi.spring6restmvc.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {
    private UUID customerId;
    private String customerName;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
