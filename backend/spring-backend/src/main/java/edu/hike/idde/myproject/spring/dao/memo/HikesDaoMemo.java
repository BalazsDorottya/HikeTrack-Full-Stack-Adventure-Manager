package edu.hike.idde.myproject.spring.dao.memo;

import edu.hike.idde.myproject.spring.dao.HikesDao;
import edu.hike.idde.myproject.spring.dao.exception.IdNotFoundException;
import edu.hike.idde.myproject.spring.model.Hike;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@Profile("memo")
public class HikesDaoMemo implements HikesDao {

    private final Map<Long, Hike> hikes = new ConcurrentHashMap<>();
    private final AtomicLong idIterrator = new AtomicLong(0);

    @Override
    public Hike read(Long id) throws IdNotFoundException {
        if (!hikes.containsKey(id)) {
            throw new IdNotFoundException();
        }
        return hikes.get(id);
    }

    @Override
    public Collection<Hike> readAll() {
        return hikes.values();
    }

    @Override
    public Hike create(Hike hike) {
        Long currentId = idIterrator.getAndIncrement();
        hike.setId(currentId);
        hikes.put(currentId, hike);
        return hike;
    }

    @Override
    public void update(Hike hike) throws IdNotFoundException {
        if (!hikes.containsKey(hike.getId())) {
            throw new IdNotFoundException();
        }
        hikes.replace(hike.getId(), hike);
    }

    @Override
    public void delete(Long id) throws IdNotFoundException {
        if (!hikes.containsKey(id)) {
            throw new IdNotFoundException();
        }
        hikes.remove(id);
    }

    @Override
    public Collection<Hike> getHikesByName(String name) {
        return hikes.values().stream()
                .filter(h -> h.getNameOfTrail() != null && h.getNameOfTrail().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Hike> getHikesByLocation(String startLocation) {
        return hikes.values().stream()
                .filter(h -> h.getNameOfTrail() != null && h.getNameOfTrail().contains(startLocation))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Hike> getHikesByFilter(String name, String location) {
        return hikes.values().stream()
                .filter(h -> h.getNameOfTrail() != null && h.getNameOfTrail().contains(location))
                .collect(Collectors.toList());
    }
}
