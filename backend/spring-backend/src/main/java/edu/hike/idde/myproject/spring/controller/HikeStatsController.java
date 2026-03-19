package edu.hike.idde.myproject.spring.controller;

import edu.hike.idde.myproject.spring.controller.dto.FavoriteUpdateDTO;
import edu.hike.idde.myproject.spring.controller.dto.HikeLongDTO;
import edu.hike.idde.myproject.spring.controller.mapper.HikeMapper;
import edu.hike.idde.myproject.spring.model.Hike;
import edu.hike.idde.myproject.spring.model.HikeUserStats;
import edu.hike.idde.myproject.spring.model.User;
import edu.hike.idde.myproject.spring.service.HikeService;
import edu.hike.idde.myproject.spring.service.HikeStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hikes/{id}/stats")
@CrossOrigin(origins = "http://localhost:5173")
public class HikeStatsController {

    @Autowired
    private HikeStatsService statsService;
    @Autowired
    private HikeService hikeService;
    @Autowired
    private HikeMapper hikeMapper;


    @PatchMapping("/favorite")
    public HikeLongDTO updateFavorite(@PathVariable Long id, @RequestBody FavoriteUpdateDTO dto) {
        Long userId = getAuthenticatedUserId();
        statsService.setFavorite(userId, id, dto.getFavorite());
        Hike hike = hikeService.read(id);
        HikeUserStats stats = statsService.getByHikeId(userId, id);
        return hikeMapper.hikeToDTO(hike, stats);
    }

    @PatchMapping("/complete")
    public HikeLongDTO complete(@PathVariable Long id) {
        Long userId = getAuthenticatedUserId();
        statsService.incrementTimesCompleted(userId, id);
        Hike hike = hikeService.read(id);
        HikeUserStats stats = statsService.getByHikeId(userId, id);
        return hikeMapper.hikeToDTO(hike, stats);
    }

    private Long getAuthenticatedUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}
