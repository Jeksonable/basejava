package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.setAttribute("size", storage.size());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType sectionType : SectionType.values()) {
                    AbstractSection section = r.getSection(sectionType);
                    switch (sectionType) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = SimpleSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = BulletedListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationListSection orgSection = (OrganizationListSection) section;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(Organization.EMPTY);
                            if (orgSection != null) {
                                for (Organization org : orgSection.getOrganizations()) {
                                    List<Organization.Experience> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Organization.Experience.EMPTY);
                                    emptyFirstPositions.addAll(org.getExperiences());
                                    emptyFirstOrganizations.add(new Organization(org.getHomePage(), emptyFirstPositions));
                                }
                            }
                            section = new OrganizationListSection(emptyFirstOrganizations);
                            break;
                    }
                    r.addSection(sectionType, section);
                }
                break;
            case "new":
                r = Resume.EMPTY;
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (!HtmlUtil.isEmpty(fullName)) {
            final boolean isCreate = (uuid == null || uuid.length() == 0);
            Resume r;
            if (isCreate) {
                r = new Resume(fullName);
            } else {
                r = storage.get(uuid);
                r.setFullName(fullName);
            }
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                if (HtmlUtil.isEmpty(value)) {
                    r.getContacts().remove(type);
                } else {
                    r.addContact(type, value);
                }
            }
            for (SectionType sectionType : SectionType.values()) {
                String value = request.getParameter(sectionType.name());
                String[] values = request.getParameterValues(sectionType.name());
                if (HtmlUtil.isEmpty(value) && values.length < 2) {
                    r.getSections().remove(sectionType);
                } else {
                    switch (sectionType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            r.addSection(sectionType, new SimpleSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            r.addSection(sectionType, new BulletedListSection(value.split("\n")));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            List<Organization> organizations = new ArrayList<>();
                            String[] urls = request.getParameterValues(sectionType.name() + "url");
                            for (int i = 0; i < values.length; i++) {
                                String name = values[i];
                                if (!HtmlUtil.isEmpty(name)) {
                                    List<Organization.Experience> experiences = new ArrayList<>();
                                    String pfx = sectionType.name() + i;
                                    String[] startDates = request.getParameterValues(pfx + "startDate");
                                    String[] endDates = request.getParameterValues(pfx + "endDate");
                                    String[] titles = request.getParameterValues(pfx + "title");
                                    String[] descriptions = request.getParameterValues(pfx + "description");
                                    for (int j = 0; j < titles.length; j++) {
                                        if (!HtmlUtil.isEmpty(titles[j])) {
                                            experiences.add(
                                                    new Organization.Experience(DateUtil.parse(startDates[j]),
                                                            DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                        }
                                    }
                                    organizations.add(new Organization(new Link(name, urls[i]), experiences));
                                }
                            }
                            r.addSection(sectionType, new OrganizationListSection(organizations));
                    }
                }
            }
            if (isCreate) {
                storage.save(r);
            } else {
                storage.update(r);
            }
        }
        response.sendRedirect("resume");
    }
}
