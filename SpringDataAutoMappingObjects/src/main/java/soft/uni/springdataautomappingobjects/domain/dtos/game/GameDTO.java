package soft.uni.springdataautomappingobjects.domain.dtos.game;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static soft.uni.springdataautomappingobjects.domain.constants.Validation.*;
import static soft.uni.springdataautomappingobjects.domain.exseptions.ExceptionMessage.*;

public class GameDTO {
    private String title;

    private double price;

    private double size;

    private String trailer;

    private String imageURL;

    private String description;

    private LocalDate releaseDate;

    public GameDTO(String title, double price, double size, String trailer, String imageURL, String description, LocalDate releaseDate) {
        this.setTitle(title);
        this.setPrice(price);
        this.setSize(size);
        this.setTrailer(trailer);
        this.setImageURL(imageURL);
        this.setDescription(description);
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        boolean isValidTitle = Pattern.matches(GAME_TITLE_VERIFICATION, title);
        try {
            if (!isValidTitle) {
                throw new IllegalArgumentException(INCORRECT_GAME_TITLE);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        try {
            if (price < 0) {
                throw new IllegalArgumentException(INVALID_GAME_PRICE);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        try {
            if (size < 0) {
                throw new IllegalArgumentException(INVALID_GAME_SIZE);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        try {
            if (trailer.length() != 11) {
                throw new IllegalArgumentException(INVALID_GAME_TRAILER);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        this.trailer = trailer;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        try {
            if (!imageURL.contains(GAME_IMAGE_FIRST_URL_VERIFICATION) && !imageURL.contains(GAME_IMAGE_SECOND_URL_VERIFICATION)) {
                throw new IllegalArgumentException(INVALID_GAME_IMAGE_URL);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        try {
            if (description.length() < 20) {
                throw new IllegalArgumentException(INVALID_GAME_DESCRIPTION);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
