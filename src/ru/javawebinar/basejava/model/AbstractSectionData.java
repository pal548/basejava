package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractSectionData implements Serializable {

    public abstract void readFromDataStream(DataInputStream dis) throws IOException;
}
