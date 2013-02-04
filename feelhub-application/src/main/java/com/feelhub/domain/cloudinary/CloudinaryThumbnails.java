package com.feelhub.domain.cloudinary;

public class CloudinaryThumbnails {

    public String getThumbnailLarge() {
        return thumbnailLarge;
    }

    public void setThumbnailLarge(final String thumbnailLarge) {
        this.thumbnailLarge = thumbnailLarge;
    }

    public String getThumbnailMedium() {
        return thumbnailMedium;
    }

    public void setThumbnailMedium(final String thumbnailMedium) {
        this.thumbnailMedium = thumbnailMedium;
    }

    public String getThumbnailSmall() {
        return thumbnailSmall;
    }

    public void setThumbnailSmall(final String thumbnailSmall) {
        this.thumbnailSmall = thumbnailSmall;
    }

    private String thumbnailLarge;
    private String thumbnailMedium;
    private String thumbnailSmall;
}
