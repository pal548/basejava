<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
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
                <c:when test="<%=type == SectionType.PERSONAL
                                 || type == SectionType.OBJECTIVE %>">
                    <dl>
                        <dt>${type.title}</dt>
                        <%--<c:if test="<%=((SectionSingle)resume.getSections().get(type)) != null%>">--%>
                            <dd><input type="text" name="${type.name()}" size=50 value="<%=((SectionSingle)resume.getSections().get(type)).getValue()%>"/></dd>
                        <%--</c:if>--%>
                    </dl>
                </c:when>
                <c:when test="<%=type == SectionType.QUALIFICATIONS
                                 || type == SectionType.ACHIEVEMENT %>">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd>
                            <a href="resume/mult_section?uuid=${resume.uuid}&type=${type.name()}&i=-1&action=add"><img src="img/add.png"></a><br>

                            <%--<c:if test="((SectionMultiple)resume.getSections().get(type)) != null">--%>
                                <ul>
                                <c:forEach var="str" items="<%=((SectionMultiple)resume.getSections().get(type)).getStrings()%>" varStatus="loop">
                                    <li>${str}
                                    <a href="resume/mult_section?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&action=add"><img src="img/add.png"></a>
                                    <a href="resume/mult_section?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&action=delete"><img src="img/delete.png"></a>
                                    <a href="resume/mult_section?uuid=${resume.uuid}&type=${type.name()}&i=${loop.index}&action=edit"><img src="img/pencil.png"></a>
                                    </li>
                                </c:forEach>
                                </ul>
                            <%--</c:if>--%>
                        </dd>
                    </dl>
                </c:when>

                <c:when test="<%=type == SectionType.EXPERIENCE
                                 || type == SectionType.EDUCATION %>">
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

                                        <c:set var="index_s" value="${loop.index}" />
                                        <c:out value = "${index_s}" />
                                        <%--<% int index = Integer.parseInt("index_s"); %>--%>
                                        <c:forEach var="sub" items='<%=((SectionExperience)resume.getSections().get(type)).getExperienceList().get(Integer.parseInt("index_s"))%>' varStatus="loop_sub">
                                        </c:forEach>
                                    </li>
                                </c:forEach>
                            </ul>
                        </dd>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>

        <input type="text" name="section" size=30 value="2"><br/>
        <input type="text" name="section" size=30 value="3"><br/>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

