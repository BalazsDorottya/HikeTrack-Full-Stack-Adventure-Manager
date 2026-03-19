package edu.hike.idde.myproject.spring.service.implementation;

import edu.hike.idde.myproject.spring.dao.HikesDao;
import edu.hike.idde.myproject.spring.dao.exception.IdNotFoundException;
import edu.hike.idde.myproject.spring.dao.exception.RepositoryException;
import edu.hike.idde.myproject.spring.model.Hike;
import edu.hike.idde.myproject.spring.service.HikeService;
import edu.hike.idde.myproject.spring.service.HikeStatsService;
import edu.hike.idde.myproject.spring.service.exception.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class HikeServiceImplementation implements HikeService {
    @Autowired
    private HikesDao hikesdao;
    @Autowired
    private HikeStatsService hikeStatsService;

    private boolean checkId(Long id) {
        return id != null && id >= 0;
    }

    @Override
    public Hike read(Long id) throws ServiceNotFoundException {
        if (checkId(id)) {
            try {
                return hikesdao.read(id);
            } catch (IdNotFoundException e) {
                throw new ServiceNotFoundException("Hike not found with this ID", e);
            }
        } else {
            throw new ServiceNotFoundException("The object couldn`t be READ!");
        }
    }

    @Override
    public Collection<Hike> readAll() {
        return hikesdao.readAll();
    }

    @Override
    public Hike create(Hike hike) {
        return hikesdao.create(hike);
    }

    @Override
    public void update(Hike hike) throws ServiceNotFoundException {
        if (checkId(hike.getId())) {
            //creating a copy, for avoiding wrong input in the DAO

            Hike copy = new Hike();
            copy.setId(hike.getId());
            copy.setNameOfTrail(hike.getNameOfTrail());
            copy.setStartLocation(hike.getStartLocation());
            copy.setStartTime(hike.getStartTime());
            copy.setPrice(hike.getPrice());
            copy.setLengthOfTrail(hike.getLengthOfTrail());

            try {
                hikesdao.update(copy);
            } catch (IdNotFoundException e) {
                throw new ServiceNotFoundException("Hike not found with this ID", e);
            }
        } else {
            throw new ServiceNotFoundException("The object couldn`t be UPDATED!");
        }
    }

    @Override
    public void delete(Long id) throws ServiceNotFoundException {
        if (checkId(id)) {
            try {
                hikeStatsService.deleteForHike(id);
                hikesdao.delete(id);
            } catch (IdNotFoundException e) {
                throw new ServiceNotFoundException("Hike not found with this ID", e);
            }
        } else {
            throw new ServiceNotFoundException("The object couldn`t be DELETED!");
        }
    }

    @Override
    public Collection<Hike> getHikesByName(String name) {
        Collection<Hike> hikes;
        try {
            hikes = hikesdao.getHikesByName(name);
        } catch (RepositoryException e) {
            throw new ServiceNotFoundException(e.getMessage());
        }

        return hikes;
    }

    @Override
    public Collection<Hike> getHikesByLocation(String location) throws ServiceNotFoundException {
        Collection<Hike> hikes;
        try {
            hikes = hikesdao.getHikesByLocation(location);
        } catch (RepositoryException e) {
            throw new ServiceNotFoundException(e.getMessage());
        }

        return hikes;
    }


    @Override
    public Collection<Hike> getHikesFiltered(String name, String location) {
        try {
            return hikesdao.getHikesByFilter(name, location);
        } catch (RepositoryException e) {
            throw new ServiceNotFoundException(e.getMessage());
        }
    }
}
