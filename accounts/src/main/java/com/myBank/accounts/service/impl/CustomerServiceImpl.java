package com.myBank.accounts.service.impl;

import com.myBank.accounts.dto.AccountsDto;
import com.myBank.accounts.dto.CardsDto;
import com.myBank.accounts.dto.CustomerDetailsDto;
import com.myBank.accounts.dto.LoansDto;
import com.myBank.accounts.entity.Accounts;
import com.myBank.accounts.entity.Customer;
import com.myBank.accounts.exception.ResourceNotFoundException;
import com.myBank.accounts.mapper.AccountsMapper;
import com.myBank.accounts.mapper.CustomerMapper;
import com.myBank.accounts.repository.AccountsRepository;
import com.myBank.accounts.repository.CustomerRepository;
import com.myBank.accounts.service.ICustomersService;
import com.myBank.accounts.service.client.CardsFeignClient;
import com.myBank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
        );
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if (null != loansDtoResponseEntity)
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if (null != cardsDtoResponseEntity)
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
