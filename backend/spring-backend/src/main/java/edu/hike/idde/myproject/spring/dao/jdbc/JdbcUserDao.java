package edu.hike.idde.myproject.spring.dao.jdbc;

import edu.hike.idde.myproject.spring.dao.UserDao;
import edu.hike.idde.myproject.spring.dao.exception.IdNotFoundException;
import edu.hike.idde.myproject.spring.dao.exception.RepositoryException;
import edu.hike.idde.myproject.spring.model.Role;
import edu.hike.idde.myproject.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class JdbcUserDao implements UserDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new IdNotFoundException("User not found with id" + id);
            }

            return mapRow(rs);
        } catch (SQLException e) {
            throw new RepositoryException("Error reading user data", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return mapRow(rs);
        } catch (SQLException e) {
            throw new RepositoryException("Error reading user by email", e);
        }
    }

    @Override
    public User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setFirstName(rs.getString("first_name"));
        u.setLastName(rs.getString("last_name"));
        u.setRole(Role.valueOf(rs.getString("role")));
        return u;
    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (email, password, first_name, last_name, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getRole().name());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getLong(1));
            }
            return user;
        } catch (SQLException e) {
            throw new RepositoryException("Error creating user", e);
        }
    }
}
