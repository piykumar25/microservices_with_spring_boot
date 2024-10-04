package com.myBank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Cards and Loans information"
)
public class CustomerDetailsDto {
    @Schema(
            name = "name",
            description = "Name of the customer",
            example = "Piyush Kumar"
    )
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 5, max = 30, message = "The length of customer name should be between 5 and 30 characters")
    private String name;

    @Schema(
            name = "email",
            description = "Email address of the customer",
            example = "W9RQp@example.com"
    )
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email address should be valid")
    private String email;

    @Schema(
            name = "mobileNumber",
            description = "Mobile number of the customer",
            example = "1234567890"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
    private String mobileNumber;

    @Schema(
            name = "accountsDto",
            description = "Account details of the customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            name = "loansDto",
            description = "Loan details of the customer"
    )
    private LoansDto loansDto;

    @Schema(
            name = "cardsDto",
            description = "Card details of the customer"
    )
    private CardsDto cardsDto;



}
