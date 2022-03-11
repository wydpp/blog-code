package com.wydpp.jaxb.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Proxy;

/**
 * @author wydpp
 */
@XmlRootElement
public class UserBean {

    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String address;

    public void setAddress(String address) {
        this.address = address;
    }

    public static void main(String[] args) throws Exception {
        StringWriter writer = new StringWriter();
        XMLStreamWriter streamWriter = XMLOutputFactory.newInstance()
                .createXMLStreamWriter(writer);
        UserBean userBean = new UserBean();
        userBean.setAddress("<xml ?><address>合肥市</address></xml>");
        System.out.println();
        JAXBContext context = JAXBContext.newInstance(UserBean.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(userBean, new MyXMLStreamWriter(streamWriter));
        //marshaller.marshal(userBean, System.out);
        System.out.println(writer);
    }
}
