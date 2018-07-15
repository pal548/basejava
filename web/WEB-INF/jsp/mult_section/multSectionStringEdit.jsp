<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <jsp:useBean id="type" type="ru.javawebinar.basejava.model.SectionType" scope="request"/>
    <jsp:useBean id="index" type="java.lang.Integer" scope="request"/>
    <jsp:useBean id="action" type="java.lang.String" scope="request"/>
    <jsp:useBean id="value" type="java.lang.String" scope="request"/>
    <title>Резюме ${resume.fullName} - ${type.title}</title>
</head>
<body>
<jsp:include page="../fragments/header.jsp"/>
<section>
    <form method="post" action="/resume/mult_section" enctype="application/x-www-form-urlencoded">
        <h2>${resume.fullName}</h2>
        <h3>${type.title}</h3>
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="type" value="${type.name()}">
        <input type="hidden" name="index" value="${index}">
        <input type="hidden" name="action" value="${action}">

        <c:choose>
            <c:when test='${action.equals("add")}'>
                <input type="text" name="value" size="80" value="">
            </c:when>
            <c:when test='${action.equals("edit")}'>
                <input type="text" name="value" size="80" value="${value}">
            </c:when>
        </c:choose>
        <br/>

        <hr>

        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
