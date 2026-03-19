package edu.hike.idde.myproject.spring.dao;

import edu.hike.idde.myproject.spring.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDao {

    User findById(Long id);

    User findByEmail(String email);

    User mapRow(ResultSet rs) throws SQLException;

    User create(User user);
}
