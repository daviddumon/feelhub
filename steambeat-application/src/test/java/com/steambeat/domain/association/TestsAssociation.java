package com.steambeat.domain.association;

import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.thesaurus.Language;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAssociation {

    @Test
    public void canCreateAnAssociationFromUriAndUUID() {
        final String address = "http://www.steambeat.com";
        final Uri uri = new Uri(address);
        final UUID uuid = UUID.randomUUID();

        final Association association = new Association(uri, uuid);

        assertThat(association.getIdentifier(), is(uri.toString()));
        assertThat(association.getSubjectId(), is(uuid));
        assertThat(association.getLanguage(), is(Language.forString("all")));
    }

    @Test
    public void canCreateAnAssociationFromATagAndUUID() {
        final Tag tag = new Tag("tag");
        final UUID uuid = UUID.randomUUID();

        final Association association = new Association(tag, uuid);

        assertThat(association.getIdentifier(), is(tag.toString()));
        assertThat(association.getSubjectId(), is(uuid));
        assertThat(association.getLanguage(), is(Language.forString("all")));
    }

    @Test
    public void canCreateAnAssociationWithALanguage() {
        final Tag tag = new Tag("tag");
        final UUID uuid = UUID.randomUUID();
        final Language french = Language.forString("french");

        final Association association = new Association(tag, uuid, french);

        assertThat(association.getIdentifier(), is(tag.toString()));
        assertThat(association.getSubjectId(), is(uuid));
        assertThat(association.getLanguage(), is(french));
    }

    @Test
    public void hasAWeight() {
        final Tag tag = new Tag("tag");
        final UUID uuid = UUID.randomUUID();
        final Language french = Language.forString("french");

        final Association association = new Association(tag, uuid, french);
        final Association otherAssociation = new Association(tag, uuid);

        assertThat(association.getWeight(), is(1));
        assertThat(otherAssociation.getWeight(), is(1));
    }

    @Test
    public void toUseAssociationIncreaseWeight() {
        final Association association = new Association(new Tag("tag"), UUID.randomUUID());

        association.use();

        assertThat(association.getWeight(), is(2));
    }
}
