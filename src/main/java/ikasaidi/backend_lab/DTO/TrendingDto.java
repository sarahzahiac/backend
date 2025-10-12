package ikasaidi.backend_lab.DTO;

import lombok.Data;


@Data
public class TrendingDto {
    private Long id;
    private String title;
    private long views;       // nb total de vues
    private double avgRating; // note moyenne
    private double score;     // score = views * factor1 + avgRating * factor2

    public TrendingDto() {
    }

    public TrendingDto(Long id, String title, long views, double avgRating, double score) {
        this.id = id;
        this.title = title;
        this.views = views;
        this.avgRating = avgRating;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "TrendingDto{id=%d, title='%s', views=%d, avgRating=%.2f, score=%.2f}"
                .formatted(id, title, views, avgRating, score);
    }
}
