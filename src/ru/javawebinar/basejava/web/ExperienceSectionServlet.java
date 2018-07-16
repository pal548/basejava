package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class ExperienceSectionServlet extends HttpServlet {
    private final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        SectionType type = SectionType.valueOf(request.getParameter("type"));
        int index = Integer.parseInt(request.getParameter("index"));
        String name = request.getParameter("name");
        String url = request.getParameter("url");

        Resume r = storage.get(uuid);

        SectionExperience sec = (SectionExperience) r.getSections().get(type);
        if (sec == null) {
            sec = new SectionExperience();
            r.addSection(type, sec);
        }
        List<ExperienceRecord> expRecs = sec.getExperienceList();
        switch (action) {
            case "edit":
                ExperienceRecord e = expRecs.get(index);
                e.getCompany().setName(name);
                e.getCompany().setUrl(url);
                break;
            case "add":
                e = new ExperienceRecord();
                e.setCompany(new Link(name, url));
                expRecs.add(index + 1, e);
                break;
            default:
                throw new IllegalStateException("Action can't be " + action);
        }
        storage.update(r);
        response.sendRedirect("/resume?uuid=" + uuid + "&action=edit");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        SectionType type = SectionType.valueOf(request.getParameter("type"));
        int index = Integer.parseInt(request.getParameter("i"));
        if (action == null) {
            response.sendRedirect("/resume?uuid=" + uuid + "&action=edit");
        } else {
            Resume r = storage.get(uuid);

            switch (action) {
                case "delete":
                    List<ExperienceRecord> expList = ((SectionExperience) r.getSections().get(type)).getExperienceList();
                    expList.remove(index);
                    storage.update(r);
                    response.sendRedirect("/resume?uuid=" + uuid + "&action=edit");
                    return;
                case "add":
                case "edit":
                    request.setAttribute("resume", r);
                    request.setAttribute("type", type);
                    request.setAttribute("action", action);
                    request.setAttribute("index", index);
                    if (action.equals("edit")) {
                        request.setAttribute("value", ((SectionExperience)r.getSections().get(type)).getExperienceList().get(index));
                    } else {
                        request.setAttribute("value", new ExperienceRecord());
                    }

                    request.getRequestDispatcher("/WEB-INF/jsp/exp_section/expSectionRecordEdit.jsp").forward(request, response);
                    return;
                default:
                    throw new IllegalArgumentException("Action can't be " + action);

            }
        }
    }
}
