package com.steambeat.sitemap.tools;

import org.w3c.dom.Document;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.zip.*;

public class XmlTransformer {

    public void writeToFile(File file, final Document xml) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source xmlSource = new DOMSource(xml);
            FileOutputStream fileStream = new FileOutputStream(file);
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new BufferedOutputStream(fileStream));
            StreamResult result = new StreamResult(gzipOutputStream);
            transformer.transform(xmlSource, result);
            gzipOutputStream.close();
        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public Document readFromFile(File file) {
        try {
            Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            FileInputStream fileInputStream = new FileInputStream(file);
            GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
            StreamSource source = new StreamSource(gzipInputStream);
            DOMResult result = new DOMResult(xml);
            transformer.transform(source, result);
            gzipInputStream.close();
            return xml;
        } catch (TransformerException e) {
        } catch (ParserConfigurationException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return null;
    }

}
