package com.app.veterinaria.dto;

import com.app.veterinaria.entity.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class NewUserRequest extends EntityRequest{
    public NewUserRequest(User user) {
        super(user.getUsername(), "", "", user.getPassword(),
                user.getEmail(), user.getRole().toString(), user.getActive(),
                user.getCreatedAt()
        );
    }
}
