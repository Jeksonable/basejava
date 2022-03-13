<%@ page import="com.urise.webapp.model.SimpleSection" %>
<%@ page import="com.urise.webapp.model.BulletedListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationListSection" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h1>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <hr>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <c:set var="sectionType" value="${sectionEntry.key}"/>
            <tr>
                <td colspan="2"><h2>${sectionType.title}</h2></td>
            </tr>
            <c:set var="abstractSection" value="${sectionEntry.value}"/>
            <jsp:useBean id="abstractSection" type="com.urise.webapp.model.AbstractSection"/>
            <tr>
            <td colspan="2">
            <c:choose>
                <c:when test="${sectionType=='PERSONAL' || sectionType=='OBJECTIVE'}">
                    <tr>
                        <td colspan="2"><h4><%=((SimpleSection) abstractSection).getDescription()%>
                        </h4></td>
                    </tr>
                </c:when>
                <c:when test="${sectionType=='ACHIEVEMENT' || sectionType=='QUALIFICATIONS'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="description"
                                           items="<%=((BulletedListSection) abstractSection).getDescriptions()%>">
                                    <li>${description}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION'}">
                    <c:forEach var="organization"
                               items="<%=((OrganizationListSection) abstractSection).getOrganizations()%>">
                        <c:set var="homePage" value="${organization.homePage}"/>
                        <tr>
                            <td colspan="2">
                                <c:choose>
                                    <c:when test="${empty homePage.url}">
                                        <h3>${homePage.name}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${homePage.url}">${homePage.name}</a></h3>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="experience" items="${organization.experiences}">
                            <jsp:useBean id="experience" type="com.urise.webapp.model.Organization.Experience"/>
                            <tr>
                                <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDates(experience)%>
                                </td>
                                <td><b>${experience.title}</b><br>${experience.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <br/>
    <button type="button" onclick="window.history.back()">ОК</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
