package com.sergey.zhuravlev.social.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "first_line", length = 255)
    private String firstLine;

    @Column(name = "second_line", length = 255)
    private String secondLine;

    @Column(name = "city", length = 180)
    private String city;

    @Column(name = "country", length = 2)
    private String country;

    @Column(name = "zip_code", length = 18)
    private String zipCode;

}
