package edu.hike.idde.myproject.spring.service;

import edu.hike.idde.myproject.spring.model.Hike;
import edu.hike.idde.myproject.spring.service.exception.ServiceNotFoundException;

import java.util.Collection;

public interface HikeService {
    Hike read(Long id) throws ServiceNotFoundException;

    Collection<Hike> readAll();

    Hike create(Hike hike);

    void update(Hike hike) throws ServiceNotFoundException;

    void delete(Long id) throws ServiceNotFoundException;

    Collection<Hike> getHikesByName(String name) throws ServiceNotFoundException;

    Collection<Hike> getHikesByLocation(String location) throws ServiceNotFoundException;

    Collection<Hike> getHikesFiltered(String name, String location) throws ServiceNotFoundException;
}
