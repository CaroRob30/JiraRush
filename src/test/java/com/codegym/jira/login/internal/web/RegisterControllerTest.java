package com.codegym.jira.login.internal.web;

import com.codegym.jira.AbstractControllerTest;
import com.codegym.jira.login.UserTo;
import com.codegym.jira.login.internal.verification.ConfirmData;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static com.codegym.jira.login.internal.web.RegisterController.REGISTER_URL;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegisterControllerTest extends AbstractControllerTest {

    @Test
    void showRegisterPage() throws Exception {
        perform(MockMvcRequestBuilders.get(REGISTER_URL))
                .andExpect(status().isOk())
                .andExpect(view().name("unauth/register"));
    }

    @Test
    @WithUserDetails(UserTestData.USER_MAIL)
    void showRegisterPageWhenAuthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REGISTER_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    void register() throws Exception {
        UserTo newTo = new UserTo(null, "newemail@gmail.com", "newPassword", "newName", "newLastName", "newDisplayName");

        Object sessionToken = Objects.requireNonNull(perform(MockMvcRequestBuilders.post(REGISTER_URL)
                        .param("email", "newemail@gmail.com")
                        .param("password", "newPassword")
                        .param("firstName", "newName")
                        .param("lastName", "newLastName")
                        .param("displayName", "newDisplayName")
                        .with(csrf()))
                        .andExpect(status().isFound())
                        .andExpect(redirectedUrl("/view/login"))
                        .andReturn()
                        .getRequest()
                        .getSession())
                .getAttribute("token");

        assertNotNull(sessionToken);
        assertInstanceOf(ConfirmData.class, sessionToken);
        UserTo sessionTo = ((ConfirmData) sessionToken).getUserTo();
        UserTestData.TO_MATCHER.assertMatch(sessionTo, newTo);
    }

    @Test
    @WithUserDetails(UserTestData.USER_MAIL)
    void registerWhenAuthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(REGISTER_URL)
                .param("email", "newemail@gmail.com")
                .param("password", "newPassword")
                .param("firstName", "newName")
                .param("lastName", "newLastName")
                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    void registerInvalid() throws Exception {
        perform(MockMvcRequestBuilders.post(REGISTER_URL)
                .param("email", "")
                .param("password", "")
                .param("firstName", "newName")
                .param("lastName", "newLastName")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("userTo", "email", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("unauth/register"));
    }

    @Test
    void registerDuplicateEmail() throws Exception {
        perform(MockMvcRequestBuilders.post(REGISTER_URL)
                .param("email", UserTestData.USER_MAIL)
                .param("password", "newPassword")
                .param("firstName", "newName")
                .param("lastName", "newLastName")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrorCode("userTo", "email", "Duplicate"))
                .andExpect(status().isOk())
                .andExpect(view().name("unauth/register"));
    }
}
