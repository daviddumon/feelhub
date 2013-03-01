package com.feelhub.domain.cloudinary;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import java.util.Map;

public class Cloudinary {

    @Inject
    public Cloudinary(final CloudinaryLink cloudinaryLink) {
        this.cloudinaryLink = cloudinaryLink;
    }

    public CloudinaryThumbnails getThumbnails(final String source) {
        try {
            final CloudinaryThumbnails cloudinaryThumbnails = new CloudinaryThumbnails();
            cloudinaryThumbnails.setThumbnailLarge(getThumbnailLarge(source));
            cloudinaryThumbnails.setThumbnailMedium(getThumbnailMedium(source));
            cloudinaryThumbnails.setThumbnailSmall(getThumbnailSmall(source));
            return cloudinaryThumbnails;
        } catch (Exception e) {
            throw new CloudinaryException();
        }
    }

    private String getThumbnailLarge(final String source) throws Exception {
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_272,h_168,c_fill,g_face,q_60");
        params.put("file", source);
        return cloudinaryLink.getIllustration(params);
    }

    private String getThumbnailMedium(final String source) throws Exception {
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_135,h_168,c_fill,g_face,q_60");
        params.put("file", source);
        return cloudinaryLink.getIllustration(params);
    }

    private String getThumbnailSmall(final String source) throws Exception {
        final Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_90,h_56,c_fill,g_face,q_60");
        params.put("file", source);
        return cloudinaryLink.getIllustration(params);
    }

    private final CloudinaryLink cloudinaryLink;
}
