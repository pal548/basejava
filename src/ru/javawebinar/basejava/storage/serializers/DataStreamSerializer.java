package ru.javawebinar.basejava.storage.serializers;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.AbstractSectionData;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public class DataStreamSerializer implements ResumeSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Set<Map.Entry<ContactType, String>> set = r.getContacts().entrySet();
            dos.writeInt(set.size());
            for (Map.Entry<ContactType, String> e : set) {
                dos.writeUTF(e.getKey().name());
                dos.writeUTF(e.getValue());
            }

            Set<Map.Entry<SectionType, AbstractSectionData>> sectionSet = r.getSections().entrySet();
            dos.writeInt(sectionSet.size());
            for (Map.Entry<SectionType, AbstractSectionData> e : sectionSet) {
                dos.writeUTF(e.getKey().name());
                dos.writeUTF(e.getValue().getClass().getName());
                e.getValue().writeToDataStream(dos);
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Class<?> clazz = Class.forName(dis.readUTF());
                AbstractSectionData section = (AbstractSectionData) clazz.getConstructor().newInstance();
                section.readFromDataStream(dis);
                resume.addSection(sectionType, section);
            }


            return resume;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new StorageException("Deserialization error", null, e);
        }
    }
}
