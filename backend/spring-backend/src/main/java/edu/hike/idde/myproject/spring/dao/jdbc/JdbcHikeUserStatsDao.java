package edu.hike.idde.myproject.spring.dao.jdbc;

import edu.hike.idde.myproject.spring.dao.HikeStatsDao;
import edu.hike.idde.myproject.spring.dao.exception.IdNotFoundException;
import edu.hike.idde.myproject.spring.dao.exception.RepositoryException;
import edu.hike.idde.myproject.spring.model.HikeUserStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class JdbcHikeUserStatsDao implements HikeStatsDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public HikeUserStats findByUserAndHikeId(Long userId, Long hikeId) {
        String sql = "SELECT * FROM hike_user_stats WHERE user_id = ? AND hike_id = ?";

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, hikeId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                HikeUserStats defaultStats = new HikeUserStats();
                defaultStats.setUserId(userId);
                defaultStats.setHikeId(hikeId);
                defaultStats.setFavorite(false);
                defaultStats.setTimesCompleted(0);
                return defaultStats;
            }

            return mapRow(rs);
        } catch (SQLException e) {
            throw new RepositoryException("Error reading hike stats", e);
        }
    }

    @Override
    public HikeUserStats create(HikeUserStats stats) {
        String sql = "INSERT INTO hike_user_stats (user_id, hike_id, favorite, times_completed) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, stats.getUserId());
            ps.setLong(2, stats.getHikeId());
            ps.setBoolean(3, stats.isFavorite());
            ps.setInt(4, stats.getTimesCompleted());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                stats.setId(keys.getLong(1));
            }

            return stats;

        } catch (SQLException e) {
            throw new RepositoryException("Error creating hike stats", e);
        }
    }

    @Override
    public void update(HikeUserStats stats) {
        String sql = "UPDATE hike_user_stats SET favorite = ?, times_completed = ? WHERE user_id = ? AND hike_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, stats.isFavorite());
            ps.setInt(2, stats.getTimesCompleted());
            ps.setLong(3, stats.getUserId());
            ps.setLong(4, stats.getHikeId());

            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new IdNotFoundException("Stats not found for hike id " + stats.getHikeId());
            }

        } catch (SQLException e) {
            throw new RepositoryException("Error updating hike stats", e);
        }

    }

    @Override
    public void deleteByHikeId(Long hikeId) {
        String sql = "DELETE FROM hike_user_stats WHERE hike_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, hikeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Error deleting hike stats", e);
        }
    }

    private HikeUserStats mapRow(ResultSet rs) throws SQLException {
        HikeUserStats stats = new HikeUserStats();
        stats.setId(rs.getLong("id"));
        stats.setUserId(rs.getLong("user_id"));
        stats.setHikeId(rs.getLong("hike_id"));
        stats.setFavorite(rs.getBoolean("favorite"));
        stats.setTimesCompleted(rs.getInt("times_completed"));
        return stats;
    }
}
