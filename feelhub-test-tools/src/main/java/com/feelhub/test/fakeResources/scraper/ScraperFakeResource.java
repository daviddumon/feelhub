package com.feelhub.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class ScraperFakeResource extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html lang='fr'>" +
                "<head>" +
                "<meta name='description' content='description meta'/>" +
                "<meta name='subject' content='subject meta'/>" +
                "<meta name='topic' content='topic meta'/>" +
                "<meta name='summary' content='summary meta'/>" +
                "<meta name='author' content='author'/>" +
                "<meta name='designer' content='designer'/>" +
                "<meta name='owner' content='owner'/>" +
                "<meta http-equiv='Content-Language' content='en'/>" +
                "<meta property='og:title' content='name og'/>" +
                "<meta property='og:type' content='article'/>" +
                "<meta property='og:description' content='description og'/>" +
                "<meta property='og:description' content='description og'/>" +
                "<meta property='og:image' content = 'http://s1.lemde.fr/image/2013/01/25/540x270/1822831_3_dfb7_un-manifestant-lance-un-cocktail-molotov-contre_ed5d9c3af6a609128210a9cab7111290.jpg' >" +
                "<meta property='og:image:height' content = '270' >" +
                "<meta property='og:image:width' content = '540' >" +
                "<meta property='og:image:type' content = 'image/jpeg' >" +
                "<meta property='og:image' content = 'http://s2.lemde.fr/image/2013/01/25/540x270/1822833_3_b295_manifestants-sur-la-place-tahrir-au-caire-le_f64679beedbe1a8d5c82d59d7017158e.jpg' >" +
                "<meta property='og:image:height' content = '270' >" +
                "<meta property='og:image:width' content = '540' >" +
                "<meta property='og:image:type' content = 'image/jpeg' >" +
                "<meta property='og:image' content = 'http://s1.lemde.fr/image/2013/01/25/540x270/1822858_3_d137_un-manifestant-jette-des-pierres-aux-forces-de_2dd4fab97a3041b74268b09b5d9797b7.jpg' >" +
                "<meta property='og:image:height' content = '270' >" +
                "<meta property='og:image:width' content = '540' >" +
                "<meta property='og:image:type' content = 'image/jpeg' >" +
                "<meta property='og:image' content = 's2.lemde.fr/image/2013/01/25/540x270/1822690_3_806d_des-manifestants-vendredi-place-tahrir_16eb8d24bab5c145155fe476c1f0ce46.jpg' >" +
                "<meta property='og:image:height' content = '270' >" +
                "<meta property='og:image:width' content = '540' >" +
                "<meta property='og:image:type' content = 'image/jpeg' >" +
                "<meta property='og:image' content = '/image/2013/01/25/540x270/1822689_3_8a6a_un-jeune-manifestant-vendredi-place-tahrir_463393f7fa4747ea1908e251224820e7.jpg' >" +
                "<meta property='og:image:height' content = '270' >" +
                "<meta property='og:image:width' content = '540' >" +
                "<meta property='og:image:type' content = 'image/jpeg' >" +
                "<meta property='og:image' content = '' >" +
                "<meta property='og:video' content='http://www.youtube.com/v/-7RY95j4lw0?version=3&amp;autohide=1'>" +
                "<meta property='og:video:type' content='application/x-shockwave-flash'>" +
                "<meta property='og:video:width' content='1920'>" +
                "<meta property='og:video:height' content='1080'>" +
                "<meta property='og:audio' content='http://example.com/bond/theme.mp3' />" +
                "<title>name title</title>" +
                "</head>" +
                "<body>" +
                "<h1>name h1</h1>" +
                "<h2>name h2</h2>" +
                "<h3>name h3</h3>" +
                "</body>" +
                "</html>";
        return new StringRepresentation(html);
    }
}
