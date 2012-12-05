package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.tag.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.Subscribe;

public class TagService {

    public TagService() {
        DomainEventBus.INSTANCE.register(this);
    }

    public Tag lookUp(final String description) {
        final Tag tag = Repositories.tags().get(description.toLowerCase());
        if (tag == null) {
            throw new TagNotFoundException();
        }
        return tag;
    }

    @Subscribe
    public void onTagRequestEvent(final TagRequestEvent tagRequestEvent) {
        createTags(tagRequestEvent.getTopic());
    }

    protected void createTags(final Topic topic) {
        for (final String description : topic.getDescriptions().values()) {
            final Tag tag = lookUpOrCreateTag(topic, description);
            if (!tag.getTopicIds().contains(topic.getId())) {
                tag.addTopic(topic);
            }
        }
    }

    private Tag lookUpOrCreateTag(final Topic topic, final String description) {
        try {
            return lookUp(description);
        } catch (TagNotFoundException e) {
            return createTag(description);
        }
    }

    private Tag createTag(final String description) {
        final Tag tag = new Tag(description.toLowerCase());
        Repositories.tags().add(tag);
        return tag;
    }
}

//public void addUriTag(final String value, final Topic topic) {
//final List<String> tokens = uriManager.getTokens(value);
//            final List<Keyword> keywords = Lists.newArrayList();
//            for (final String token : tokens) {
//                try {
//                    keywords.add(lookUp(token));
//                } catch (KeywordNotFound e) {
//                    keywords.add(createUri(token, topic.getId()));
//                }
//            }
//            final KeywordMerger keywordMerger = new KeywordMerger();
//            keywordMerger.merge(keywords);
//            final Uri uri = (Uri) keywords.get(0);
//}

//public Tag lookUpOrCreate(final String value, final FeelhubLanguage language) {
//    if (TagIdentifier.isUri(value)) {
//        //return uriService.lookUpOrCreate(value);
//        return null;
//    } else {
//        return wordService.lookUpOrCreate(value, language);
//    }
//}

//todo delete
//public Tag lookUp(final UUID topicId, final FeelhubLanguage language) {
//    final Tag tag;
//final List<Tag> tags = Repositories.tags().forTopicId(topicId);
//if (!tags.isEmpty()) {
//    tag = getGoodKeyword(tags, language);
//} else {
//    // it should never happens!
//    tag = new Word("?", language, topicId);
//}
//return tag;
//}

//todo delete normalement ce choix est fait au niveau du topic maintenant
//private Tag getGoodKeyword(final List<Tag> tags, final FeelhubLanguage feelhubLanguage) {
//    Tag referenceTag = null;
//    for (final Tag tag : tags) {
//        if (tag.getLanguage().equals(feelhubLanguage)) {
//            return tag;
//        } else if (tag.getLanguage().equals(FeelhubLanguage.reference())) {
//            referenceTag = tag;
//        }
//    }
//    if (referenceTag != null) {
//        return referenceTag;
//    } else {
//        return tags.get(0);
//    }
//}
