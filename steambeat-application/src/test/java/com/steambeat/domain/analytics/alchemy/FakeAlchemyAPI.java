package com.steambeat.domain.analytics.alchemy;

import com.alchemyapi.api.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class FakeAlchemyAPI extends AlchemyAPI{

    public FakeAlchemyAPI(final Document document) {
        super();
        this.document = document;
    }

    @Override
    protected Document GET(final String callName, final String callPrefix, final AlchemyAPI_Params params) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        return document;
    }

    private Document document;
}
