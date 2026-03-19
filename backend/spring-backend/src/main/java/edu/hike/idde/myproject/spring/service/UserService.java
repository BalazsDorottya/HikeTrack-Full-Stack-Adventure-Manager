package edu.hike.idde.myproject.spring.service;

import edu.hike.idde.myproject.spring.model.User;

public interface UserService {

    User register(String firstName, String lastName, String email, String rawPassword);

    User authenticate(String email, String rawPassword);

    User getById(Long id);
}
