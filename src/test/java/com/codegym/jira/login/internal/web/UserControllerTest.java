package com.codegym.jira.login.internal.web;

import com.codegym.jira.AbstractControllerTest;
import com.codegym.jira.login.User;
import com.codegym.jira.login.UserTo;
import com.codegym.jira.login.internal.UserMapper;
import com.codegym.jira.login.internal.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.codegym.jira.common.internal.config.SecurityConfig.PASSWORD_ENCODER;
import static com.codegym.jira.common.util.JsonUtil.writeValue;
import static com.codegym.jira.login.internal.web.UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL;
import static com.codegym.jira.login.internal.web.UserController.REST_URL;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {

    @Autowired
    UserMapper mapper;
    @Autowired
    private UserRepository repository;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.user));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.admin));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createWithLocation() throws Exception {
        UserTo newTo = mapper.toTo(UserTestData.getNew());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(newTo, newTo.getPassword())))
                .andExpect(status().isCreated());

        User created = UserTestData.USER_MATCHER.readFromJson(action);
        long newId = created.id();
        User newUser = UserTestData.getNew();
        newUser.setId(newId);
        UserTestData.USER_MATCHER.assertMatch(created, newUser);
        UserTestData.USER_MATCHER.assertMatch(repository.getExisted(newId), newUser);
    }

    @Test
    void createInvalid() throws Exception {
        UserTo invalid = new UserTo(null, "", null, "Aa", "", "");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(invalid, invalid.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createDuplicateEmail() throws Exception {
        UserTo expected = new UserTo(null, UserTestData.USER_MAIL, "newPass", "duplicateFirstName", "duplicateLastName", "duplicateDisplayName");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(expected, expected.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_EMAIL)));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void update() throws Exception {
        User dbUserBefore = repository.getExistedByEmail(UserTestData.USER_MAIL);
        UserTo updatedTo = mapper.toTo(UserTestData.getUpdated());
        updatedTo.setPassword(null);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(updatedTo, updatedTo.getPassword())))
                .andDo(print())
                .andExpect(status().isNoContent());

        User dbUserAfter = repository.getExistedByEmail(UserTestData.USER_MAIL);
        assertEquals(dbUserBefore.getPassword(), dbUserAfter.getPassword(), "user's password must not be changed");
        User updated = UserTestData.getUpdated();
        updated.setRoles(dbUserBefore.getRoles());   // user's roles must not be changed
        UserTestData.USER_MATCHER.assertMatch(dbUserAfter, updated);
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void updateDuplicate() throws Exception {
        UserTo updatedTo = mapper.toTo(UserTestData.getUpdated());
        updatedTo.setEmail(UserTestData.ADMIN_MAIL);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_EMAIL)));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void updateInvalid() throws Exception {
        UserTo invalid = mapper.toTo(UserTestData.getUpdated());
        invalid.setFirstName("");
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findByEmailIgnoreCase(UserTestData.USER_MAIL).isPresent());
    }

    @Test
    void deleteUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void changePassword() throws Exception {
        String changedPassword = "changedPassword";
        perform(MockMvcRequestBuilders.post(REST_URL + "/change_password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("oldPassword", "password")
                .param("newPassword", changedPassword))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertTrue(PASSWORD_ENCODER.matches(changedPassword, repository.getExisted(UserTestData.USER_ID).getPassword()));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void changePasswordInvalid() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/change_password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("oldPassword", "password")
                .param("newPassword", "cp"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void changePasswordUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/change_password"))
                .andExpect(status().isUnauthorized());
    }
}
