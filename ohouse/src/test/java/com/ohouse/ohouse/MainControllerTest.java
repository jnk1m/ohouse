package com.ohouse.ohouse;

import com.ohouse.ohouse.config.RedisConfig;
import com.ohouse.ohouse.controller.MainController;
import com.ohouse.ohouse.security.auth.CustomOAuth2UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MainController.class)
@Import(RedisConfig.class)
@ActiveProfiles("dev")
class MainControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CustomOAuth2UserService customOAuth2UserService;

  @Test
  void testIndex() throws Exception {
    mvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
  }

  @Test
  void testUserAgreement() throws Exception {
    mvc.perform(get("/policies/user-agreement"))
            .andExpect(status().isOk())
            .andExpect(view().name("footer/user-agreement"));
  }

  @Test
  void testPrivacyPolicy() throws Exception {
    mvc.perform(get("/policies/privacy-policy"))
            .andExpect(status().isOk())
            .andExpect(view().name("footer/privacy-policy"));
  }

  @Test
  void testContacts() throws Exception {
    mvc.perform(get("/contacts"))
            .andExpect(status().isOk())
            .andExpect(view().name("contacts"));
  }

  @Test
  void testAbout() throws Exception {
    mvc.perform(get("/about"))
            .andExpect(status().isOk())
            .andExpect(view().name("about"));
  }

}
