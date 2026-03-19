package edu.hike.idde.myproject.spring.dao;

import edu.hike.idde.myproject.spring.dao.exception.IdNotFoundException;
import edu.hike.idde.myproject.spring.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    T read(Long id) throws IdNotFoundException;

    Collection<T> readAll();

    T create(T hike);

    void update(T hike) throws IdNotFoundException;

    void delete(Long id) throws IdNotFoundException;
}
