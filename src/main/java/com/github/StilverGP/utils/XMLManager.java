package com.github.StilverGP.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;

public class XMLManager {
    /**
     * Reads an XML file and unmarshals it into an object of the specified class type.
     *
     * @param c         the class type of the object to be created.
     * @param filename  the InputStream of the XML file to be read.
     * @param <T>       the type of the object to be created.
     * @return an object of the specified class type containing the data from the XML file.
     */
    public static<T> T readXML(T c, InputStream filename){
        T result = c;
        JAXBContext context;

        try{
            context = JAXBContext.newInstance(c.getClass());
            Unmarshaller um = context.createUnmarshaller();
            result = (T) um.unmarshal(filename);
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return result;
    }
}
