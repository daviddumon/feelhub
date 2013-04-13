package com.feelhub.domain.topic;

public class Thumbnail {

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getCloudinary() {
        return cloudinary;
    }

    public void setCloudinary(final String cloudinary) {
        this.cloudinary = cloudinary;
    }

    private String origin;
    private String cloudinary;
}
