package com.feelhub.domain.tag;

public class TestsTagMerger {

    //@Test
    //public void mergeStatistics() {
    //    final Topic good = TestFactories.topics().newTopic();
    //    final Topic bad = TestFactories.topics().newTopic();
    //    TestFactories.statistics().newStatisticsWithSentiments(bad.getId(), Granularity.hour);
    //
    //    topicMerger.merge(createListOfKeyword(good, bad));
    //
    //    final Statistics statistics = Repositories.statistics().getAll().get(0);
    //    assertThat(statistics.getTopicId()).isEqualTo(good.getId());
    //}
    //
    //private List<Tag> createListOfKeyword(final Topic good, final Topic bad) {
    //    final List<Tag> tags = Lists.newArrayList();
    //    final Tag first = TestFactories.tags().newTag("first", FeelhubLanguage.reference(), good.getId());
    //    tags.add(first);
    //    time.waitDays(1);
    //    tags.add(TestFactories.tags().newTag("second", FeelhubLanguage.none(), bad.getId()));
    //    tags.add(TestFactories.tags().newTag("third"));
    //    return tags;
    //}
}
