import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExpSubRecordServlet extends HttpServlet {
    private final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

            switch (action) {
                case "delete":
                    List<ExperienceSubRecord> subRecords = ((SectionExperience) r.getSections().get(type)).getExperienceList().get(index).getListExperience();
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
                    if (action.equals("edit")) {
                        request.setAttribute("value", ((SectionExperience) r.getSections().get(type)).getExperienceList().get(index).getListExperience().get(iSub));
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
