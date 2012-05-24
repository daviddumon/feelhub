package com.steambeat.domain.yahooboss;

import com.steambeat.domain.subject.concept.Concept;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.YahooApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import java.io.*;
import java.net.*;

public class YahooBossLink {

    public String getIllustration(final Concept concept) {
        //try {
        //    OAuthConsumer consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);
        //    URL url = new URL(getUrlRequestForConcept(concept));
        //    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        //    httpURLConnection.setDoOutput(true);
        //    consumer.sign(httpURLConnection);
        //    httpURLConnection.connect();
        //    return unmarshall(httpURLConnection.getInputStream());
        //} catch (UnsupportedEncodingException e) {
        //    e.printStackTrace();
        //} catch (MalformedURLException e) {
        //    e.printStackTrace();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //} catch (OAuthExpectationFailedException e) {
        //    e.printStackTrace();
        //} catch (OAuthCommunicationException e) {
        //    e.printStackTrace();
        //} catch (OAuthMessageSignerException e) {
        //    e.printStackTrace();
        //}
        final OAuthService service = new ServiceBuilder()
                .provider(YahooApi.class)
                .apiKey(consumer_key)
                .apiSecret(consumer_secret)
                .build();
        System.out.println("=== Yahoo's OAuth Workflow ===");
        System.out.println();

        // Obtain the Request Token
        System.out.println("Fetching the Request Token...");
        Token requestToken = service.getRequestToken();
        System.out.println("Got the Request Token!");
        System.out.println();

        System.out.println("Now go and authorize Scribe here:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        System.out.println("And paste the verifier here");
        System.out.print(">>");
        Verifier verifier = new Verifier("steambeat");
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        Token accessToken = service.getAccessToken(requestToken, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken + " )");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        String request = "";
        try {
            request = getUrlRequestForConcept(concept);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, request);
        service.signRequest(accessToken, oAuthRequest);
        Response response = oAuthRequest.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());

        System.out.println();
        System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
        return "";
    }

    private String getUrlRequestForConcept(final Concept concept) throws UnsupportedEncodingException, MalformedURLException {
        final StringBuilder url = new StringBuilder();
        url.append(yahooServer);
        url.append(URLEncoder.encode(concept.getDescription(), "UTF-8"));
        url.append(requestOptions);
        return url.toString();
    }

    private String unmarshall(final InputStream stream) {
        //final ObjectMapper objectMapper = new ObjectMapper();
        //List<NamedEntity> results = Lists.newArrayList();
        //try {
        //final AlchemyJsonResults alchemyJsonResults = objectMapper.readValue(stream, AlchemyJsonResults.class);
        //for (AlchemyJsonEntity entity : alchemyJsonResults.entities) {
        //    entity.language = alchemyJsonResults.language;
        //    results.add(namedEntityBuilder.build(entity));
        //}
        //return results;
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        System.out.println(stream);
        return "";
    }

    private static String yahooServer = "http://yboss.yahooapis.com/ysearch/images?q=";
    private static String requestOptions = "&count=1&filter=no&dimensions=medium";
    private static String consumer_key = "dj0yJmk9azlHeVN3VVFIeDcxJmQ9WVdrOWJXMXNXazVxTjJNbWNHbzlNVEEwTmpZME1UazJNZy0tJnM9Y29uc3VtZXJzZWNyZXQmeD1iYQ--";
    //private static String consumer_key = "dj0yJmk9ejEzbTZDMHFxSnpNJmQ9WVdrOVRXSjJTalk1Tm1VbWNHbzlNemt4T0RNek16WXkmcz1jb25zdW1lcnNlY3JldCZ4PWVm--";
    private static String consumer_secret = "2e2e098338a7f7323255c37f81b929ecc2764f7b";
    //private static String consumer_secret = "68a7f83cdb8e68ef31ef4da92bbb2768cba3b87e";
}
