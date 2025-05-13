package com.codegym.jira.project.internal.web;

import com.codegym.jira.login.internal.web.UserTestData;
import com.codegym.jira.AbstractControllerTest;
import com.codegym.jira.bugtracking.project.Project;
import com.codegym.jira.bugtracking.project.ProjectRepository;
import com.codegym.jira.common.BaseHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.codegym.jira.common.util.JsonUtil.writeValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerTest extends AbstractControllerTest {
    private static final String REST_URL_PROJECT = BaseHandler.REST_URL + "/projects";
    private static final String REST_URL_MNGR_PROJECT = BaseHandler.REST_URL + "/mngr/projects";

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROJECT + "/" + ProjectTestData.PARENT_PROJECT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ProjectTestData.PROJECT_TO_MATCHER.contentJson(ProjectTestData.projectTo1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROJECT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.MANAGER_MAIL)
    void update() throws Exception {
        ProjectTestData.projectTo2.setTitle("PROJECT-2 UPD");
        perform(MockMvcRequestBuilders.put(REST_URL_MNGR_PROJECT + "/" + ProjectTestData.PROJECT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(ProjectTestData.projectTo2)))
                .andDo(print())
                .andExpect(status().isNoContent());
        ProjectTestData.PROJECT_TO_MATCHER.assertMatch(ProjectTestData.projectTo2, ProjectTestData.getUpdated());
    }

    @Test
    @WithUserDetails(value = UserTestData.MANAGER_MAIL)
    void createNoBody() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_MNGR_PROJECT)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.MANAGER_MAIL)
    void create() throws Exception {
        Project newProject = ProjectTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_MNGR_PROJECT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newProject)))
                .andExpect(status().isCreated());
        Project created = ProjectTestData.PROJECT_MATCHER.readFromJson(action);
        ProjectTestData.PROJECT_MATCHER.assertMatch(created, newProject);
        ProjectTestData.PROJECT_MATCHER.assertMatch(projectRepository.getExisted(created.id()), newProject);
    }

    @Test
    @WithUserDetails(value = UserTestData.MANAGER_MAIL)
    void disable() throws Exception {
        ProjectTestData.projectTo2.setEnabled(false);
        perform(MockMvcRequestBuilders.put(REST_URL_MNGR_PROJECT + "/" + ProjectTestData.PROJECT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(ProjectTestData.projectTo2)))
                .andDo(print())
                .andExpect(status().isNoContent());
        ProjectTestData.PROJECT_TO_MATCHER.assertMatch(ProjectTestData.projectTo2, ProjectTestData.getDisabled());
    }
}

