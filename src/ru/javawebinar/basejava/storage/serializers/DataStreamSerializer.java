package ru.javawebinar.basejava.storage.serializers;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements ResumeSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeList(dos, r.getContacts().entrySet(), e -> {
                dos.writeUTF(e.getKey().name());
                dos.writeUTF(e.getValue());
            });

            writeList(dos, r.getSections().entrySet(), e -> {
                dos.writeUTF(e.getKey().name());
                AbstractSectionData section = e.getValue();
                dos.writeUTF(section.getClass().getName());
                if (section instanceof SectionSingle) {
                    dos.writeUTF(((SectionSingle) section).getValue());
                } else if (section instanceof SectionMultiple) {
                    SectionMultiple secMult = (SectionMultiple) section;
                    writeList(dos, secMult.getStrings(), dos::writeUTF);
                } else if (section instanceof SectionExperience) {
                    SectionExperience secExp = (SectionExperience) section;
                    writeList(dos, secExp.getExperienceList(), er -> {
                        dos.writeUTF(er.getCompany().getName());
                        dos.writeUTF(er.getCompany().getUrl());
                        writeList(dos, er.getListExperience(), esr -> {
                            dos.writeUTF(esr.getDateBeg().toString());
                            dos.writeUTF(esr.getDateEnd() == null ? "null" : esr.getDateEnd().toString());
                            dos.writeUTF(esr.getPosition());
                            dos.writeUTF(esr.getDescription());
                        });
                    });
                }

            });
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
                if (section instanceof SectionSingle) {
                    SectionSingle secSingle = (SectionSingle) section;
                    secSingle.setValue(dis.readUTF());
                } else if (section instanceof SectionMultiple) {
                    SectionMultiple sectionMultiple = (SectionMultiple) section;
                    readList(dis, sectionMultiple.getStrings(), dis::readUTF);
                } else if (section instanceof SectionExperience) {
                    SectionExperience secExp = (SectionExperience) section;
                    readList(dis, secExp.getExperienceList(), () -> {
                        ExperienceRecord er = new ExperienceRecord();
                        er.setCompany(new Link(dis.readUTF(), dis.readUTF()));
                        readList(dis, er.getListExperience(), () -> {
                            ExperienceSubRecord esr = new ExperienceSubRecord();
                            esr.setDateBeg(LocalDate.parse(dis.readUTF()));
                            String dend = dis.readUTF();
                            if (!Objects.equals(dend, "null")) {
                                esr.setDateEnd(LocalDate.parse(dend));
                            }
                            esr.setPosition(dis.readUTF());
                            esr.setDescription(dis.readUTF());
                            return esr;
                        });
                        return er;
                    });
                }
                resume.addSection(sectionType, section);
            }


            return resume;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new StorageException("Deserialization error", null, e);
        }
    }

    private static <T> void writeList(DataOutputStream dos, Collection<T> list, DataStreamWriter<T> elementWriter) throws IOException {
        dos.writeInt(list.size());
        for (T elem : list) {
            elementWriter.write(elem);
        }
    }

    private static <T> void readList(DataInputStream dis, List<T> list, DataStreamReader<T> elemReader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(elemReader.read());
        }
    }

    @FunctionalInterface
    private interface DataStreamWriter<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    private interface DataStreamReader<T> {
        T read() throws IOException;
    }
}


