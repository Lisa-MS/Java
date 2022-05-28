package com.chnu.phone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Utility {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utility_generator")
    @SequenceGenerator(name = "utility_generator", sequenceName = "utility_seq", allocationSize = 50)
    //@Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(unique = true)
    private String title;
}
