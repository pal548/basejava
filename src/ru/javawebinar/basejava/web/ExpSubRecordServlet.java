package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class ExpSubRecordServlet extends HttpServlet {
    private final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        SectionType type = SectionType.valueOf(request.getParameter("type"));
        int index = Integer.parseInt(request.getParameter("index"));
        int i_sub = Integer.parseInt(request.getParameter("i_sub"));

        LocalDate dateBeg = LocalDate.parse(request.getParameter("date_beg"));
        String date_end_s = request.getParameter("date_end");
        LocalDate dateEnd = date_end_s.equals("") ? DateUtil.NOW : LocalDate.parse(date_end_s);
        String position = request.getParameter("position");
        String description = request.getParameter("description");

        Resume r = storage.get(uuid);

        SectionExperience sec = (SectionExperience) r.getSections().get(type);
        List<ExperienceSubRecord> experienceSubRecords = sec.getExperienceList().get(index).getListExperience();
        switch (action) {
            case "edit":
                ExperienceSubRecord subRecord = experienceSubRecords.get(i_sub);
                subRecord.setDateBeg(dateBeg);
                subRecord.setDateEnd(dateEnd);
                subRecord.setPosition(position);
                subRecord.setDescription(description);
                break;
            case "add":
                subRecord = new ExperienceSubRecord();
                subRecord.setDateBeg(dateBeg);
                subRecord.setDateEnd(dateEnd);
                subRecord.setPosition(position);
                subRecord.setDescription(description);
                experienceSubRecords.add(i_sub + 1, subRecord);
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
        int iSub = Integer.parseInt(request.getParameter("i_sub"));
        if (action == null) {
            response.sendRedirect("/resume?uuid=" + uuid + "&action=edit");
            return;
        } else {
            Resume r = storage.get(uuid);

            ExperienceRecord expRec = ((SectionExperience) r.getSections().get(type)).getExperienceList().get(index);
            switch (action) {
                case "delete":
                    List<ExperienceSubRecord> subRecords = expRec.getListExperience();
                    subRecords.remove(index);
                    storage.update(r);
                    response.sendRedirect("/resume?uuid=" + uuid + "&action=edit");
                    return;
                case "add":
                case "edit":
                    request.setAttribute("resume", r);
                    request.setAttribute("type", type);
                    request.setAttribute("action", action);
                    request.setAttribute("index", index);
                    request.setAttribute("i_sub", iSub);
                    request.setAttribute("expRec", expRec);
                    if (action.equals("edit")) {
                        request.setAttribute("value", expRec.getListExperience().get(iSub));
                    } else {
                        request.setAttribute("value", new ExperienceSubRecord());
                    }

                    request.getRequestDispatcher("/WEB-INF/jsp/exp_sub_record/expSubRecordEdit.jsp").forward(request, response);
                    return;
                default:
                    throw new IllegalArgumentException("Action can't be " + action);

            }
        }
    }
}
