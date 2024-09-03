package com.passenger.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDetailsFindRequest {
    @NotNull(message = "id cannot be empty")
    private Integer id;

    @NotEmpty(message = "name cannot be empty")
    @Size(max = 200, message = "name length cannot be more than 200 chars")
    private String name;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "invalid email format")
    @Size(max = 200, message = "name length cannot be more than 200 chars")
    private String email;

    @NotEmpty(message = "address cannot be empty")
    @Size(max = 2000, message = "name length cannot be more than 2000 chars")
    private String address;

    @NotEmpty(message = "mobile number cannot be empty")
    @Size(max = 45, message = "name length cannot be more than 45 chars")
    private String mobileNo;
}
