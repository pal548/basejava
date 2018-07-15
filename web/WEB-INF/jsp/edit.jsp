<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionSingle" %>
<%@ page import="ru.javawebinar.basejava.model.SectionMultiple" %>
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
                        <dd><input type="text" name="${type.name()}" size=50 value="<%=((SectionSingle)resume.getSections().get(type)).getValue()%>"/></dd>
                    </dl>
                </c:when>
                <c:when test="<%=type == SectionType.QUALIFICATIONS
                                 || type == SectionType.ACHIEVEMENT %>">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd>
                            <c:forEach var="str" items="<%=((SectionMultiple)resume.getSections().get(type)).getStrings()%>" varStatus="loop">
                                ${str}
                                <a href="resume/mult_section?uuid=${resume.uuid}&action=add&i=${loop.index}"><img src="img/add.png"></a>
                                <a href="resume/mult_section?uuid=${resume.uuid}&action=add&i=${loop.index}"><img src="img/delete.png"></a>
                                <a href="resume/mult_section?uuid=${resume.uuid}&action=add&i=${loop.index}"><img src="img/pencil.png"></a>
                                <br>
                            </c:forEach>
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

