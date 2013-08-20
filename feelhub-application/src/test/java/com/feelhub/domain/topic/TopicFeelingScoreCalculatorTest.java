package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TopicFeelingScoreCalculatorTest {

    @Rule
    public SystemTime systemTime = SystemTime.fixed();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void scoreIsZeroWhenNoFeelings() {
        final TopicFeelingScoreCalculator calculator = new TopicFeelingScoreCalculator();

        final int score = calculator.feelingScore(Lists.<Feeling>newArrayList());

        assertThat(score).isEqualTo(0);
    }

    @Test
    public void soreIsMinus100WhenOnlyOneBadFeeling() {
        final TopicFeelingScoreCalculator calculator = new TopicFeelingScoreCalculator();
        final List<Feeling> feelings = Lists.newArrayList(newFeelingWithFixedDate(FeelingValue.bad, 10));

        final int score = calculator.feelingScore(feelings);

        assertThat(score).isEqualTo(-100);
    }

    @Test
    public void scoreIsMinus100WhenAllFeelingsAreBad() {
        final TopicFeelingScoreCalculator calculator = new TopicFeelingScoreCalculator();
        final List<Feeling> feelings = Lists.newArrayList(newFeelingWithFixedDate(FeelingValue.bad, 10), newFeelingWithFixedDate(FeelingValue.bad, 11));

        final int score = calculator.feelingScore(feelings);

        assertThat(score).isEqualTo(-100);
    }

    @Test
    public void scoreIs100WhenAllFeelingsAreGood() {
        final TopicFeelingScoreCalculator calculator = new TopicFeelingScoreCalculator();
        final List<Feeling> feelings = Lists.newArrayList(newFeelingWithFixedDate(FeelingValue.good, 10), newFeelingWithFixedDate(FeelingValue.good, 11));

        final int score = calculator.feelingScore(feelings);

        assertThat(score).isEqualTo(100);
    }

    @Test
    public void scoreIsPonderatedByCreationDate() {
        final TopicFeelingScoreCalculator calculator = new TopicFeelingScoreCalculator();
        List<Feeling> feelings = Lists.newArrayList();
        feelings.add(newFeelingWithFixedDate(FeelingValue.bad, 11));
        feelings.add(newFeelingWithFixedDate(FeelingValue.neutral, 12));
        feelings.add(newFeelingWithFixedDate(FeelingValue.bad, 13));
        feelings.add(newFeelingWithFixedDate(FeelingValue.bad, 14));
        feelings.add(newFeelingWithFixedDate(FeelingValue.neutral, 15));
        feelings.add(newFeelingWithFixedDate(FeelingValue.good, 16));
        feelings.add(newFeelingWithFixedDate(FeelingValue.good, 17));
        feelings.add(newFeelingWithFixedDate(FeelingValue.good, 18));
        feelings.add(newFeelingWithFixedDate(FeelingValue.bad, 19));
        feelings.add(newFeelingWithFixedDate(FeelingValue.good, 20));

        final int score = calculator.feelingScore(feelings);

        assertThat(score).isIn(Lists.newArrayList(31, 32));
    }

    private Feeling newFeelingWithFixedDate(final FeelingValue feelingValue, final long time) {
        systemTime.set(new DateTime(time));
        return TestFactories.feelings().feelingWithValue(feelingValue);
    }
}
