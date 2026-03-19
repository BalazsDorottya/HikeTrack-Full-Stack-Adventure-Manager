package edu.hike.idde.myproject.spring.dao.jdbc;

import edu.hike.idde.myproject.spring.dao.HikesDao;
import edu.hike.idde.myproject.spring.dao.exception.IdNotFoundException;
import edu.hike.idde.myproject.spring.dao.exception.RepositoryException;
import edu.hike.idde.myproject.spring.model.Hike;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@Slf4j
@Profile("jdbc")
public class HikesDaoJdbc implements HikesDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public Hike read(Long id) throws IdNotFoundException {
        String sql = "SELECT * FROM hikes WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToHike(rs);
            } else {
                log.error("Id not found");
                throw new IdNotFoundException();
            }
        } catch (SQLException e) {
            log.error("Database error during read()", e);
            throw new RepositoryException("Error reading hike from database", e);
        }
    }

    @Override
    public Collection<Hike> readAll() {
        String sql = "SELECT * FROM hikes";
        List<Hike> hikes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                hikes.add(mapResultSetToHike(rs));
            }
        } catch (SQLException e) {
            log.error("Error reading all hikes", e);
            throw new RepositoryException("Error reading all hikes", e);
        }


        return hikes;
    }

    @Override
    public Hike create(Hike hike) {
        String sql = "INSERT INTO hikes (nameOfTrail, startLocation,"
                + "startTime, price, lengthOfTrail) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, hike.getNameOfTrail());
            stmt.setString(2, hike.getStartLocation());
            stmt.setTimestamp(3, Timestamp.from(hike.getStartTime()));
            stmt.setDouble(4, hike.getPrice());
            stmt.setDouble(5, hike.getLengthOfTrail());
            stmt.executeUpdate();

            try (ResultSet key = stmt.getGeneratedKeys()) {
                if (key.next()) {
                    hike.setId(key.getLong(1));
                }
            }

            return hike;
        } catch (SQLException e) {
            log.error("Error creating hike {}", hike, e);
            throw new RepositoryException("Error creating hike", e);
        }
    }

    @Override
    public void update(Hike hike) throws IdNotFoundException {
        String sql = "UPDATE hikes SET nameOfTrail=?, startLocation=?,"
                + "startTime=?, price=?, lengthOfTrail=? WHERE id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hike.getNameOfTrail());
            stmt.setString(2, hike.getStartLocation());
            stmt.setTimestamp(3, Timestamp.from(hike.getStartTime()));
            stmt.setDouble(4, hike.getPrice());
            stmt.setDouble(5, hike.getLengthOfTrail());
            stmt.setLong(6, hike.getId());

            int updated = stmt.executeUpdate();
            if (updated == 0) {
                log.error("Id not found for update");
                throw new IdNotFoundException();
            }

        } catch (SQLException e) {
            log.error("Error updating hike {}", hike, e);
            throw new RepositoryException("Error updating hike", e);
        }
    }

    @Override
    public void delete(Long id) throws IdNotFoundException {
        String sql = "DELETE FROM hikes WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int deleted = stmt.executeUpdate();
            if (deleted == 0) {
                throw new IdNotFoundException();
            }

        } catch (SQLException e) {
            log.error("Error deleting hike with id {}", id, e);
            throw new RepositoryException("Error deleting hike", e);
        }
    }

    private Hike mapResultSetToHike(ResultSet rs) throws SQLException {
        Hike hike = new Hike();
        hike.setId(rs.getLong("id"));
        hike.setNameOfTrail(rs.getString("nameOfTrail"));
        hike.setStartLocation(rs.getString("startLocation"));

        Timestamp ts = rs.getTimestamp("startTime");
        if (ts != null) {
            hike.setStartTime(ts.toInstant());
        }

        hike.setPrice(rs.getDouble("price"));
        hike.setLengthOfTrail(rs.getDouble("lengthOfTrail"));
        return hike;
    }

    @Override
    public Collection<Hike> getHikesByName(String name) {
        String sql = "SELECT * FROM hikes WHERE nameOfTrail LIKE ?";
        List<Hike> hikes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hikes.add(mapResultSetToHike(rs));
            }

        } catch (SQLException e) {
            log.error("Server couldn`t fetch hikes by name: ", e);
            throw new RepositoryException("Error fetching hikes by name", e);
        }

        return hikes;
    }

    @Override
    public Collection<Hike> getHikesByLocation(String startLocation) {
        String sql = "SELECT * FROM hikes WHERE startLocation LIKE ?";
        List<Hike> hikes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + startLocation + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hikes.add(mapResultSetToHike(rs));
            }

        } catch (SQLException e) {
            log.error("Server couldn`t fetch hikes by location: ", e);
            throw new RepositoryException("Error fetching hikes by location", e);
        }

        return hikes;
    }

    @Override
    public Collection<Hike> getHikesByFilter(String name, String location) {
        StringBuilder sql = new StringBuilder("SELECT * FROM hikes WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            sql.append(" AND nameOfTrail LIKE ?");
            params.add("%" + name + "%");
        }

        if (location != null && !location.isEmpty()) {
            sql.append(" AND startLocation LIKE ?");
            params.add("%" + location + "%");
        }

        List<Hike> hikes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hikes.add(mapResultSetToHike(rs));
            }
        } catch (SQLException e) {
            log.error("Error filtering hikes", e);
            throw new RepositoryException("Error filtering hikes", e);
        }
        return hikes;
    }

}
