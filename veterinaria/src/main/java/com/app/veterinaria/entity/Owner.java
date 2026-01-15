package com.app.veterinaria.entity;

import com.app.veterinaria.dto.NewOwnerRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String address;

    public Owner(NewOwnerRequest newOwner, User user) {
        this.user = user;
        this.name = newOwner.getFirstName();
        this.lastName = newOwner.getLastName();
        this.phoneNumber = newOwner.getPhoneNumber();
        this.address = newOwner.getAddress();
    }

    public void updateOwner(Owner owner) {
        if (owner.getName() != null) this.name = owner.getName();
        if (owner.getLastName() != null) this.lastName = owner.getLastName();
        if (owner.getPhoneNumber() != null) this.phoneNumber = owner.getPhoneNumber();
        if (owner.getAddress() != null) this.address = owner.getAddress();
    }
}
