package com.wydpp.jaxb.xml;

import javax.xml.stream.XMLStreamWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CDataHandler implements InvocationHandler {
    // *) 单独拦截 writeCharacters(String)方法
    private static Method gWriteCharactersMethod = null;

    static {
        try {
            gWriteCharactersMethod = XMLStreamWriter.class
                    .getDeclaredMethod("writeCharacters", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private XMLStreamWriter writer;

    public CDataHandler(XMLStreamWriter writer) {
        this.writer = writer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equalsIgnoreCase("writeCharacters")) {
            String text = new String((char[]) args[0]);
            // *) 遇到CDATA标记时, 则转而调用writeCData方法
            if (text != null && text.startsWith("<![CDATA[") && text.endsWith("]]>")) {
                writer.writeCData(text.substring(9, text.length() - 3));
                return null;
            }
        }
        return method.invoke(writer, args);
    }
}
