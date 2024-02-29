package soft.uni.springdataautomappingobjects.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "games")
public class Game extends BasicEntity {

    @Column(nullable = false, unique = true)
    private String title;

    @Column
    private String trailer;

    @Column(name = "image_url")
    private String imageURL;

    @Column(nullable = false)
    private double size;

    @Column(nullable = false)
    private double price;

    @Column
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Game() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {


        this.imageURL = imageURL;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
