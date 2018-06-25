package com.manminh.simplechem.data;

import android.content.Context;
import android.util.Pair;

import com.manminh.simplechem.ui.activityseries.ElementActivityInfo;
import com.manminh.simplechem.ui.electseries.ElectElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlDataManager {

    public static List<ElectElement> getElectrochemicalSeries(Context context) {
        List<ElectElement> res = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("electrochemical_series.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nodes = doc.getElementsByTagName("element");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                Element e = (Element) node;
                ElectElement elecElement = new ElectElement();
                elecElement.Name = e.getAttribute("name");
                elecElement.Sup = e.getAttribute("sup");
                elecElement.Value = e.getAttribute("value");
                res.add(elecElement);
            }

        } catch (Exception e) {
            return null;
        }
        return res;
    }

    public static Set<String> getElementSymbols(Context context) {
        Set<String> res = new HashSet<>();
        try {
            InputStream is = context.getAssets().open("elements.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nodes = doc.getElementsByTagName("item");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node e = nodes.item(i).getFirstChild();
                res.add(e.getNodeValue());
            }

        } catch (Exception e) {
            return null;
        }
        return res;
    }

    public static List<ElementActivityInfo> getActivitySeries(Context context) {
        List<ElementActivityInfo> res = new ArrayList<>();
        Map<Pair<String, String>, String> infoRef = createInfoRef(context);
        try {
            InputStream is = context.getAssets().open("activity_series.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nodes = doc.getElementsByTagName("element");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                Element e = (Element) node;
                ElementActivityInfo info = new ElementActivityInfo(e.getAttribute("symbol"));
                info.addInfo(infoRef.get(new Pair<>("type", e.getAttribute("type"))));
                info.addInfo(infoRef.get(new Pair<>("water", e.getAttribute("water"))));
                info.addInfo(infoRef.get(new Pair<>("kick", e.getAttribute("kick"))));
                info.addInfo(infoRef.get(new Pair<>("axit", e.getAttribute("axit"))));
                info.addInfo(infoRef.get(new Pair<>("oxit", e.getAttribute("oxit"))));
                res.add(info);
            }
        } catch (Exception e) {
            return null;
        }
        return res;
    }

    private static Map<Pair<String, String>, String> createInfoRef(Context context) {
        Map<Pair<String, String>, String> res = new HashMap<>();
        try {
            InputStream is = context.getAssets().open("activity_series_info.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nodes = doc.getElementsByTagName("item");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element e1 = (Element) nodes.item(i);
                String attr = e1.getAttribute("name");
                NodeList childs = e1.getElementsByTagName("if");
                for (int j = 0; j < childs.getLength(); j++) {
                    Element e2 = (Element) childs.item(j);
                    Pair<String, String> p = new Pair<>(attr, e2.getAttribute("value"));
                    String content = childs.item(j).getFirstChild().getNodeValue();
                    res.put(p, content);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return res;
    }
}
