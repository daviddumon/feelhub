package com.steambeat.domain.uri;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsUriTokenizer {

    @Test
    public void canGetTokensFromHttpAddresses() {
        testUri("http://www.google.fr", "http://");
        testUri("http://www.lequipe.fr/Football/directs/Live220452.html", "http://");
        testUri("http://seriouswheels.com/cars/2010/top-2010-Aston-Martin-DBS-Volante.htm", "http://");
        testUri("http://www.lefigaro.fr/flash-actu/2011/08/17/97001-20110817FILWWW00263-gerard-depardieu-urine-dans-un-avion.php", "http://");
        testUri("http://www.europe1.fr/Economie/De-Tel-Aviv-au-Golfe-les-Bourses-chutent-659093", "http://");
        testUri("http://video.voila.fr/video/iLyROoaf2s-V.html", "http://");
        testUri("http://www.lefigaro.fr/hightech/2011/11/04/01007-20111104ARTFIG00498-virus-duqu-microsoft-prepare-un-correctif-en-urgence.php", "http://");
        testUri("http://bigbrowser.blog.lemonde.fr/2011/11/03/crise-silvio-berlusconi-reporte-la-sortie-de-son-nouvel-album-de-chansons-damour", "http://");
        testUri("http://internetactu.blog.lemonde.fr/2011/10/31/le-cerveau-objet-technologique-28-le-plus-complexe-non-ordinateur-du-monde", "http://");
        testUri("http://www.cafzone.net/2011/10/pendant-ce-temps-la-au-japon", "http://");
        testUri("http://www.youtube.com/user/PoufydeGameblog#p/u/2/xsJ0u7MIxLM", "http://");
        testUri("http://www.mongodb.org/display/docs/java+language+center", "http://");
        testUri("http://bigbrowser.blog.lemonde.fr/2011/08/01/post-it-les-open-space-parisiens-se-declarent-la-guerre", "http://");
        testUri("http://lesmoutonsenrages.fr/2011/08/10/alain-minc-le-peuple-est-responsable-de-la-crise", "http://");
        testUri("http://bigbrowser.blog.lemonde.fr/2011/08/29/tennis-loeuf-qui-a-permis-a-djokovic-de-devenir-numero-1", "http://");
        testUri("http://bigbrowser.blog.lemonde.fr/2011/09/02/gaulois-depardieu-exige-un-sanglier-dans-un-avion", "http://");
        testUri("http://elysee.blog.lemonde.fr/2011/09/16/benghazi-26-heures-de-galere-pour-2-minutes-40-de-discours-de-sarkozy", "http://");
        testUri("http://lapiflor.wordpress.com/2011/09/01/test-dell-inspiron-1210-mini", "http://");
        testUri("http://revolutionsociale.wordpress.com/2011/10/25/le-patronat-numerique-passe-encore-une-fois-a-cote-de-lhistoire", "http://");
        testUri("http://lolannonces.fr/2011/09/14/fetichiste-laine-mohair-et-laine-douce", "http://");
        testUri("http://www.youtube.com/user/joueurdugrenier#p/a/u/2/FUXQjIOnJqA", "http://");
        testUri("http://www.éléphant.com", "http://");
        testUri("http://www.lemonde.fr/myaddress/1/29?q#frag", "http://");
        testUri("http://lemonde.fr/myaddress/1/29?q#frag", "http://");
        testUri("http://lemonde.fr/myaddress/1/29?q#frag", "http://");
        testUri("http://www.lemonde.fr/myaddress/1/29?q#frag", "http://");
        testUri("http://www.lemonde.fr", "http://");
        testUri("http://lemonde.fr", "http://");
        testUri("http://lemonde.fr", "http://");
        testUri("http://www.lemonde.fr", "http://");
        testUri("http://www.lemonde.fr?q", "http://");
        testUri("http://www.mongodb.org/display/docs/java+language+center", "http://");
        testUri("http%3A%2F%2Fpute.com", "http://");
    }

    @Test
    public void canGetTokensFromHttpsAddresses() {
        testUri("https://www.google.fr", "https://");
        testUri("https://www.lequipe.fr/Football/directs/Live220452.html", "https://");
        testUri("https://seriouswheels.com/cars/2010/top-2010-Aston-Martin-DBS-Volante.htm", "https://");
        testUri("https://www.lefigaro.fr/flash-actu/2011/08/17/97001-20110817FILWWW00263-gerard-depardieu-urine-dans-un-avion.php", "https://");
        testUri("https://www.europe1.fr/Economie/De-Tel-Aviv-au-Golfe-les-Bourses-chutent-659093", "https://");
        testUri("https://video.voila.fr/video/iLyROoaf2s-V.html", "https://");
        testUri("https://www.lefigaro.fr/hightech/2011/11/04/01007-20111104ARTFIG00498-virus-duqu-microsoft-prepare-un-correctif-en-urgence.php", "https://");
        testUri("https://bigbrowser.blog.lemonde.fr/2011/11/03/crise-silvio-berlusconi-reporte-la-sortie-de-son-nouvel-album-de-chansons-damour", "https://");
        testUri("https://internetactu.blog.lemonde.fr/2011/10/31/le-cerveau-objet-technologique-28-le-plus-complexe-non-ordinateur-du-monde", "https://");
        testUri("https://www.cafzone.net/2011/10/pendant-ce-temps-la-au-japon", "https://");
        testUri("https://www.youtube.com/user/PoufydeGameblog#p/u/2/xsJ0u7MIxLM", "https://");
        testUri("https://www.mongodb.org/display/docs/java+language+center", "https://");
        testUri("https://bigbrowser.blog.lemonde.fr/2011/08/01/post-it-les-open-space-parisiens-se-declarent-la-guerre", "https://");
        testUri("https://lesmoutonsenrages.fr/2011/08/10/alain-minc-le-peuple-est-responsable-de-la-crise", "https://");
        testUri("https://bigbrowser.blog.lemonde.fr/2011/08/29/tennis-loeuf-qui-a-permis-a-djokovic-de-devenir-numero-1", "https://");
        testUri("https://bigbrowser.blog.lemonde.fr/2011/09/02/gaulois-depardieu-exige-un-sanglier-dans-un-avion", "https://");
        testUri("https://elysee.blog.lemonde.fr/2011/09/16/benghazi-26-heures-de-galere-pour-2-minutes-40-de-discours-de-sarkozy", "https://");
        testUri("https://lapiflor.wordpress.com/2011/09/01/test-dell-inspiron-1210-mini", "https://");
        testUri("https://revolutionsociale.wordpress.com/2011/10/25/le-patronat-numerique-passe-encore-une-fois-a-cote-de-lhistoire", "https://");
        testUri("https://lolannonces.fr/2011/09/14/fetichiste-laine-mohair-et-laine-douce", "https://");
        testUri("https://www.youtube.com/user/joueurdugrenier#p/a/u/2/FUXQjIOnJqA", "https://");
        testUri("https://www.éléphant.com", "https://");
        testUri("https://www.lemonde.fr/myaddress/1/29?q#frag", "https://");
        testUri("https://lemonde.fr/myaddress/1/29?q#frag", "https://");
        testUri("https://lemonde.fr/myaddress/1/29?q#frag", "https://");
        testUri("https://www.lemonde.fr/myaddress/1/29?q#frag", "https://");
        testUri("https://www.lemonde.fr", "https://");
        testUri("https://lemonde.fr", "https://");
        testUri("https://lemonde.fr", "https://");
        testUri("https://www.lemonde.fr", "https://");
        testUri("https://www.lemonde.fr?q", "https://");
        testUri("https://www.mongodb.org/display/docs/java+language+center", "https://");
        testUri("https%3A%2F%2Fpute.com", "https://");
    }

    private void testUri(final String uri, final String protocol) {
        final UriTokenizer uriTokenizer = new UriTokenizer();

        final List<String> tokens = uriTokenizer.getTokensFor(uri);

        assertThat(tokens.size(), is(2));
        assertThat(tokens.get(0), is(decode(uri)));
        assertThat(tokens.get(1), is(decode(uri).replaceFirst(protocol, "")));
    }


    @Test
    public void canLowercaseDomain() {
        final UriTokenizer uriTokenizer = new UriTokenizer();
        final String uri = "http://wwW.youtube.com/watch?abCdEf";

        final List<String> tokens = uriTokenizer.getTokensFor(uri);

        assertThat(tokens.size(), is(2));
        assertThat(tokens.get(0), is("http://www.youtube.com/watch?abCdEf"));
        assertThat(tokens.get(1), is("www.youtube.com/watch?abCdEf"));
    }

    @Test
    public void dontLowerAddressAfterDomain() {
        final UriTokenizer uriTokenizer = new UriTokenizer();
        final String uri = "http://www.youtube.com/Watch?ab";

        final List<String> tokens = uriTokenizer.getTokensFor(uri);

        assertThat(tokens.size(), is(2));
        assertThat(tokens.get(0), is("http://www.youtube.com/Watch?ab"));
        assertThat(tokens.get(1), is("www.youtube.com/Watch?ab"));
    }

    @Test
    public void canLowerWithoutQuery() {
        final UriTokenizer uriTokenizer = new UriTokenizer();
        final String uri = "http://wwW.YOUTube.com";

        final List<String> tokens = uriTokenizer.getTokensFor(uri);

        assertThat(tokens.size(), is(2));
        assertThat(tokens.get(0), is("http://www.youtube.com"));
        assertThat(tokens.get(1), is("www.youtube.com"));
    }

    @Test
    public void dontLowerAfterAnchor() {
        final UriTokenizer uriTokenizer = new UriTokenizer();
        final String uri = "http://www.youtube.com/#p/u/isruMMM";

        final List<String> tokens = uriTokenizer.getTokensFor(uri);

        assertThat(tokens.size(), is(2));
        assertThat(tokens.get(0), is("http://www.youtube.com/#p/u/isruMMM"));
        assertThat(tokens.get(1), is("www.youtube.com/#p/u/isruMMM"));
    }

    private String decode(final String uri) {
        try {
            return URLDecoder.decode(uri, "UTF-8").replace(" ", "+");
        } catch (UnsupportedEncodingException e) {
            return uri;
        }
    }
}
