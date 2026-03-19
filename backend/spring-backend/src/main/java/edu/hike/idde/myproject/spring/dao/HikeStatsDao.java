package edu.hike.idde.myproject.spring.dao;

import edu.hike.idde.myproject.spring.model.HikeUserStats;

public interface HikeStatsDao {

    HikeUserStats findByUserAndHikeId(Long userId, Long hikeId);

    HikeUserStats create(HikeUserStats stats);

    void update(HikeUserStats stats);

    void deleteByHikeId(Long hikeId);
}
