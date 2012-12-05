package com.feelhub.domain.tag;

public class TagMerger {

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
}
