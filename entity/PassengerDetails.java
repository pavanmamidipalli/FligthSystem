package com.passenger.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "passenger_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDetails {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile_no")
    private String mobileNo;

}
