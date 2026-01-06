package com.app.veterinaria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewOwnerRequest extends EntityRequest{
    private String phoneNumber;
    private String address;
}
