package com.feelhub.domain.cloudinary;

import java.io.IOException;
import java.util.Map;

public class FakeCloudinaryLink extends CloudinaryLink {

    @Override
    public String getIllustration(final Map<String, String> params) throws IOException {
        return "thumbnail";
    }
}
