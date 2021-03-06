package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        String action = request.getParameter("action");
        String fullName = request.getParameter("fullName");
        String uuid;
        Resume r;
        boolean adding = action.equals("add");
        if (adding) {
            uuid = UUID.randomUUID().toString();
            r = new Resume(uuid, fullName);
        } else {
            uuid = request.getParameter("uuid");
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if ((value == null) || (value.trim().length() == 0)) {
                r.getContacts().remove(type);
            } else {
                r.addContact(type, value);
            }

        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    String value = request.getParameter(type.name());
                    if (value == null || value.length() == 0) {
                        r.getSections().remove(type);
                    } else {
                        r.addSection(type, new SectionSingle(value));
                    }
                    break;
            }
        }

        if (adding) {
            storage.save(r);
            response.sendRedirect("resume?uuid="+uuid+"&action=edit");
        } else {
            storage.update(r);
            response.sendRedirect("resume");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        } else {
            Resume r;
            request.setAttribute("action", action);
            switch (action) {
                case "add":
                    request.setAttribute("resume", new Resume());
                    request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
                    return;
                case "delete":
                    storage.delete(uuid);
                    response.sendRedirect("resume");
                    return;
                case "view":
                case "edit":
                    r = storage.get(uuid);
                    if (action.equals("edit")) {
                        fillResumeWithEnptySections(r);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Action can't be " + action);
            }
            request.setAttribute("resume", r);
            request.getRequestDispatcher(
                    ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
            ).forward(request, response);
        }
    }

    private void fillResumeWithEnptySections(Resume r) {
        for (SectionType type : SectionType.values()) {
            if (r.getSections().get(type) == null) {
                switch (type){
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(type, new SectionSingle(""));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new SectionMultiple());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        r.addSection(type, new SectionExperience());
                        break;
                }
            }
        }
    }
}
