package edu.hike.idde.myproject.spring.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
}
