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
    <jsp:useBean id="value" type="ru.javawebinar.basejava.model.ExperienceRecord" scope="request"/>
    <title>Резюме ${resume.fullName} - ${type.title}</title>
</head>
<body>
<jsp:include page="../fragments/header.jsp"/>
<section>
    <form method="post" action="/resume/exp_section" enctype="application/x-www-form-urlencoded">
        <h2>${resume.fullName}</h2>
        <h3>${type.title}</h3>
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="type" value="${type.name()}">
        <input type="hidden" name="index" value="${index}">
        <input type="hidden" name="action" value="${action}">

        <dl>
            <dt>Компания</dt>
            <dd>
                <input type="text" name="name" size="80"
                       value='<%=action.equals("add") ? "" : value.getCompany().getName()%>'></dd>
        </dl>
        <dl>
            <dt>Сайт</dt>
            <dd><input type="text" name="url" size="80"
                       value='<%=action.equals("add") ? "" : value.getCompany().getUrl()%>'></dd>
        </dl>
        <br/>

        <hr>

        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
