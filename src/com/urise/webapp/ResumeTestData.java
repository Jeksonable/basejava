package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = createResume("uuid", "Григорий Кислин");
        printResume(resume);
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKED, "www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "www.github.com/gkislin");
        resume.addContact(ContactType.STACK, "www.stackoverflow.com/users/548473");
        resume.addContact(ContactType.HOME, "gkislin.ru");

        String personalDescription = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
        resume.addSection(SectionType.PERSONAL, new SimpleSection(personalDescription));

        String objectiveDescription = "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры.";
        resume.addSection(SectionType.OBJECTIVE, new SimpleSection(objectiveDescription));

        List<String> achievementDescriptions = new ArrayList<>();
        achievementDescriptions.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        achievementDescriptions.add("Реализация двухфакторной аутентификации для онлайн платформы управления " +
                "проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementDescriptions.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        achievementDescriptions.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementDescriptions.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных " +
                "сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации " +
                "о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и " +
                "мониторинга системы по JMX (Jython/ Django).");
        achievementDescriptions.add("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.addSection(SectionType.ACHIEVEMENT, new BulletedListSection(achievementDescriptions));

        List<String> qualificationDescriptions = new ArrayList<>();
        qualificationDescriptions.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationDescriptions.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationDescriptions.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualificationDescriptions.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualificationDescriptions.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualificationDescriptions.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualificationDescriptions.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), " +
                "Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualificationDescriptions.add("Python: Django.");
        qualificationDescriptions.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualificationDescriptions.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualificationDescriptions.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, " +
                "StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, " +
                "LDAP, OAuth1, OAuth2, JWT.");
        qualificationDescriptions.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualificationDescriptions.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, " +
                "Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualificationDescriptions.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования");
        qualificationDescriptions.add("Родной русский, английский \"upper intermediate\"");
        resume.addSection(SectionType.QUALIFICATIONS, new BulletedListSection(qualificationDescriptions));

        List<Organization> organizationDescriptions = new ArrayList<>();
        organizationDescriptions.add(new Organization("Java Online Projects", "javaops.ru",
                new Organization.Experience(2013, Month.OCTOBER, "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок.")));
        organizationDescriptions.add(new Organization("Wrike", "https://www.wrike.com/",
                new Organization.Experience(2014, Month.OCTOBER, 2016, Month.JANUARY,
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, " +
                                "Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, " +
                                "авторизация по OAuth1, OAuth2, JWT SSO.")));
        organizationDescriptions.add(new Organization("RIT Center", "",
                new Organization.Experience(2012, Month.APRIL, 2014, Month.OCTOBER,
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                                "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование " +
                                "системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения " +
                                "(почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование " +
                                "из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, " +
                                "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                                "Unix shell remote scripting via ssh tunnels, PL/Python")));
        organizationDescriptions.add(new Organization("Luxoft (Deutsche Bank)", "www.luxoft.ru",
                new Organization.Experience(2010, Month.DECEMBER, 2012, Month.APRIL,
                        "Ведущий программист",
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, " +
                                "GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения " +
                                "для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. " +
                                "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")));
        organizationDescriptions.add(new Organization("Yota", "https://www.yota.ru/",
                new Organization.Experience(2008, Month.JUNE, 2010, Month.DECEMBER,
                        "Ведущий специалист",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, " +
                                "v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, " +
                                "статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")));
        organizationDescriptions.add(new Organization("Enkata", "enkata.com",
                new Organization.Experience(2007, Month.MARCH, 2008, Month.JUNE,
                        "Разработчик ПО",
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                                "частей кластерного J2EE приложения (OLAP, Data mining).")));
        organizationDescriptions.add(new Organization("Siemens AG", "https://new.siemens.com/ru/ru.html",
                new Organization.Experience(2005, Month.JANUARY, 2007, Month.FEBRUARY,
                        "Разработчик ПО",
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО " +
                                "на мобильной IN платформе Siemens @vantage (Java, Unix).")));
        organizationDescriptions.add(new Organization("Alcatel", "www.alcatel.ru",
                new Organization.Experience(1997, Month.SEPTEMBER, 2005, Month.JANUARY,
                        "Инженер по аппаратному и программному тестированию",
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")));
        resume.addSection(SectionType.EXPERIENCE, new OrganizationListSection(organizationDescriptions));

        List<Organization> educationDescriptions = new ArrayList<>();
        educationDescriptions.add(new Organization("Coursera",
                "https://www.coursera.org/learn/progfun",
                new Organization.Experience(2013, Month.MARCH, 2013, Month.MAY,
                        "\"Functional Programming Principles in Scala\" by Martin Odersky", null)));
        educationDescriptions.add(new Organization("Luxoft",
                "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                new Organization.Experience(2011, Month.MARCH, 2011, Month.APRIL,
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null)));
        educationDescriptions.add(new Organization("Siemens AG",
                "http://www.siemens.ru/",
                new Organization.Experience(2005, Month.JANUARY, 2005, Month.APRIL,
                        "3 месяца обучения мобильным IN сетям (Берлин)", null)));
        educationDescriptions.add(new Organization("Alcatel",
                "http://www.alcatel.ru/",
                new Organization.Experience(1997, Month.SEPTEMBER, 1998, Month.MARCH,
                        "6 месяцев обучения цифровым телефонным сетям (Москва)", null)));
        educationDescriptions.add(new Organization("Санкт-Петербургский национальный исследовательский " +
                "университет информационных технологий, механики и оптики",
                "http://www.ifmo.ru/",
                new Organization.Experience(1993, Month.SEPTEMBER, 1996, Month.JULY,
                        "Аспирантура (программист С, С++)", null),
                new Organization.Experience(1987, Month.SEPTEMBER, 1993, Month.JULY,
                        "Инженер (программист Fortran, C)", null)));
        educationDescriptions.add(new Organization("Заочная физико-техническая школа при МФТИ",
                "http://www.school.mipt.ru/",
                new Organization.Experience(1984, Month.SEPTEMBER, 1987, Month.JUNE,
                        "Закончил с отличием", null)));
        resume.addSection(SectionType.EDUCATION, new OrganizationListSection(educationDescriptions));

        return resume;
    }

    private static void printResume(Resume resume) {
        System.out.println(resume.getFullName());
        System.out.println();
        Arrays.stream(ContactType.values()).map(contactType -> contactType.toString() + resume.getContact(contactType))
                .forEachOrdered(System.out::println);
        Arrays.stream(SectionType.values()).map(sectionType -> sectionType.toString() + resume.getSection(sectionType))
                .forEachOrdered(System.out::println);
    }
}
