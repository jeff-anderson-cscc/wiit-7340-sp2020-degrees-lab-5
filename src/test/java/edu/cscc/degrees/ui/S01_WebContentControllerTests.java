package edu.cscc.degrees.ui;

import edu.cscc.degrees.api.MenuCategoryItems;
import edu.cscc.degrees.data.MenuItemRepository;
import edu.cscc.degrees.services.ContentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class S01_WebContentControllerTests {

  @MockBean private ContentService contentService;

  @Test
  @DisplayName("T01: GET to / returns index view")
  public void test01 (@Autowired MockMvc mockMvc) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(
      MockMvcResultMatchers.status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.view().name("index"));
  }

  @Test
  @DisplayName("T02: GET to /menu returns menu view and model attributes")
  public void test02 (@Autowired MockMvc mockMvc) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/menu")).andExpect(
      MockMvcResultMatchers.status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.view().name("menu"));
  }

  @Test
  @DisplayName("T03: GET to /menu returns expected menu model attributes")
  public void test03 (@Autowired MockMvc mockMvc) throws Exception {
    List<MenuCategoryItems> menuCategoryItems = new ArrayList<>();
    when(contentService.getMenuItems()).thenReturn(menuCategoryItems);
    mockMvc.perform(MockMvcRequestBuilders.get("/menu")).andExpect(
      MockMvcResultMatchers.status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.model().attributeExists("menuItems"))
      .andExpect(MockMvcResultMatchers.model().attribute("menuItems",
        equalTo(menuCategoryItems)))
      .andExpect(MockMvcResultMatchers.view().name("menu"));
    verify(contentService, times(1)).getMenuItems();
  }

}
