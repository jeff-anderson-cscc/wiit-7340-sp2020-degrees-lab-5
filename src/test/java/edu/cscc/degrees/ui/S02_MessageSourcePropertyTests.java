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
public class S02_MessageSourcePropertyTests {

  @Test
  @DisplayName("T01: MessageSource has english and spanish about page messages")
  public void test01 (@Autowired MessageSource messageSource){
    Locale localeES = new Locale("ES");
    String[] keys = {"view.about.message", "view.about.title",
      "view.about.parking", "view.about.hours", "view.about.phone-label",
      "view.about.location-label", "view.about.hours-label"};
    for (String key: keys ) {
      String m1 = messageSource.getMessage(key, null, Locale.US);
      String m2 = messageSource.getMessage(key, null, localeES);
      assertNotNull(m1);
      assertNotNull(m2);
      assertNotEquals(m1, m2);
    }
  }

}
