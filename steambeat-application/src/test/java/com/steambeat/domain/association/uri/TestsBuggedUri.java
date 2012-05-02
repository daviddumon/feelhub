package com.steambeat.domain.association.uri;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsBuggedUri {

    @Test
    public void testBuggedUri() {
        final List<String> uris = Lists.newArrayList();
        uris.add("http://www.lequipe.fr/Football/directs/Live220452.html");
        uris.add("http://seriouswheels.com/cars/2010/top-2010-Aston-Martin-DBS-Volante.htm");
        uris.add("http://www.lefigaro.fr/flash-actu/2011/08/17/97001-20110817FILWWW00263-gerard-depardieu-urine-dans-un-avion.php");
        uris.add("http://www.europe1.fr/Economie/De-Tel-Aviv-au-Golfe-les-Bourses-chutent-659093");
        uris.add("http://video.voila.fr/video/iLyROoaf2s-V.html");
        uris.add("http://www.lefigaro.fr/hightech/2011/11/04/01007-20111104ARTFIG00498-virus-duqu-microsoft-prepare-un-correctif-en-urgence.php");
        uris.add("http://bigbrowser.blog.lemonde.fr/2011/11/03/crise-silvio-berlusconi-reporte-la-sortie-de-son-nouvel-album-de-chansons-damour");
        uris.add("http://internetactu.blog.lemonde.fr/2011/10/31/le-cerveau-objet-technologique-28-le-plus-complexe-non-ordinateur-du-monde");
        uris.add("http://www.cafzone.net/2011/10/pendant-ce-temps-la-au-japon");
        uris.add("http://www.youtube.com/user/PoufydeGameblog#p/u/2/xsJ0u7MIxLM");
        uris.add("http://www.mongodb.org/display/docs/java+language+center");
        uris.add("http://bigbrowser.blog.lemonde.fr/2011/08/01/post-it-les-open-space-parisiens-se-declarent-la-guerre");
        uris.add("http://lesmoutonsenrages.fr/2011/08/10/alain-minc-le-peuple-est-responsable-de-la-crise");
        uris.add("http://bigbrowser.blog.lemonde.fr/2011/08/29/tennis-loeuf-qui-a-permis-a-djokovic-de-devenir-numero-1");
        uris.add("http://bigbrowser.blog.lemonde.fr/2011/09/02/gaulois-depardieu-exige-un-sanglier-dans-un-avion");
        uris.add("http://elysee.blog.lemonde.fr/2011/09/16/benghazi-26-heures-de-galere-pour-2-minutes-40-de-discours-de-sarkozy");
        uris.add("http://lapiflor.wordpress.com/2011/09/01/test-dell-inspiron-1210-mini");
        uris.add("http://revolutionsociale.wordpress.com/2011/10/25/le-patronat-numerique-passe-encore-une-fois-a-cote-de-lhistoire");
        uris.add("http://lolannonces.fr/2011/09/14/fetichiste-laine-mohair-et-laine-douce");
        uris.add("http://www.youtube.com/user/joueurdugrenier#p/a/u/2/FUXQjIOnJqA");
        uris.add("http://www.éléphant.com");

        for (final String uri : uris) {
            testUri(uri);
        }
    }

    private void testUri(final String address) {
        final Uri uri = new Uri(address);
        assertThat(uri.toString(), is(address));
    }

}
