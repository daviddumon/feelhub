package com.feelhub.repositories;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.*;
import com.feelhub.test.SystemTime;
import com.google.common.base.Optional;
import com.mongodb.*;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class UserMongoRepositoryTest extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repository = Repositories.users();
    }

    @Test
    public void canPersistAnUser() {
        final User user = new User();
        user.setEmail("email@email.com");
        user.setPassword("password");
        user.setFullname("fullname");
        user.setLanguage(FeelhubLanguage.fromCode("en"));
        user.addSocialAuth(new SocialAuth(SocialNetwork.FACEBOOK, "id", "token"));

        repository.add(user);

        final DBObject userFound = getUserFromDB(user.getId());
        assertThat(userFound).isNotNull();
        assertThat(userFound.get("_id")).isEqualTo(user.getId());
        assertThat(userFound.get("email").toString()).isEqualTo(user.getEmail());
        assertThat(userFound.get("password")).isEqualTo(user.getPassword());
        assertThat(userFound.get("fullname")).isEqualTo(user.getFullname());
        assertThat(userFound.get("languageCode")).isEqualTo(user.getLanguageCode());
        assertThat(userFound.get("active")).isEqualTo(user.isActive());
        assertThat(userFound.get("creationDate")).isEqualTo(time.getNow().getMillis());
        assertThat(userFound.get("lastModificationDate")).isEqualTo(time.getNow().getMillis());
        final BasicDBList socialTokens = (BasicDBList) userFound.get("socialAuths");
        assertThat(socialTokens).hasSize(1);
        final DBObject token = (DBObject) socialTokens.get(0);
        assertThat(token.get("token")).isEqualTo("token");
        assertThat(token.get("network")).isEqualTo("FACEBOOK");
        assertThat(token.get("id")).isEqualTo("id");
        assertThat(userFound.get("welcomePanelShow")).isEqualTo(true);
    }

    @Test
    public void canGetAnUser() {
        final DBCollection collection = getMongo().getCollection("user");
        final DBObject user = new BasicDBObject();
        final UUID uuid = UUID.randomUUID();
        user.put("_id", uuid);
        collection.insert(user);

        final User userFound = repository.get(uuid);

        assertThat(userFound).isNotNull();
    }

    @Test
    public void canGetByEmail() {
        final DBCollection collection = getMongo().getCollection("user");
        final DBObject user = new BasicDBObject();
        user.put("_id", UUID.randomUUID());
        user.put("email", "jb@test.com");
        user.put("active", true);
        collection.insert(user);

        final Optional<User> userFound = repository.forEmail("jb@test.com");

        assertThat(userFound.isPresent()).isTrue();
    }

    @Test
    @Ignore("fongo do not handle elemMatch")
    public void canGetByNetwork() {
        final User user = new User();
        user.addSocialAuth(new SocialAuth(SocialNetwork.FACEBOOK, "id", "token"));
        repository.add(user);

        final User userFound = repository.findBySocialNetwork(SocialNetwork.FACEBOOK, "id");

        assertThat(userFound).isNotNull();
    }

    private DBObject getUserFromDB(final UUID id) {
        final DBCollection collection = getMongo().getCollection("user");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private UserRepository repository;
}
