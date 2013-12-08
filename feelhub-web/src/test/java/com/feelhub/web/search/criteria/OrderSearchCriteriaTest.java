package com.feelhub.web.search.criteria;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class OrderSearchCriteriaTest {

    @Test
    public void defaultCriteriaIsAll() {
        assertThat(OrderSearchCriteria.defaultValue()).isEqualTo(OrderSearchCriteria.Hot);
    }

    @Test
    public void canGetValueFromString() {
        assertThat(OrderSearchCriteria.fromString("Hot topics")).isEqualTo(OrderSearchCriteria.Hot);
        assertThat(OrderSearchCriteria.fromString("New topics")).isEqualTo(OrderSearchCriteria.New);
        assertThat(OrderSearchCriteria.fromString("Popular topics")).isEqualTo(OrderSearchCriteria.Popular);
    }

}
