package edu.hike.idde.myproject.spring.controller.mapper;

import edu.hike.idde.myproject.spring.controller.dto.HikeCreationDTO;
import edu.hike.idde.myproject.spring.controller.dto.HikeLongDTO;
import edu.hike.idde.myproject.spring.controller.dto.HikeShortDTO;
import edu.hike.idde.myproject.spring.model.Hike;
import edu.hike.idde.myproject.spring.model.HikeUserStats;
import org.mapstruct.*;

import java.util.Collection;
import java.util.Map;

@Mapper(componentModel = "spring")
//MapStruct generates a mapper implementation (it gets handled like a Bean)
public abstract class HikeMapper {
    public abstract Hike dtoToHike(HikeCreationDTO hikeCreation);

    @IterableMapping(elementTargetType = HikeShortDTO.class) //Tells how to map collections
    public abstract Collection<HikeShortDTO> hikesToDTO(Collection<Hike> hikes,
                                                        @Context Map<Long, HikeUserStats> statsMap);

    public abstract HikeLongDTO hikeToDTO(Hike hike);

    public abstract HikeLongDTO hikeToDTO(Hike hike, @Context HikeUserStats stats);

    @AfterMapping
    protected void enrichedWithStats(Hike hike, @MappingTarget HikeLongDTO dto, @Context HikeUserStats stats) {
        dto.setFavorite(stats.isFavorite());
        dto.setTimesCompleted(stats.getTimesCompleted());
    }

    @AfterMapping
    protected void enrichShortDTO(Hike hike, @MappingTarget HikeShortDTO dto, @Context HikeUserStats stats) {
        dto.setFavorite(stats.isFavorite());
        dto.setTimesCompleted(stats.getTimesCompleted());
    }

    public abstract HikeShortDTO hikeToShortDTO(Hike hike, @Context HikeUserStats stats);
}
