<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <jsp:useBean id="type" type="ru.javawebinar.basejava.model.SectionType" scope="request"/>
    <jsp:useBean id="index" type="java.lang.Integer" scope="request"/>
    <jsp:useBean id="i_sub" type="java.lang.Integer" scope="request"/>
    <jsp:useBean id="action" type="java.lang.String" scope="request"/>
    <jsp:useBean id="value" type="ru.javawebinar.basejava.model.ExperienceSubRecord" scope="request"/>
    <title>Резюме ${resume.fullName} - ${type.title}</title>
</head>
<body>
<jsp:include page="../fragments/header.jsp"/>
<section>
    <form method="post" action="/resume/exp_sub" enctype="application/x-www-form-urlencoded">
        <h2>${resume.fullName}</h2>
        <h3>${type.title}</h3>
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="type" value="${type.name()}">
        <input type="hidden" name="index" value="${index}">
        <input type="hidden" name="i_sub" value="${index}">
        <input type="hidden" name="action" value="${action}">

        <dl>
            <dt>Дата начала</dt>
            <dd>
                <input type="text" name="date_beg" size="80"
                       value='<%=action.equals("add") ? "" : value.getDateBeg().toString()%>'></dd>
        </dl>
        <dl>
            <dt>Дата окончания</dt>
            <dd><input type="text" name="date_end" size="80"
                       value='<%=action.equals("add") ? "" : value.getDateEnd().toString()%>'></dd>
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