package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionMultiple;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MultipleSectionServlet extends HttpServlet {
    private final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        SectionType type = SectionType.valueOf(request.getParameter("type"));
        int index = Integer.parseInt(request.getParameter("index"));
        String value = request.getParameter("value");
        Resume r = storage.get(uuid);

        SectionMultiple sec = (SectionMultiple) r.getSections().get(type);
        if (sec == null) {
            sec = new SectionMultiple();
            r.addSection(type, sec);
        }
        List<String> strs = sec.getStrings();
        switch (action) {
            case "edit":
                strs.remove(index);
                strs.add(index, value);
                storage.update(r);
                response.sendRedirect("/resume?uuid=" + uuid + "&action=edit");
                return;
            case "add":
                strs.add(index + 1, value);
                storage.update(r);
                response.sendRedirect("/resume?uuid=" + uuid + "&action=edit");
                return;
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        SectionType type = SectionType.valueOf(request.getParameter("type"));
        int index = Integer.parseInt(request.getParameter("i"));
        if (action == null) {
            response.sendRedirect("/resume?uuid=" + uuid + "&action=edit");
            return;
        } else {
            Resume r = storage.get(uuid);

            switch (action) {
                case "delete":
                    List<String> strs = ((SectionMultiple) r.getSections().get(type)).getStrings();
                    strs.remove(index);
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
                        request.setAttribute("value", ((SectionMultiple) r.getSections().get(type)).getStrings().get(index));
                    } else {
                        request.setAttribute("value", "");
                    }

                    request.getRequestDispatcher("/WEB-INF/jsp/mult_section/multSectionStringEdit.jsp").forward(request, response);
                    return;
                default:
                    throw new IllegalArgumentException("Action can't be " + action);

            }
        }
    }

}
