package com.feelhub.web.dto;

import com.feelhub.domain.Entity;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.uri.Uri;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
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

        public Builder goodFeelingCount(final int goodFeelingCount) {
            this.goodFeelingCount = goodFeelingCount;
            return this;
        }

        public Builder neutralFeelingCount(final int neutralFeelingCount) {
            this.neutralFeelingCount = neutralFeelingCount;
            return this;
        }

        public Builder badFeelingCount(final int badFeelingCount) {
            this.badFeelingCount = badFeelingCount;
            return this;
        }

        private String id = "";
        private String thumbnail = "";
        private String name = "";
        private TopicType type = UnusableTopicTypes.None;
        private List<String> subTypes = Lists.newArrayList();
        private final List<String> uris = Lists.newArrayList();
        private String description = "";
        private int goodFeelingCount;
        private int neutralFeelingCount;
        private int badFeelingCount;
    }

    private TopicData(final Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type.toString();
        this.subTypes = builder.subTypes;
        this.uris = builder.uris;
        this.description = builder.description;
        this.thumbnail = builder.thumbnail;
        this.goodFeelingCount = builder.goodFeelingCount;
        this.neutralFeelingCount = builder.neutralFeelingCount;
        this.badFeelingCount = builder.badFeelingCount;
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

    public int getGoodFeelingCount() {
        return goodFeelingCount;
    }

    public int getNeutralFeelingCount() {
        return neutralFeelingCount;
    }

    public int getBadFeelingCount() {
        return badFeelingCount;
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

    private final int goodFeelingCount;
    private final int badFeelingCount;
    private final int neutralFeelingCount;
    private final String id;
    private final String thumbnail;
    private final String name;
    private final String type;
    private final List<String> subTypes;
    private final List<String> uris;
    private final String description;
}
