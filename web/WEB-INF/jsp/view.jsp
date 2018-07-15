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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <p>
        <c:forEach var="sec" items="${resume.sections}">
            <jsp:useBean id="sec" type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSectionData>"/>
            <h3><%=sec.getKey().getTitle()%></h3>
            <c:choose>

                <c:when test="<%=(sec.getKey() == SectionType.OBJECTIVE)
                                  || (sec.getKey() == SectionType.PERSONAL)   %>">
                    <p><%=((SectionSingle)sec.getValue()).getValue()%></p>
                </c:when>

                <c:when test="<%=sec.getKey() == SectionType.ACHIEVEMENT
                                 || sec.getKey() == SectionType.QUALIFICATIONS %>">
                    <ul type="disc">
                        <c:forEach items="<%=((SectionMultiple)sec.getValue()).getStrings()%>" var="line">
                            <li>${line}</li>
                        </c:forEach>
                    </ul>
                </c:when>

                

            </c:choose>
        </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

