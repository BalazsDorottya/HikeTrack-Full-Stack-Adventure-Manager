package edu.hike.idde.myproject.spring.service;

import edu.hike.idde.myproject.spring.model.HikeUserStats;

public interface HikeStatsService {

    HikeUserStats getByHikeId(Long userId, Long hikeId);

    void setFavorite(Long userId, Long hikeId, Boolean favorite);

    void incrementTimesCompleted(Long userId, Long hikeId);

    void createForHike(Long hikeId);

    void deleteForHike(Long hikeId);

}
