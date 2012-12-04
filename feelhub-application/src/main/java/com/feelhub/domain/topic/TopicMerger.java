package com.feelhub.domain.topic;

import com.feelhub.domain.tag.Tag;

import java.util.List;

public class TopicMerger {

    public void merge(final Topic newTopic, final Topic oldTopic) {
        //final TopicPatch topicPatch = createTopicPatch(tags);
        //tagManager.merge(topicPatch);
        //illustrationManager.merge(topicPatch);
        //feelingManager.merge(topicPatch);
        //relationManager.merge(topicPatch);
        //statisticsManager.merge(topicPatch);
    }

    //private final StatisticsManager statisticsManager = new StatisticsManager();

    //private TopicPatch createTopicPatch(final List<Tag> tags) {
    //    final List<Topic> allTopics = getAllTopics(tags);
    //    final Topic oldestTopic = getOldestTopic(allTopics);
    //    final TopicPatch topicPatch = new TopicPatch(oldestTopic.getId());
    //    appendOldTopics(topicPatch, allTopics);
    //    return topicPatch;
    //}
    //
    //private List<Topic> getAllTopics(final List<Tag> tags) {
    //    final List<Topic> topics = Lists.newArrayList();
    //    for (final Tag tag : tags) {
    //        final UUID topicId = tag.getTopicId();
    //        final Topic topic = Repositories.topics().get(topicId);
    //        topics.add(topic);
    //    }
    //    return topics;
    //}
    //
    //private Topic getOldestTopic(final List<Topic> topics) {
    //    Topic result = topics.get(0);
    //    for (int i = 1; i < topics.size(); i++) {
    //        final Topic current = topics.get(i);
    //        if (current.getCreationDate().isBefore(result.getCreationDate())) {
    //            result = current;
    //        }
    //    }
    //    return result;
    //}
    //
    //private void appendOldTopics(final TopicPatch topicPatch, final List<Topic> allTopics) {
    //    for (final Topic topic : allTopics) {
    //        final UUID currentId = topic.getId();
    //        if (!currentId.equals(topicPatch.getNewTopicId())) {
    //            topicPatch.addOldTopicId(currentId);
    //        }
    //    }
    //}
    //
    //private final TagManager tagManager = new TagManager();
    //private final IllustrationManager illustrationManager = new IllustrationManager();
    //private final FeelingManager feelingManager = new FeelingManager();
    //private final RelationManager relationManager = new RelationManager();
}
