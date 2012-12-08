package com.feelhub.domain.topic.usable;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.tag.TagRequestEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.google.common.collect.Maps;
import org.apache.commons.lang.WordUtils;

import java.util.*;

public abstract class UsableTopic extends Topic {

    //mongolink
    protected UsableTopic() {
    }

    public UsableTopic(final UUID id, final TopicType type) {
        super(id);
        this.type = type;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public TopicType getType() {
        return type;
    }

    public void addName(final FeelhubLanguage feelhubLanguage, final String name) {
        names.put(feelhubLanguage, WordUtils.capitalizeFully(name));
        createTags(name);
    }

    private void createTags(final String name) {
        DomainEventBus.INSTANCE.post(new TagRequestEvent(this, name));
    }

    public String getName(final FeelhubLanguage feelhubLanguage) {
        if (names.containsKey(feelhubLanguage)) {
            return names.get(feelhubLanguage);
        } else if (names.containsKey(FeelhubLanguage.reference())) {
            return names.get(FeelhubLanguage.reference());
        } else {
            return "";
        }
    }

    public void addDescription(final FeelhubLanguage language, final String description) {
        descriptions.put(language, description);
    }

    public String getDescription(final FeelhubLanguage language) {
        if (descriptions.containsKey(language)) {
            return descriptions.get(language);
        } else if (descriptions.containsKey(FeelhubLanguage.reference())) {
            return descriptions.get(FeelhubLanguage.reference());
        } else {
            return "";
        }
    }

    public Map<FeelhubLanguage, String> getNames() {
        return names;
    }

    public Map<FeelhubLanguage, String> getDescriptions() {
        return descriptions;
    }

    public void setType(final TopicType type) {
        this.type = type;
    }

    protected TopicType type;
    private UUID userId;
    protected Map<FeelhubLanguage, String> names = Maps.newHashMap();
    private final Map<FeelhubLanguage, String> descriptions = Maps.newHashMap();
}
