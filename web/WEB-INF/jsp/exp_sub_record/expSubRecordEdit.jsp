<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
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
    <jsp:useBean id="expRec" type="ru.javawebinar.basejava.model.ExperienceRecord" scope="request"/>
    <jsp:useBean id="value" type="ru.javawebinar.basejava.model.ExperienceSubRecord" scope="request"/>
    <title>Резюме ${resume.fullName} - ${type.title}</title>
</head>
<body>
<jsp:include page="../fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}</h2>
    <h3>${type.title}</h3>
    <b>${expRec.company.name}</b>
    <form method="post" action="/resume/exp_sub" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="type" value="${type.name()}">
        <input type="hidden" name="index" value="${index}">
        <input type="hidden" name="i_sub" value="${i_sub}">
        <input type="hidden" name="action" value="${action}">

        <dl>
            <dt>Дата начала</dt>
            <dd>
                <input type="date" name="date_beg" size="80" required
                       value='<%=action.equals("add") ? "" : value.getDateBeg()%>'></dd>
        </dl>
        <dl>
            <dt>Дата окончания</dt>
            <dd><input type="date" name="date_end" size="80"
                       value='<%=action.equals("add") || value.getDateEnd().equals(DateUtil.NOW) ? "" : value.getDateEnd()%>'>
                <br/>
                (оставьте пустым, если занятость продолжается)
            </dd>
        </dl>
        <dl>
            <dt>Должность</dt>
            <dd><input type="text" name="position" size="80"
                       value='<%=action.equals("add") ? "" : value.getPosition()%>'>
            </dd>
        </dl>
        <dl>
            <dt>Описание</dt>
            <dd><textarea name="description" cols="80" rows="5"><%=action.equals("add") ? "" : value.getDescription()%></textarea>
            </dd>
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