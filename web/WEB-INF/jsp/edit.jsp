<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <jsp:useBean id="action" type="java.lang.String" scope="request"/>
    <c:choose>
        <c:when test='${action == "add"}' >
            <title>Новое резюме</title>
        </c:when>
        <c:otherwise>
            <title>Резюме ${resume.fullName}</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="action" value="${action}">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=50 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <h3>Секции:</h3>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <jsp:useBean id="type" type="ru.javawebinar.basejava.model.SectionType"/>
            <c:choose>
                <c:when test="${type == SectionType.PERSONAL
                                 || type == SectionType.OBJECTIVE }">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type.name()}" size=50 value='<%=action.equals("add") ? "" : ((SectionSingle)resume.getSections().get(type)).getValue()%>'/></dd>
                    </dl>
                </c:when>
                <c:when test='${(type == SectionType.QUALIFICATIONS
                                 || type == SectionType.ACHIEVEMENT)
                                && ! (action == "add")}'>
                    <dl>
                        <dt>${type.title}</dt>
                        <dd>
                            <a href="resume/mult_section?uuid=${resume.uuid}&type=${type.name()}&i=-1&action=add"><img src="img/add.png"></a><br>

                                <ul>
                                <c:forEach var="str" items="<%=((SectionMultiple)resume.getSections().get(type)).getStrings()%>" varStatus="loop">
                                    <li>${str}
                                    <a href="resume/mult_section?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&action=add"><img src="img/add.png"></a>
                                    <a href="resume/mult_section?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&action=delete"><img src="img/delete.png"></a>
                                    <a href="resume/mult_section?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&action=edit"><img src="img/pencil.png"></a>
                                    </li>
                                </c:forEach>
                                </ul>
                        </dd>
                    </dl>
                </c:when>

                <c:when test='${(type == SectionType.EXPERIENCE
                                 || type == SectionType.EDUCATION)
                                && action != "add" }'>
                    <dl>
                        <dt>${type.title}</dt>
                        <dd>
                            <a href="resume/exp_section?uuid=${resume.uuid}&type=${type.name()}&i=-1&action=add"><img src="img/add.png"></a><br>

                            <ul>
                                <c:forEach var="expRec" items="<%=((SectionExperience)resume.getSections().get(type)).getExperienceList()%>" varStatus="loop">
                                    <jsp:useBean id="expRec" type="ru.javawebinar.basejava.model.ExperienceRecord"/>
                                    <li>
                                        <c:choose>
                                            <c:when test="${expRec.company.url == null || expRec.company.url.length() == 0}">
                                                ${expRec.company.name}
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${expRec.company.url}">${expRec.company.name}</a>
                                            </c:otherwise>
                                        </c:choose>
                                        <a href="resume/exp_section?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&action=add"><img src="img/add.png"></a>
                                        <a href="resume/exp_section?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&action=delete"><img src="img/delete.png"></a>
                                        <a href="resume/exp_section?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&action=edit"><img src="img/pencil.png"></a>

                                        <br>
                                        <a href="resume/exp_sub?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&i_sub=-1&action=add"><img src="img/add.png"></a> <br>

                                        <c:set var="index" value="${loop.index}" />
                                        <jsp:useBean id="index" type="java.lang.Integer"/>
                                        <% DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/yyyy"); %>
                                        <table cellpadding="4">
                                        <c:forEach var="sub" items='<%=((SectionExperience)resume.getSections().get(type)).getExperienceList().get(index).getListExperience()%>' varStatus="loop_sub">
                                            <jsp:useBean id="sub" type="ru.javawebinar.basejava.model.ExperienceSubRecord"/>
                                            <tr>
                                                <td style="vertical-align: top" width="130">
                                                    <%= df.format(sub.getDateBeg())%>
                                                    &nbsp;-&nbsp;
                                                    <%= sub.getDateEnd().equals(DateUtil.NOW) ? "Сейчас" : df.format(sub.getDateEnd()) %>
                                                </td>
                                                <td style="vertical-align: top" width="62">
                                                    <a href="resume/exp_sub?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&i_sub=${loop_sub.index}&action=add"><img src="img/add.png"></a>
                                                    <a href="resume/exp_sub?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&i_sub=${loop_sub.index}&action=delete"><img src="img/delete.png"></a>
                                                    <a href="resume/exp_sub?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&i_sub=${loop_sub.index}&action=edit"><img src="img/pencil.png"></a>
                                                </td>
                                                <td>
                                                    <b>${sub.position}</b>
                                                    <br/>
                                                    ${sub.description}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </table>
                                    </li>
                                </c:forEach>
                            </ul>
                        </dd>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

