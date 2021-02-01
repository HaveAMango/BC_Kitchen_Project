package com.example.bc_kitchen_project;

public class Rating {
    private String name;
    private String comment;
    private String ratingValue;

    public Rating() {
    }

    public Rating(String name, String comment, String rating_value) {
        this.name = name;
        this.comment = comment;
        this.ratingValue = rating_value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }
}
