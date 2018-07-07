package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private static final Storage storage;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        storage = new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        //String name = request.getParameter("name");
        //response.getWriter().write(name == null ? "hello resumes" : "hello " + name + "!");

        List<Resume> resumes = storage.getAllSorted();
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n" +
                  "<html>\n" +
                  "  <head>\n" +
                  "    <meta charset=\"utf-8\">\n" +
                  "    <title>Title</title>\n" +
                  "  </head>\n" +
                  "  <body>\n");
        sb.append("<table border=1>\n");
        for(Resume r : resumes) {
            sb.append("  <tr>\n");
            sb.append("      <td>").append(r.getUuid()).append("</td>\n");
            sb.append("      <td>").append(r.getFullName()).append("</td>\n");
            sb.append("  </tr>\n");
        }
        sb.append("</table>\n" +
                  "</body>\n" +
                  "</html>\n");
        response.getWriter().write(sb.toString());
    }
}
