package com.steambeat.domain.subject.uri;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsBuggedUri {

    @Test
    public void testBuggedUri() {
        testUri("http://www.lequipe.fr/Football/directs/Live220452.html");
        testUri("http://seriouswheels.com/cars/2010/top-2010-Aston-Martin-DBS-Volante.htm");
        testUri("http://www.lefigaro.fr/flash-actu/2011/08/17/97001-20110817FILWWW00263-gerard-depardieu-urine-dans-un-avion.php");
        testUri("http://www.europe1.fr/Economie/De-Tel-Aviv-au-Golfe-les-Bourses-chutent-659093");
        testUri("http://video.voila.fr/video/iLyROoaf2s-V.html");
        testUri("http://www.lefigaro.fr/hightech/2011/11/04/01007-20111104ARTFIG00498-virus-duqu-microsoft-prepare-un-correctif-en-urgence.php");
        testUri("http://bigbrowser.blog.lemonde.fr/2011/11/03/crise-silvio-berlusconi-reporte-la-sortie-de-son-nouvel-album-de-chansons-damour");
        testUri("http://internetactu.blog.lemonde.fr/2011/10/31/le-cerveau-objet-technologique-28-le-plus-complexe-non-ordinateur-du-monde");
        testUri("http://www.cafzone.net/2011/10/pendant-ce-temps-la-au-japon");
        testUri("http://www.youtube.com/user/PoufydeGameblog#p/u/2/xsJ0u7MIxLM");
        testUri("http://www.mongodb.org/display/docs/java+language+center");
        testUri("http://bigbrowser.blog.lemonde.fr/2011/08/01/post-it-les-open-space-parisiens-se-declarent-la-guerre");
        testUri("http://lesmoutonsenrages.fr/2011/08/10/alain-minc-le-peuple-est-responsable-de-la-crise");
        testUri("http://bigbrowser.blog.lemonde.fr/2011/08/29/tennis-loeuf-qui-a-permis-a-djokovic-de-devenir-numero-1");
        testUri("http://bigbrowser.blog.lemonde.fr/2011/09/02/gaulois-depardieu-exige-un-sanglier-dans-un-avion");
        testUri("http://elysee.blog.lemonde.fr/2011/09/16/benghazi-26-heures-de-galere-pour-2-minutes-40-de-discours-de-sarkozy");
        testUri("http://lapiflor.wordpress.com/2011/09/01/test-dell-inspiron-1210-mini");
        testUri("http://revolutionsociale.wordpress.com/2011/10/25/le-patronat-numerique-passe-encore-une-fois-a-cote-de-lhistoire");
        testUri("http://lolannonces.fr/2011/09/14/fetichiste-laine-mohair-et-laine-douce");
        testUri("http://www.youtube.com/user/joueurdugrenier#p/a/u/2/FUXQjIOnJqA");
        testUri("http://www.éléphant.com");
    }

    private void testUri(final String address) {
        final Uri uri = new Uri(address);
        assertThat(uri.toString(), is(address));
    }

}
