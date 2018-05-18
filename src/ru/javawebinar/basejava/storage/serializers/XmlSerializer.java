package ru.javawebinar.basejava.storage.serializers;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlSerializer implements ResumeSerializer {
    private XmlParser xmlParser;

    public XmlSerializer() {
        xmlParser = new XmlParser(
                Resume.class, Link.class,
                AbstractSectionData.class, SectionSingle.class, SectionMultiple.class, SectionExperience.class,
                ExperienceRecord.class, ExperienceSubRecord.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }

}
