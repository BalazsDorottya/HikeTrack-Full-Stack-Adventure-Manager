package edu.hike.idde.myproject.spring.service.implementation;

import edu.hike.idde.myproject.spring.dao.HikeStatsDao;
import edu.hike.idde.myproject.spring.model.HikeUserStats;
import edu.hike.idde.myproject.spring.service.HikeStatsService;
import edu.hike.idde.myproject.spring.service.exception.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HikeStatsServiceImplementation implements HikeStatsService {

    @Autowired
    private HikeStatsDao dao;

    @Override
    public HikeUserStats getByHikeId(Long userId, Long hikeId) throws ServiceNotFoundException {
        return dao.findByUserAndHikeId(userId, hikeId);
    }

    @Override
    public void setFavorite(Long userId, Long hikeId, Boolean favorite) {
        HikeUserStats stats = dao.findByUserAndHikeId(userId, hikeId);
        stats.setFavorite(favorite);

        if (stats.getId() == null) {
            dao.create(stats);
        } else {
            dao.update(stats);
        }
    }

    @Override
    public void incrementTimesCompleted(Long userId, Long hikeId) {
        HikeUserStats stats = dao.findByUserAndHikeId(userId, hikeId);
        stats.setTimesCompleted(stats.getTimesCompleted() + 1);

        if (stats.getId() == null) {
            dao.create(stats);
        } else {
            dao.update(stats);
        }
    }

    @Override
    public void createForHike(Long hikeId) {
        HikeUserStats stats = new HikeUserStats();
        stats.setHikeId(hikeId);
        dao.create(stats);
    }

    @Override
    public void deleteForHike(Long hikeId) {
        dao.deleteByHikeId(hikeId);
    }
}
