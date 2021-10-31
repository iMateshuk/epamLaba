package com.epam.esm.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class GiftCertDTO {

    private int id;
    private String name;
    private String description;
    private float price;
    private int duration;
    private Date lastUpdateDate;
    private List<String> tagNames = new ArrayList<>();

    public GiftCertDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GiftCertDTO that = (GiftCertDTO) o;

        return id == that.id && Float.compare(that.price, price) == 0 && duration == that.duration && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(lastUpdateDate, that.lastUpdateDate) && Objects.equals(tagNames, that.tagNames);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, price, duration, lastUpdateDate, tagNames);
    }

    @Override
    public String toString() {

        return getClass().getName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tagNames=" + tagNames +
                '}';
    }
}
