package edu.cscc.degrees.ui;

import edu.cscc.degrees.data.MenuCategoryRepository;
import edu.cscc.degrees.domain.MenuCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class WebMenuCategoryController {
  private final MenuCategoryRepository menuCategoryRepository;

  public WebMenuCategoryController(
    MenuCategoryRepository menuCategoryRepository) {
    this.menuCategoryRepository = menuCategoryRepository;
  }

  // @GetMapping
  // public String getAllMenuCategories (Model model) {

  // @GetMapping("{id}")
  // public String getMenuCategoryById (@PathVariable Long id, Model model) {

  // @GetMapping("/new")
  // public String newMenuCategory (Model model) {

  @PostMapping
  public String saveMenuCategory (@Valid MenuCategory newMenuCategory,
  Errors errors, RedirectAttributes redirectAttributes) {
    if (errors.hasErrors()) {
      return "edit_category";
    }
    menuCategoryRepository.save(newMenuCategory);
    redirectAttributes.addFlashAttribute("message",
      String.format("Category '%s' saved", newMenuCategory.getCategoryTitle()));
    return "redirect:/categories";
  }

  // @GetMapping("/delete/{id}")
  // public String deleteMenuCategoryById (@PathVariable Long id, RedirectAttributes redirectAttributes) {


}
