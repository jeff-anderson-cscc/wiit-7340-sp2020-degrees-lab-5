package edu.cscc.degrees.ui;

import edu.cscc.degrees.data.MenuCategoryRepository;
import edu.cscc.degrees.domain.MenuCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class S03_WebMenuCategoryControllerTests {

  private static final MenuCategory testCategory =
    new MenuCategory(0L, "Sandwiches", "Served with fries", 10);
  private static final MenuCategory savedTestCategory =
    new MenuCategory(1L, "Sandwiches", "Served with fries", 10);

  private static final String RESOURCE_URI = "/categories";

  @MockBean
  private MenuCategoryRepository mockRepository;

  @Test
  @DisplayName("T01: GET to /categories returns proper model and view")
  public void test01 (@Autowired MockMvc mockMvc) throws Exception {
    List<MenuCategory> menuCategories = new ArrayList<>();
    when(mockRepository.findAll()).thenReturn(menuCategories);
    mockMvc.perform(MockMvcRequestBuilders.get(RESOURCE_URI)).andExpect(
      MockMvcResultMatchers.status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.model().attributeExists("categoryList"))
      .andExpect(MockMvcResultMatchers.model().attribute("categoryList",
        equalTo(menuCategories)))
      .andExpect(MockMvcResultMatchers.view().name("categories"));
    verify(mockRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("T02: GET to /categories/{id} returns proper model and view")
  public void test02 (@Autowired MockMvc mockMvc) throws Exception {
    when(mockRepository.findById(anyLong()))
      .thenReturn(Optional.of(savedTestCategory));
    mockMvc.perform(get(RESOURCE_URI + "/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.model().attributeExists("menuCategory"))
      .andExpect(MockMvcResultMatchers.model().attribute("menuCategory",
        equalTo(savedTestCategory)))
      .andExpect(MockMvcResultMatchers.view().name("edit_category"));
    verify(mockRepository, times(1)).findById(anyLong());
  }

  @Test
  @DisplayName("T03: GET to /categories/100 returns not found view")
  public void test03 (@Autowired MockMvc mockMvc) throws Exception {
    when(mockRepository.findById(100L)).thenReturn(Optional.empty());
    mockMvc.perform(get(RESOURCE_URI + "/100"))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("menuCategory"))
      .andExpect(MockMvcResultMatchers.view().name("error/404"));
    verify(mockRepository, times(1)).findById(100L);
  }

  @Test
  @DisplayName("T04: GET to /categories/new returns create model and view")
  public void test04 (@Autowired MockMvc mockMvc) throws Exception {
    mockMvc.perform(get(RESOURCE_URI + "/new"))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.model().attributeExists("menuCategory"))
      .andExpect(MockMvcResultMatchers.model().attribute("menuCategory",
        instanceOf(MenuCategory.class)))
      .andExpect(MockMvcResultMatchers.view().name("edit_category"));
  }

  @Test
  @DisplayName("T05: POST valid new category works")
  public void test05 (@Autowired MockMvc mockMvc) throws Exception {
    when(mockRepository.save(any(MenuCategory.class))).thenReturn(savedTestCategory);
    mockMvc.perform(post(RESOURCE_URI).contentType(MediaType.APPLICATION_JSON)
      .param("categoryTitle", "Foo"))
      .andExpect(status().isFound())
      .andExpect(MockMvcResultMatchers.view().name("redirect:/categories"))
      .andExpect(flash().attributeCount(1))
      .andExpect(flash().attribute("message", "Category 'Foo' saved"));
    verify(mockRepository, times(1)).save(any(MenuCategory.class));
  }

  @Test
  public void postInvalidCreatesNewBlogCategory_Test (@Autowired MockMvc mockMvc) throws Exception {
    mockMvc.perform(post(RESOURCE_URI).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(flash().attributeCount(0))
      .andExpect(MockMvcResultMatchers.view().name("edit_category"))
      .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("menuCategory","categoryTitle"));
    verify(mockRepository, never()).save(any(MenuCategory.class));
  }

  @Test
  public void deleteValidBlogCategoryWorksProperly_Test (@Autowired MockMvc mockMvc) throws Exception {
    when(mockRepository.findById(1L)).thenReturn(Optional.of(savedTestCategory));
    mockMvc.perform(get(RESOURCE_URI + "/delete/1"))
      .andExpect(MockMvcResultMatchers.status().isFound())
      .andExpect(flash().attributeCount(1))
      .andExpect(flash().attribute("message", "Category 1 deleted"))
      .andExpect(MockMvcResultMatchers.view().name("redirect:/categories"));
    Mockito.verify(mockRepository, times(1)).delete(any(MenuCategory.class));
  }

  @Test
  public void deleteInvalidBlogCategoryReturnsNotFound_Test (@Autowired MockMvc mockMvc) throws Exception {
    when(mockRepository.findById(1L)).thenReturn(Optional.empty());
    mockMvc.perform(get(RESOURCE_URI + "/delete/1"))
      .andExpect(MockMvcResultMatchers.status().isFound())
      .andExpect(flash().attributeCount(2))
      .andExpect(flash().attribute("error", "True"))
      .andExpect(flash().attribute("message", "Category 1 not found"))
      .andExpect(MockMvcResultMatchers.view().name("redirect:/categories"));
    verify(mockRepository, never()).delete(any(MenuCategory.class));
  }


}
