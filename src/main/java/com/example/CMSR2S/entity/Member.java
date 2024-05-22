package com.example.CMSR2S.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username", length = 64, unique = true)
    private String username;

    @Column(name = "password", length = 256)
    private String password;

    @Column(name = "first_name", length = 64)
    private String firstName;

    @Column(name = "last_name", length = 256)
    private String lastName;

    @Column(name = "email", length = 128)
    private String email;

    @Column(name = "phone", length = 12)
    private String phone;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    private List<Content> contents;
}
