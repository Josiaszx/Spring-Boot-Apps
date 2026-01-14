package com.app.veterinaria.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewOwnerRequest extends EntityRequest{
    private String phoneNumber;
    private String address;
}
