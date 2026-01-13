package com.app.veterinaria.dto;

import java.time.LocalDate;

public interface AnyEntityRequest {

    public String getUsername();
    public String getPassword();
    public String getEmail();
    public String getFirstName();
    public String getLastName();
    public String getRole();
    public Boolean getActive();
    public LocalDate getCreatedAt();

}
