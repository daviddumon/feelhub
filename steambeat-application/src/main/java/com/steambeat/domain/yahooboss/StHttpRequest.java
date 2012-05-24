package com.steambeat.domain.yahooboss;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.*;

import java.io.*;
import java.net.*;

public class StHttpRequest {

    private String responseBody = "";
    private OAuthConsumer consumer = null;

    public StHttpRequest() {
    }

    public HttpURLConnection getConnection(String url)
            throws IOException,
            OAuthMessageSignerException,
            OAuthExpectationFailedException,
            OAuthCommunicationException {
        try {
            URL u = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            try {
                System.out.println("Signing the oAuth consumer");
                consumer.sign(uc);

            } catch (OAuthMessageSignerException e) {
                System.out.println("Error signing the consumer");
                e.printStackTrace();
                throw e;

            } catch (OAuthExpectationFailedException e) {
                System.out.println("Error signing the consumer");
                e.printStackTrace();
                throw e;

            } catch (OAuthCommunicationException e) {
                System.out.println("Error signing the consumer");
                e.printStackTrace();
                throw e;
            }
            uc.connect();
            return uc;
        } catch (IOException e) {
            System.out.println("Error signing the consumer");
            e.printStackTrace();
            throw e;
        }
    }

    public int sendGetRequest(String url)
            throws IOException,
            OAuthMessageSignerException,
            OAuthExpectationFailedException,
            OAuthCommunicationException {


        int responseCode = 500;
        try {
            HttpURLConnection uc = getConnection(url);
            responseCode = uc.getResponseCode();
            if (200 == responseCode || 401 == responseCode || 404 == responseCode) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(responseCode == 200 ? uc.getInputStream() : uc.getErrorStream()));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                setResponseBody(sb.toString());
            }
        } catch (MalformedURLException ex) {
            throw new IOException(url + " is not valid");
        } catch (IOException ie) {
            throw new IOException("IO Exception " + ie.getMessage());
        }
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        if (null != responseBody) {
            this.responseBody = responseBody;
        }
    }

    public void setOAuthConsumer(OAuthConsumer consumer) {
        this.consumer = consumer;
    }
}