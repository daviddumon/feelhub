package com.steambeat.tools;

import org.restlet.Response;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.IOException;

public class XmlDomParser {

    public static XmlDomParser createNew(final Response response) {
        return new XmlDomParser(response);
    }

    private XmlDomParser(final Response response) {
        try {
            final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.parse(response.getEntity().getStream());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object evaluate(final String expression, final QName type) {
        final XPath xpath = XmlDomParser.xPathFactory.newXPath();
        try {
            return xpath.evaluate(expression, document, type);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRootNode() {
        return document.getDocumentElement().getNodeName();
    }

    private XmlDomParser() {

    }

    private Document document;

    private static final XPathFactory xPathFactory = XPathFactory.newInstance();
}
