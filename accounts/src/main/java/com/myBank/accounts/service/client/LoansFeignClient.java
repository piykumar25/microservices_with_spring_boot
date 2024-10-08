package com.myBank.accounts.service.client;

import com.myBank.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("mybank-correlation-id")
                                                         String correlationId,
                                                     @RequestParam String mobileNumber);
}
