package com.MegaKnytes.DecisionTable;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.MegaKnytes.DecisionTable.drivers.common.CRServoDriver;
import com.MegaKnytes.DecisionTable.drivers.common.GamepadDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.MegaKnytes.DecisionTable.drivers.DTDriver;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLProcessor {

    private final OpMode opMode;

    public XMLProcessor(OpMode opMode) {
        this.opMode = opMode;
    }

    public <T extends DTDriver> Map<String, T> processXML(Document document, HashMap<String, Class<? extends DTDriver>> driverClasses) {
        Map<String, T> initializedDevices = new HashMap<>();

        try {
            document.getDocumentElement().normalize();
            NodeList deviceNodes = document.getElementsByTagName("Devices").item(0).getChildNodes();

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
                        HashMap<String, Object> configOptions = getDeviceConfig(driverElement);

                        T driverInstance = initializeDriver(driverName, deviceName, configOptions, (List<Class<? extends T>>) driverClasses);
                        if (driverInstance != null) {
                            initializedDevices.put(deviceName, driverInstance);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return initializedDevices;
    }

    private HashMap<String, Object> getDeviceConfig(Element driverElement) {
        HashMap<String, Object> configOptions = new HashMap<>();
        NodeList options = driverElement.getChildNodes();

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

    private <T extends DTDriver> T initializeDriver(String driverName, String deviceName, HashMap<String, Object> configOptions, List<Class<? extends T>> driverClasses) {
        try {
            for (Class<? extends T> driverClass : driverClasses) {
                if (driverClass.getSimpleName().equalsIgnoreCase(driverName)) {
                    T driverInstance = driverClass.getDeclaredConstructor().newInstance();
                    driverInstance.setup(opMode, deviceName, configOptions);

                    return driverInstance;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return null;
    }
}
