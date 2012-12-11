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

    public UsableTopic(final UUID id) {
        super(id);
    }

    public abstract TopicType getType();

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void addName(final FeelhubLanguage feelhubLanguage, final String name) {
        names.put(feelhubLanguage.getCode(), WordUtils.capitalizeFully(name));
        createTags(name);
    }

    private void createTags(final String name) {
        DomainEventBus.INSTANCE.post(new TagRequestEvent(this, name));
    }

    public String getName(final FeelhubLanguage feelhubLanguage) {
        if (names.containsKey(feelhubLanguage.getCode())) {
            return names.get(feelhubLanguage.getCode());
        } else if (names.containsKey(FeelhubLanguage.reference().getCode())) {
            return names.get(FeelhubLanguage.reference().getCode());
        } else {
            return "";
        }
    }

    public void addDescription(final FeelhubLanguage language, final String description) {
        descriptions.put(language.getCode(), description);
    }

    public String getDescription(final FeelhubLanguage language) {
        if (descriptions.containsKey(language.getCode())) {
            return descriptions.get(language.getCode());
        } else if (descriptions.containsKey(FeelhubLanguage.reference().getCode())) {
            return descriptions.get(FeelhubLanguage.reference().getCode());
        } else {
            return "";
        }
    }

    public Map<String, String> getNames() {
        return names;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    private UUID userId;
    protected Map<String, String> names = Maps.newHashMap();
    private final Map<String, String> descriptions = Maps.newHashMap();
}
