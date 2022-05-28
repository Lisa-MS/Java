package com.chnu.phone.entity;

import com.chnu.phone.entity.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator", sequenceName = "user_seq", allocationSize=50)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String identifier;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToMany
    private Set<Utility> utilities;

}
