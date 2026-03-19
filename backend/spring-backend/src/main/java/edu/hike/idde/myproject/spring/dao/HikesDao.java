package edu.hike.idde.myproject.spring.dao;

import edu.hike.idde.myproject.spring.model.Hike;

import java.util.Collection;

public interface HikesDao extends Dao<Hike> {
    Collection<Hike> getHikesByName(String name);

    Collection<Hike> getHikesByLocation(String startLocation);

    Collection<Hike> getHikesByFilter(String name, String location);
}
