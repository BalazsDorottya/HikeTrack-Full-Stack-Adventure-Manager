package edu.hike.idde.myproject.spring.service.implementation;

import edu.hike.idde.myproject.spring.controller.exception.BadRequestException;
import edu.hike.idde.myproject.spring.dao.UserDao;
import edu.hike.idde.myproject.spring.model.Role;
import edu.hike.idde.myproject.spring.model.User;
import edu.hike.idde.myproject.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserDao userDao;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User register(String firstName, String lastName, String email, String rawPassword) {
        String hashed = passwordEncoder.encode(rawPassword);
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(hashed);
        user.setRole(Role.USER);
        return userDao.create(user);
    }

    @Override
    public User authenticate(String email, String rawPassword) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new BadRequestException("Invalid credentials");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }
        return user;
    }

    @Override
    public User getById(Long id) {
        return userDao.findById(id);
    }
}
