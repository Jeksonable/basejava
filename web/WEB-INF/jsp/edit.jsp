<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.OrganizationListSection" %>
<%@ page import="com.urise.webapp.model.BulletedListSection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <h1>Имя:</h1>
        <dl>
            <dd><input type="text" name="fullName" size=100 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=75 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(sectionType)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <dt>${sectionType.title}</dt>
            <c:choose>
                <c:when test="${sectionType=='PERSONAL' || sectionType=='OBJECTIVE'}">
                    <dl>
                        <dd><input type="text" name="${sectionType.name()}" size=100 value="${section}"></dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType=='ACHIEVEMENT' || sectionType=='QUALIFICATIONS'}">
                    <dl>
                        <dd><textarea name='${sectionType.name()}' cols=98
                                      rows=5><%=String.join("\n", ((BulletedListSection) section).getDescriptions())%></textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION'}">
                    <c:forEach var="organization" items="<%=((OrganizationListSection) section).getOrganizations()%>"
                               varStatus="counter">
                        <c:set var="homePage" value="${organization.homePage}"/>
                        <dl>
                            <dt>Название учереждения:</dt>
                            <dd><input type="text" name='${sectionType}' size=100 value="${homePage.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт учереждения:</dt>
                            <dd><input type="text" name='${sectionType}url' size=100 value="${homePage.url}"></dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <c:forEach var="experience" items="${organization.experiences}">
                                <jsp:useBean id="experience" type="com.urise.webapp.model.Organization.Experience"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd>
                                        <input type="text" name="${sectionType}${counter.index}startDate" size=10
                                               value="<%=DateUtil.format(experience.getStartDate())%>"
                                               placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <input type="text" name="${sectionType}${counter.index}endDate" size=10
                                               value="<%=DateUtil.format(experience.getEndDate())%>"
                                               placeholder="MM/yyyy"></dd>
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name='${sectionType}${counter.index}title' size=71
                                               value="${experience.title}"></dd>
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${sectionType}${counter.index}description" rows=5
                                                  cols=70>${experience.description}</textarea></dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <h5>Обратите внимание! Поле "Имя" является обязательным для заполнения.</h5>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
