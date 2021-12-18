package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid", "Григорий Кислин");

        resume.addContact(ContactType.PHONE, new Contact("+7(921) 855-0482", ""));
        resume.addContact(ContactType.SKYPE, new Contact("grigory.kislin", "skype:grigory.kislin"));
        resume.addContact(ContactType.MAIL, new Contact("gkislin@yandex.ru", "mailto:gkislin@yandex.ru"));
        resume.addContact(ContactType.LINKED, new Contact("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin"));
        resume.addContact(ContactType.GITHUB, new Contact("Профиль GitHub", "https://www.github.com/gkislin"));
        resume.addContact(ContactType.STACK, new Contact("Профиль Stackoverflow", "https://www.stackoverflow.com/users/548473"));
        resume.addContact(ContactType.HOME, new Contact("Домашняя страница", "gkislin.ru"));

        String personalDescription = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
        resume.addSection(SectionType.PERSONAL, new SimpleSection(personalDescription));
        String objectiveDescription = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.";
        resume.addSection(SectionType.OBJECTIVE, new SimpleSection(objectiveDescription));

        List<String> achievementDescriptions = new ArrayList<>();
        achievementDescriptions.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. " +
                "Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievementDescriptions.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, " +
                "DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementDescriptions.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, " +
                "CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO " +
                "аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementDescriptions.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT " +
                "(GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementDescriptions.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, " +
                "JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. " +
                "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievementDescriptions.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, " +
                "Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.addSection(SectionType.ACHIEVEMENT, new BulletedListSection(achievementDescriptions));
        List<String> qualificationDescriptions = new ArrayList<>();
        qualificationDescriptions.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationDescriptions.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationDescriptions.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualificationDescriptions.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualificationDescriptions.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualificationDescriptions.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualificationDescriptions.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA " +
                "(Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, " +
                "Selenium (htmlelements).");
        qualificationDescriptions.add("Python: Django.");
        qualificationDescriptions.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualificationDescriptions.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualificationDescriptions.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, " +
                "JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualificationDescriptions.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualificationDescriptions.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, " +
                "pgBouncer.");
        qualificationDescriptions.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, " +
                "функционального программирования");
        qualificationDescriptions.add("Родной русский, английский \"upper intermediate\"");
        resume.addSection(SectionType.QUALIFICATIONS, new BulletedListSection(qualificationDescriptions));

        List<Experience> experienceDescriptions = new ArrayList<>();
        List<LocalDate> ld = new ArrayList<>();
        ld.add(new LocalDate("10/2013 - Сейчас", "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experienceDescriptions.add(new Experience("Java Online Projects", "javaops.ru", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("10/2014 - 01/2016", "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, " +
                        "MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, " +
                        "JWT SSO."));
        experienceDescriptions.add(new Experience("Wrike", "https://www.wrike.com/", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("04/2012 - 10/2014", "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, " +
                        "ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), " +
                        "AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, " +
                        "BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco " +
                        "JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache " +
                        "Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell " +
                        "remote scripting via ssh tunnels, PL/Python"));
        experienceDescriptions.add(new Experience("RIT Center", "", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("12/2010 - 04/2012", "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, " +
                        "Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для " +
                        "администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, " +
                        "Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        experienceDescriptions.add(new Experience("Luxoft (Deutsche Bank)", "www.luxoft.ru", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("06/2008 - 12/2010", "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, " +
                        "EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и " +
                        "мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        experienceDescriptions.add(new Experience("Yota", "https://www.yota.ru/", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("03/2007 - 06/2008", "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного " +
                        "J2EE приложения (OLAP, Data mining)."));
        experienceDescriptions.add(new Experience("Enkata", "enkata.com", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("01/2005 - 02/2007", "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной " +
                        "IN платформе Siemens @vantage (Java, Unix)."));
        experienceDescriptions.add(new Experience("Siemens AG", "https://new.siemens.com/ru/ru.html", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("09/1997 - 01/2005", "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        experienceDescriptions.add(new Experience("Alcatel", "www.alcatel.ru", new ArrayList<>(ld)));
        resume.addSection(SectionType.EXPERIENCE, new Organization(experienceDescriptions));
        List<Experience> educationDescriptions = new ArrayList<>();
        ld.clear();
        ld.add(new LocalDate("03/2013 - 05/2013", "\"Functional Programming Principles in Scala\" by Martin Odersky"));
        educationDescriptions.add(new Experience("Coursera", "https://www.coursera.org/learn/progfun", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("03/2011 - 04/2011", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
        educationDescriptions.add(new Experience("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("01/2005 - 04/2005", "3 месяца обучения мобильным IN сетям (Берлин)"));
        educationDescriptions.add(new Experience("Siemens AG", "http://www.siemens.ru/", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("09/1997 - 03/1998", "6 месяцев обучения цифровым телефонным сетям (Москва)"));
        educationDescriptions.add(new Experience("Alcatel", "http://www.alcatel.ru/", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("09/1993 - 07/1996", "Аспирантура (программист С, С++)"));
        ld.add(new LocalDate("09/1987 - 07/1993", "Инженер (программист Fortran, C)"));
        educationDescriptions.add(new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "http://www.ifmo.ru/", new ArrayList<>(ld)));
        ld.clear();
        ld.add(new LocalDate("09/1984 - 06/1987", "Закончил с отличием"));
        educationDescriptions.add(new Experience("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/", new ArrayList<>(ld)));
        resume.addSection(SectionType.EDUCATION, new Organization(educationDescriptions));

        printResume(resume);
    }

    private static void printResume(Resume resume) {
        System.out.println(resume.getFullName());
        System.out.println();
        Arrays.stream(ContactType.values()).map(contactType -> contactType.toString() + resume.getContact(contactType)).forEachOrdered(System.out::println);
        Arrays.stream(SectionType.values()).map(sectionType -> sectionType.toString() + resume.getSection(sectionType)).forEachOrdered(System.out::println);
    }
}
