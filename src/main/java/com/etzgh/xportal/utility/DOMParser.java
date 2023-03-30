/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import java.io.StringReader;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author samuel.onwona
 */
public class DOMParser {

    Document document;

    public static void main(String[] args) {
        try {

            String xml = "<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:processResponse xmlns:ns2=\"http://ws.fundgate.etranzact.com/\"><response><direction>response</direction><action>VT</action><reference>09FG08221535504042265</reference><amount>0.0</amount><totalFailed>0</totalFailed><totalSuccess>0</totalSuccess><error>0</error><message>Transaction Successfull Ref:09FG08221535504042265</message><otherReference>02AT463942</otherReference><openingBalance>0.0</openingBalance><closingBalance>0.0</closingBalance></response></ns2:processResponse></S:Body></S:Envelope>";
            String error = getElementValue(xml, "error");

            System.out.println(error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getElementValue(String xml, String tag) {
        String elementValue = "";
        try {
            DOMParser domParser = new DOMParser(xml);
            elementValue = domParser.getNodeValue(tag, domParser.getDocument().getElementsByTagName(tag));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return elementValue;
    }

    public DOMParser(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature(XMLConstants.ACCESS_EXTERNAL_DTD, Boolean.FALSE);
        DocumentBuilder db = factory.newDocumentBuilder();

        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlString));
        document = (Document) db.parse(is);

    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    protected Node getNode(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                return node;
            }
        }
        return null;
    }

    protected String getNodeValue(Node node) {
        NodeList childNodes = node.getChildNodes();
        for (int x = 0; x < childNodes.getLength(); x++) {
            Node data = childNodes.item(x);
            if (data.getNodeType() == Node.TEXT_NODE) {
                return data.getNodeValue();
            }
        }
        return "";
    }

    protected String getNodeValue(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                NodeList childNodes = node.getChildNodes();
                for (int y = 0; y < childNodes.getLength(); y++) {
                    Node data = childNodes.item(y);
                    if (data.getNodeType() == Node.TEXT_NODE) {
                        return data.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    protected String getNodeAttr(String attrName, Node node) {
        NamedNodeMap attrs = node.getAttributes();
        for (int y = 0; y < attrs.getLength(); y++) {
            Node attr = attrs.item(y);
            if (attr.getNodeName().equalsIgnoreCase(attrName)) {
                return attr.getNodeValue();
            }
        }
        return "";
    }

    protected String getNodeAttr(String tagName, String attrName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                NodeList childNodes = node.getChildNodes();
                for (int y = 0; y < childNodes.getLength(); y++) {
                    Node data = childNodes.item(y);
                    if (data.getNodeType() == Node.ATTRIBUTE_NODE && data.getNodeName().equalsIgnoreCase(attrName)) {
                        return data.getNodeValue();
                    }
                }
            }
        }

        return "";
    }
}
