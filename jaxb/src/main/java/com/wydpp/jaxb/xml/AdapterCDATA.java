package com.wydpp.jaxb.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Proxy;

public class AdapterCDATA extends XmlAdapter<String, String> {
    @Override
    public String marshal(String arg0) throws Exception {
        return "<![CDATA[" + arg0 + "]]>";
    }
    @Override
    public String unmarshal(String arg0) throws Exception {
        return arg0;
    }

    public static void main(String[] args) {
        StringWriter writer = new StringWriter();
        XMLStreamWriter streamWriter = null;
        try {
            streamWriter = XMLOutputFactory.newInstance()
                    .createXMLStreamWriter(writer);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        XMLStreamWriter cdataStreamWriter = (XMLStreamWriter) Proxy.newProxyInstance(
                streamWriter.getClass().getClassLoader(),
                streamWriter.getClass().getInterfaces(),
                new CDataHandler(streamWriter)
        );
    }
}
