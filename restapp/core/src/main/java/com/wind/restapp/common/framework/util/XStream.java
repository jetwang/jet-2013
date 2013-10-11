package com.wind.restapp.common.framework.util;

import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import org.apache.log4j.Logger;
import org.dom4j.io.OutputFormat;

/**
 * @author Jet
 */
public class XStream {
    public static final Logger logger = Logger.getLogger(XStream.class);


    public static com.thoughtworks.xstream.XStream xml;
    public static com.thoughtworks.xstream.XStream json;

    static {
        Dom4JDriver driver = new Dom4JDriver();
        driver.setOutputFormat(OutputFormat.createPrettyPrint());
        xml = new com.thoughtworks.xstream.XStream(driver);
        xml.autodetectAnnotations(true);
        xml.setMode(com.thoughtworks.xstream.XStream.XPATH_RELATIVE_REFERENCES);
        json = new com.thoughtworks.xstream.XStream(new JsonHierarchicalStreamDriver());
        json.setMode(com.thoughtworks.xstream.XStream.NO_REFERENCES);
        json.autodetectAnnotations(true);
    }
}

