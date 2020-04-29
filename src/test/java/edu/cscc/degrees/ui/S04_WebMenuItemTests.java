package edu.cscc.degrees.ui;

import edu.cscc.degrees.data.MenuCategoryRepository;
import edu.cscc.degrees.data.MenuItemRepository;
import edu.cscc.degrees.domain.MenuCategory;
import edu.cscc.degrees.domain.MenuItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

@SpringBootTest
@AutoConfigureMockMvc
public class S04_WebMenuItemTests {

  @MockBean
  private MenuItemRepository mockRepository;
  @MockBean
  private MenuCategoryRepository menuCategoryRepository;

  private static final String RESOURCE_URI = "/items";
  private static final MenuCategory testCategory =
    new MenuCategory(1L, "Sandwiches", "Served with fries", 10);
  private static final MenuItem testItem =
    new MenuItem(0L, testCategory, "Smoked Turkey Grinder",
      "Mozzarella, iceberg, onion, red wine vinaigrette", "12", 10);
  private static final MenuItem savedTestItem =
    new MenuItem(1L, testCategory, "Smoked Turkey Grinder",
      "Mozzarella, iceberg, onion, red wine vinaigrette", "12", 10);
  private final List<MenuCategory> categoryList = Collections.singletonList(testCategory);
  

  @Test
  @DisplayName("T01: GET to /items returns proper model and view")
  public void test01 (@Autowired MockMvc mockMvc) throws Exception {
    List<MenuItem> menuItems = Collections.singletonList(savedTestItem);
    when(mockRepository.findAll()).thenReturn(menuItems);
    mockMvc.perform(MockMvcRequestBuilders.get(RESOURCE_URI)).andExpect(
      MockMvcResultMatchers.status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.model().attributeExists("itemList"))
      .andExpect(MockMvcResultMatchers.model().attribute("itemList",
        equalTo(menuItems)))
      .andExpect(MockMvcResultMatchers.view().name("items"));
    verify(mockRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("T02: GET to /items/{id} returns proper model and view")
  public void test02 (@Autowired MockMvc mockMvc) throws Exception {
    when(mockRepository.findById(anyLong()))
      .thenReturn(Optional.of(savedTestItem));
    when(menuCategoryRepository.findAll())
      .thenReturn(categoryList);
    mockMvc.perform(get(RESOURCE_URI + "/1"))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.model().attributeExists("menuItem"))
      .andExpect(MockMvcResultMatchers.model().attribute("menuItem",
        equalTo(savedTestItem)))
      .andExpect(MockMvcResultMatchers.model().attributeExists("categoryList"))
      .andExpect(MockMvcResultMatchers.model().attribute("categoryList",
        equalTo(categoryList)))
      .andExpect(MockMvcResultMatchers.view().name("edit_item"));
    verify(mockRepository, times(1)).findById(anyLong());
  }

  @Test
  @DisplayName("T03: GET to /items/100 returns not found view")
  public void test03 (@Autowired MockMvc mockMvc) throws Exception {
    when(mockRepository.findById(100L)).thenReturn(Optional.empty());
    mockMvc.perform(get(RESOURCE_URI + "/100"))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("menuItem"))
      .andExpect(MockMvcResultMatchers.view().name("error/404"));
    verify(mockRepository, times(1)).findById(100L);
  }

  @Test
  @DisplayName("T04: GET to /items/new returns create model and view")
  public void test04 (@Autowired MockMvc mockMvc) throws Exception {
    when(menuCategoryRepository.findAll())
      .thenReturn(categoryList);
    mockMvc.perform(get(RESOURCE_URI + "/new"))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
      .andExpect(content().encoding("UTF-8"))
      .andExpect(MockMvcResultMatchers.model().attributeExists("menuItem"))
      .andExpect(MockMvcResultMatchers.model().attribute("menuItem",
        instanceOf(MenuItem.class)))
      .andExpect(MockMvcResultMatchers.model().attributeExists("categoryList"))
      .andExpect(MockMvcResultMatchers.model().attribute("categoryList",
        equalTo(categoryList)))
      .andExpect(MockMvcResultMatchers.view().name("edit_item"));
  }

  @Test
  @DisplayName("T05: POST valid new item works")
  public void test05 (@Autowired MockMvc mockMvc) throws Exception {
    when(mockRepository.save(any(MenuItem.class))).thenReturn(savedTestItem);
    mockMvc.perform(post(RESOURCE_URI).contentType(MediaType.APPLICATION_JSON)
      .param("menuCategory.id", "1")
      .param("name", "Foo")
      .param("price", "12"))
      .andExpect(status().isFound())
      .andExpect(MockMvcResultMatchers.view().name("redirect:/items"))
      .andExpect(flash().attributeCount(1))
      .andExpect(flash().attribute("message", "Item 'Foo' saved"));
    verify(mockRepository, times(1)).save(any(MenuItem.class));
  }

  @Test
  public void postInvalidCreatesNewBlogItem_Test (@Autowired MockMvc mockMvc) throws Exception {
    mockMvc.perform(post(RESOURCE_URI).contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(flash().attributeCount(0))
      .andExpect(MockMvcResultMatchers.view().name("edit_item"))
      .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("menuItem","name"))
      .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("menuItem","menuCategory"))
      .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("menuItem","price"));
    verify(mockRepository, never()).save(any(MenuItem.class));
  }

  @Test
  public void deleteValidBlogItemWorksProperly_Test (@Autowired MockMvc mockMvc) throws Exception {
    when(mockRepository.findById(1L)).thenReturn(Optional.of(savedTestItem));
    mockMvc.perform(get(RESOURCE_URI + "/delete/1"))
      .andExpect(MockMvcResultMatchers.status().isFound())
      .andExpect(flash().attributeCount(1))
      .andExpect(flash().attribute("message", "Item 1 deleted"))
      .andExpect(MockMvcResultMatchers.view().name("redirect:/items"));
    Mockito.verify(mockRepository, times(1)).delete(any(MenuItem.class));
  }

  @Test
  public void deleteInvalidBlogItemReturnsNotFound_Test (@Autowired MockMvc mockMvc) throws Exception {
    when(mockRepository.findById(1L)).thenReturn(Optional.empty());
    mockMvc.perform(get(RESOURCE_URI + "/delete/1"))
      .andExpect(MockMvcResultMatchers.status().isFound())
      .andExpect(flash().attributeCount(2))
      .andExpect(flash().attribute("error", "True"))
      .andExpect(flash().attribute("message", "Item 1 not found"))
      .andExpect(MockMvcResultMatchers.view().name("redirect:/items"));
    verify(mockRepository, never()).delete(any(MenuItem.class));
  }
  

}
