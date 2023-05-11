package com.ohouse.ohouse;

import com.ohouse.ohouse.config.SecurityConfig;
import com.ohouse.ohouse.controller.MainController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MainController.class)
@ExtendWith(SpringExtension.class)
@Import(SecurityConfig.class)
class MainControllerTest {

  @Autowired
  MockMvc mvc;

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
