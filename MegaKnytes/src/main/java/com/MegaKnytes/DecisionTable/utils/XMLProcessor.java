package com.MegaKnytes.DecisionTable.utils;

import org.w3c.dom.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.MegaKnytes.DecisionTable.drivers.DTPDriver;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLProcessor {

    public <T extends DTPDriver> HashMap<String, HashMap<T, HashMap<String, Object>>> processXML(File file, HashMap<String, Class<? extends DTPDriver>> driverClasses) {
        HashMap<String, HashMap<T, HashMap<String, Object>>> processedDTP = new HashMap<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);

            document.getDocumentElement().normalize();

            NodeList deviceNodes = document.getElementsByTagName("Devices").item(0).getChildNodes();

            return processDeviceNode(deviceNodes, driverClasses);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T extends DTPDriver> HashMap<String, HashMap<T, HashMap<String, Object>>> processDeviceNode(NodeList deviceNodes, HashMap<String, Class<? extends DTPDriver>> driverClasses){
        HashMap<String, HashMap<T, HashMap<String, Object>>> initializedDevices = new HashMap<>();

        for (int i = 0; i < deviceNodes.getLength(); i++) {
            Node deviceNode = deviceNodes.item(i);

            if (deviceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element deviceElement = (Element) deviceNode;
                String deviceName = deviceElement.getTagName();

                Node driverNode = deviceElement.getFirstChild();

                while (driverNode != null && driverNode.getNodeType() != Node.ELEMENT_NODE) {
                    driverNode = driverNode.getNextSibling();
                }

                if (driverNode != null) {
                    Element driverElement = (Element) driverNode;
                    String driverName = driverElement.getTagName();

                    try {
                        for (Class<? extends DTPDriver> driverClass : driverClasses.values()) {
                            if (driverClass.getSimpleName().equalsIgnoreCase(driverName)) {
                                HashMap<String, Object> deviceConfig = getConfigOptions(driverElement);
                                HashMap<T, HashMap<String, Object>> compiledDevice = new HashMap<>();
                                T driverInstance = (T) driverClass.getDeclaredConstructor().newInstance();
                                compiledDevice.put(driverInstance, deviceConfig);
                                initializedDevices.put(deviceName, compiledDevice);
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return initializedDevices;
    }

    private HashMap<String, Object> getConfigOptions(Element element) {
        HashMap<String, Object> configOptions = new HashMap<>();
        NodeList options = element.getChildNodes();

        for (int j = 0; j < options.getLength(); j++) {
            Node optionNode = options.item(j);

            if (optionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element optionElement = (Element) optionNode;
                String optionName = optionElement.getTagName();
                String optionValue = optionElement.getTextContent().trim();
                configOptions.put(optionName, optionValue);
            }
        }
        return configOptions;
    }
}
