package com.feelhub.web.dto;

import com.feelhub.domain.Entity;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.uri.Uri;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.json.JSONObject;

import java.util.*;

public class TopicData {

    public static class Builder {

        public TopicData build() {
            return new TopicData(this);
        }

        public Builder id(final UUID id) {
            if (id != null) {
                this.id = id.toString();
            }
            return this;
        }

        public Builder name(final String name) {
            if (name != null) {
                this.name = name;
            }
            return this;
        }

        public Builder type(final TopicType type) {
            this.type = type;
            return this;
        }

        public Builder subtypes(final List<String> subTypes) {
            this.subTypes = subTypes;
            return this;
        }

        public Builder uris(final List<Uri> uris) {
            for (final Uri uri : uris) {
                this.uris.add(uri.toString());
            }
            return this;
        }

        public Builder thumbnail(final String thumbnail) {
            if (thumbnail != null) {
                this.thumbnail = thumbnail;
            }
            return this;
        }

        public Builder description(final String description) {
            if (description != null) {
                this.description = description;
            }
            return this;
        }

        public Builder happyFeelingCount(final int happyFeelingCount) {
            this.happyFeelingCount = happyFeelingCount;
            return this;
        }

        public Builder boredFeelingCount(final int boredFeelingCount) {
            this.boredFeelingCount = boredFeelingCount;
            return this;
        }

        public Builder sadFeelingCount(final int sadFeelingCount) {
            this.sadFeelingCount = sadFeelingCount;
            return this;
        }

        public Builder hasFeelings(final boolean hasFeelings) {
            this.hasFeelings = hasFeelings;
            return this;
        }

        public Builder creationDate(final DateTime creationDate) {
            this.creationDate = creationDate.getMillis();
            return this;
        }

        public Builder lastModificatioNDate(final DateTime lastModificationDate) {
            this.lastModificationDate = lastModificationDate.getMillis();
            return this;
        }

        public Builder viewCount(final int viewCount) {
            this.viewCount = viewCount;
            return this;
        }

        public Builder popularity(final int popularity) {
            this.popularity = popularity;
            return this;
        }

        private String id = "";
        private String thumbnail = "";
        private String name = "";
        private TopicType type = UnusableTopicTypes.None;
        private List<String> subTypes = Lists.newArrayList();
        private final List<String> uris = Lists.newArrayList();
        private String description = "";
        private int happyFeelingCount;
        private int boredFeelingCount;
        private int sadFeelingCount;
        private boolean hasFeelings = false;
        private long creationDate;
        private long lastModificationDate;
        private int viewCount = 0;
        private int popularity = 0;
    }

    private TopicData(final Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type.toString();
        this.subTypes = builder.subTypes;
        this.uris = builder.uris;
        this.description = builder.description;
        this.thumbnail = builder.thumbnail;
        this.happyFeelingCount = builder.happyFeelingCount;
        this.boredFeelingCount = builder.boredFeelingCount;
        this.sadFeelingCount = builder.sadFeelingCount;
        this.hasFeelings = builder.hasFeelings;
        this.creationDate = builder.creationDate;
        this.lastModificationDate = builder.lastModificationDate;
        this.viewCount = builder.viewCount;
        this.popularity = builder.popularity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<String> getSubTypes() {
        return subTypes;
    }

    public List<String> getUris() {
        return uris;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    public int getHappyFeelingCount() {
        return happyFeelingCount;
    }

    public int getBoredFeelingCount() {
        return boredFeelingCount;
    }

    public int getSadFeelingCount() {
        return sadFeelingCount;
    }

    public boolean getHasFeelings() {
        return hasFeelings;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public long getLastModificationDate() {
        return lastModificationDate;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getViewCount() {
        return viewCount;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Entity entity = (Entity) obj;
        return Objects.equal(entity.getId(), this.getId());
    }

    private final boolean hasFeelings;
    private final int happyFeelingCount;
    private final int sadFeelingCount;
    private final int boredFeelingCount;
    private final String id;
    private final String thumbnail;
    private final String name;
    private final String type;
    private final List<String> subTypes;
    private final List<String> uris;
    private final String description;
    private final long creationDate;
    private final long lastModificationDate;
    private final int viewCount;
    private final int popularity;
}
