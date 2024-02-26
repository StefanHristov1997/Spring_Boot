package com.example.springintro.model.entity;

import java.math.BigDecimal;

public class BookDTO {
    private String title;
    private String editionType;
    private String ageRestriction;
    private BigDecimal price;

    public BookDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEditionType() {
        return editionType;
    }

    public void setEditionType(String editionType) {
        this.editionType = editionType;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", editionType=" + editionType +
                ", ageRestriction=" + ageRestriction +
                ", price=" + price +
                '}';
    }
}
