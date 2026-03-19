package edu.hike.idde.myproject.spring.controller;

import edu.hike.idde.myproject.spring.controller.dto.HikeCreationDTO;
import edu.hike.idde.myproject.spring.controller.dto.HikeLongDTO;
import edu.hike.idde.myproject.spring.controller.dto.HikeShortDTO;
import edu.hike.idde.myproject.spring.controller.mapper.HikeMapper;
import edu.hike.idde.myproject.spring.model.Hike;
import edu.hike.idde.myproject.spring.model.HikeUserStats;
import edu.hike.idde.myproject.spring.model.User;
import edu.hike.idde.myproject.spring.service.HikeService;
import edu.hike.idde.myproject.spring.service.HikeStatsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController // every method return json as response
@RequestMapping("/hikes")
@CrossOrigin(origins = "http://localhost:5173")
public class HikeController {
    @Autowired
    private HikeService service;
    @Autowired
    private HikeMapper hikeMapper;
    @Autowired
    private HikeStatsService statsService;


    @GetMapping
    public Collection<HikeShortDTO> getHikes(
            @RequestParam(required = false) String nameOfTrail,
            @RequestParam(required = false) String locationName) {

        Long userId = getAuthenticatedUserId();

        Collection<Hike> hikes = service.getHikesFiltered(nameOfTrail, locationName);

        return hikes.stream()
                .map(h -> hikeMapper.hikeToShortDTO(h, statsService.getByHikeId(userId, h.getId())))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public HikeLongDTO read(@PathVariable("id") Long id) {
        Long userId = getAuthenticatedUserId();
        Hike hike = service.read(id);
        HikeUserStats stats = statsService.getByHikeId(userId, hike.getId());
        return hikeMapper.hikeToDTO(hike, stats);
    }

    @PostMapping
    public HikeLongDTO create(@RequestBody @Valid HikeCreationDTO hikeDto) {
        Long userId = getAuthenticatedUserId();
        Hike hike = service.create(hikeMapper.dtoToHike(hikeDto));
        HikeUserStats stats = statsService.getByHikeId(userId, hike.getId());
        return hikeMapper.hikeToDTO(hike, stats);
    }

    @PutMapping("/{id}")
    public HikeLongDTO update(@PathVariable("id") Long id, @RequestBody @Valid HikeCreationDTO hikeDTO) {
        Long userId = getAuthenticatedUserId();
        Hike hike = hikeMapper.dtoToHike(hikeDTO);
        hike.setId(id);
        service.update(hike);

        HikeUserStats stats = statsService.getByHikeId(userId, id);
        return hikeMapper.hikeToDTO(hike, stats);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }


    private Long getAuthenticatedUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

}
