package com.steambeat.tools;

import org.jsoup.nodes.Document;

import java.io.InputStream;

public class HtmlParser {

    public void parse() {
//        final Client client = Clients.create();
//        final Request request = Requests.create(Method.GET, association.getCanonicalUri());
//        final Response response = client.handle(request);
//        try {
//            String encoding = "";
//            final String baseUri = "";
////            InputStream stream = response.getEntity().getStream();
//            if (response.getEntity().getCharacterSet() == null) {
////                int length = new Long(response.getEntity().getSize()).intValue();
////                encoding = guessEncoding(length, stream);
//                encoding = null;
//            } else {
//                encoding = response.getEntity().getCharacterSet().toString();
//            }
////            document = Jsoup.parse(stream, encoding, baseUri);
//            document = Jsoup.parse(response.getEntity().getStream(), encoding, baseUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private String guessEncoding(final int length, final InputStream stream) {
//        try {
//            if (length > 0) {
//                UniversalDetector detector = new UniversalDetector(null);
//                byte[] buf = new byte[length];
//                int nread = stream.read(buf);
//                detector.handleData(buf, 0, nread);
//                detector.dataEnd();
//                if (detector.getDetectedCharset() != null) {
//                    return detector.getDetectedCharset();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "UTF-8";
    }

    public String getSingleNode(final String tag) {
        //final Element elementTag = document.select(tag).first();
        //if (elementTag != null) {
        //    return elementTag.text();
        //}
        return "";
    }

    private Document document;
}
