<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <% SectionType type = sec.getKey(); %>
            <h3><%=type.getTitle()%></h3>

            <c:choose>

                <c:when test="<%=(type == SectionType.OBJECTIVE)
                                  || (type == SectionType.PERSONAL)   %>">
                    <p><%=((SectionSingle)sec.getValue()).getValue()%></p>
                </c:when>

                <c:when test="<%=type == SectionType.ACHIEVEMENT
                                 || type == SectionType.QUALIFICATIONS %>">
                    <ul type="disc">
                        <c:forEach items="<%=((SectionMultiple)sec.getValue()).getStrings()%>" var="line">
                            <li>${line}</li>
                        </c:forEach>
                    </ul>
                </c:when>

                <c:when test="<%=type == SectionType.EDUCATION
                                 || type == SectionType.EXPERIENCE %>">
                    <ul type="disc">
                        <c:forEach items="<%=((SectionExperience)sec.getValue()).getExperienceList()%>" var="expRec">
                            <jsp:useBean id="expRec" type="ru.javawebinar.basejava.model.ExperienceRecord"/>
                            <li><c:choose>
                                    <c:when test="<%=expRec.getCompany().getUrl() == null
                                                     || expRec.getCompany().getUrl().length() == 0%>">
                                        <%=expRec.getCompany().getName()%>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<%=expRec.getCompany().getUrl()%>"><%=expRec.getCompany().getName()%></a>
                                    </c:otherwise>
                                </c:choose>
                                <br/>
                                <ul type="disc">
                                    <table cellpadding="4">
                                    <c:forEach items="<%=expRec.getListExperience()%>" var="expSubRec">
                                        <jsp:useBean id="expSubRec" type="ru.javawebinar.basejava.model.ExperienceSubRecord"/>
                                        <tr>
                                            <td style="vertical-align: top" width="130">
                                                <% Date dbeg = Date.from(expSubRec.getDateBeg().atStartOfDay(ZoneId.systemDefault()).toInstant()); %>
                                                <fmt:formatDate value="<%=dbeg%>" pattern="MM/yyyy"/>
                                                -
                                                <c:choose>
                                                    <c:when test="<%=expSubRec.getDateEnd().equals(DateUtil.NOW)%>">
                                                        Сейчас
                                                    </c:when>
                                                    <c:otherwise>
                                                        <% Date dend = Date.from(expSubRec.getDateEnd().atStartOfDay(ZoneId.systemDefault()).toInstant()); %>
                                                        <fmt:formatDate value="<%=dend%>" pattern="MM/yyyy"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <b>${expSubRec.position}</b>
                                                <br/>
                                                ${expSubRec.description}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </table>
                                </ul>
                            </li>
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

