package edu.hike.idde.myproject.spring.model;

public class HikeUserStats extends BaseEntity {

    private Long userId;
    private Long hikeId;
    private Boolean favorite;
    private Integer timesCompleted;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHikeId() {
        return hikeId;
    }

    public void setHikeId(Long hikeId) {
        this.hikeId = hikeId;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getTimesCompleted() {
        return timesCompleted;
    }

    public void setTimesCompleted(Integer timesCompleted) {
        this.timesCompleted = timesCompleted;
    }
}
