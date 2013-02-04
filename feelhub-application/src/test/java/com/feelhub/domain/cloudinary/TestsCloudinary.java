package com.feelhub.domain.cloudinary;

import com.google.common.collect.Maps;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Map;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsCloudinary {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        cloudinaryLink = mock(CloudinaryLink.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CloudinaryLink.class).toInstance(cloudinaryLink);
            }
        });
        cloudinary = injector.getInstance(Cloudinary.class);
    }

    @Test
    public void tryToGetThumbnailLarge() throws IOException {
        String source = "source";
        Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_272,h_168,c_fill,g_face,q_60");
        params.put("file", source);

        cloudinary.getThumbnails(source);

        verify(cloudinaryLink).getIllustration(params);
    }

    @Test
    public void tryToGetThumbnailMedium() throws IOException {
        String source = "source";
        Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_135,h_168,c_fill,g_face,q_60");
        params.put("file", source);

        cloudinary.getThumbnails(source);

        verify(cloudinaryLink).getIllustration(params);
    }

    @Test
    public void tryToGetThumbnailSmall() throws IOException {
        String source = "source";
        Map<String, String> params = Maps.newHashMap();
        params.put("format", "jpg");
        params.put("transformation", "w_90,h_56,c_fill,g_face,q_60");
        params.put("file", source);

        cloudinary.getThumbnails(source);

        verify(cloudinaryLink).getIllustration(params);
    }

    @Test
    public void returnAllThumbnails() throws IOException {
        String source = "source";
        when(cloudinaryLink.getIllustration(anyMap())).thenReturn("thumbnail");

        CloudinaryThumbnails cloudinaryThumbnails = cloudinary.getThumbnails(source);

        assertThat(cloudinaryThumbnails).isNotNull();
        assertThat(cloudinaryThumbnails.getThumbnailSmall()).isEqualTo("thumbnail");
        assertThat(cloudinaryThumbnails.getThumbnailMedium()).isEqualTo("thumbnail");
        assertThat(cloudinaryThumbnails.getThumbnailLarge()).isEqualTo("thumbnail");
    }

    @Test
    public void throwCloudinaryExceptionOnAnyError() throws IOException {
        exception.expect(CloudinaryException.class);
        when(cloudinaryLink.getIllustration(anyMap())).thenThrow(new IOException());

        cloudinary.getThumbnails("source");
    }

    private Cloudinary cloudinary;
    private CloudinaryLink cloudinaryLink;
}
