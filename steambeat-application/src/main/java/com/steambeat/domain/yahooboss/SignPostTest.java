package com.steambeat.domain.yahooboss;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import java.io.*;
import java.net.URLEncoder;

public class SignPostTest {

    protected static String yahooServer = "http://yboss.yahooapis.com/ysearch/";
    private static String consumer_key = "";
    private static String consumer_secret = "";
    private static StHttpRequest httpRequest = new StHttpRequest();
    private static final String callType = "web";
    private static final int HTTP_STATUS_OK = 200;

    public enum services {
        web, limitedweb, images, news, blogs, ads
    }

    public int returnHttpData()
            throws UnsupportedEncodingException,
            Exception {

        String params = callType;
        params = params.concat("?q=");
        params = params.concat(URLEncoder.encode("yahoo", "UTF-8"));
        String url = yahooServer + params;
        OAuthConsumer consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);
        httpRequest.setOAuthConsumer(consumer);

        try {
            int responseCode = httpRequest.sendGetRequest(url);
            if (responseCode != HTTP_STATUS_OK) {
                System.out.println("Error in response due to status code = " + responseCode);
            }
            System.out.println(httpRequest.getResponseBody());

        } catch (UnsupportedEncodingException e) {
            System.out.println("Encoding/Decording error");
        } catch (IOException e) {
            System.out.println("Error with HTTP IO");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(httpRequest.getResponseBody());
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
