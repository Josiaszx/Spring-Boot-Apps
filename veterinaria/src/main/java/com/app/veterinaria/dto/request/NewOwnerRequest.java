package com.app.veterinaria.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewOwnerRequest extends EntityRequest {
    private String phoneNumber;
    private String address;
}
