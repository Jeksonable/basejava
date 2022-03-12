<%@ page import="com.urise.webapp.model.SimpleSection" %>
<%@ page import="com.urise.webapp.model.BulletedListSection" %>
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
                            <h4><%=((SimpleSection) abstractSection).getDescription()%>
                            </h4>
                        </c:when>
                        <c:when test="${sectionType=='ACHIEVEMENT' || sectionType=='QUALIFICATIONS'}">
                            <ul>
                                <c:forEach var="description" items="<%=((BulletedListSection) abstractSection).getDescriptions()%>">
                                    <li>${description}</li>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
