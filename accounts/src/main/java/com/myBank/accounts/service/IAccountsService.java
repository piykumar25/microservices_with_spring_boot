package com.myBank.accounts.service;


import com.myBank.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * A description of the entire Java function.
     *
     * @param  customerDto description of parameter
     */
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
