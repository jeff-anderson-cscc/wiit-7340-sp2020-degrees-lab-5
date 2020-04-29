package edu.cscc.degrees.ui;

import edu.cscc.degrees.data.MenuCategoryRepository;
import edu.cscc.degrees.data.MenuItemRepository;
import edu.cscc.degrees.domain.MenuItem;
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
@RequestMapping("/items")
public class WebMenuItemController {
  private final MenuItemRepository menuItemRepository;
  private final MenuCategoryRepository menuCategoryRepository;

  public WebMenuItemController(MenuItemRepository menuItemRepository,
    MenuCategoryRepository menuCategoryRepository) {
    this.menuItemRepository = menuItemRepository;
    this.menuCategoryRepository = menuCategoryRepository;
  }

  //@GetMapping
  // public String getAllMenuItems (Model model) {


  @GetMapping("{id}")
  public String getMenuItemById (@PathVariable Long id, Model model) {

    Optional<MenuItem> menuItem = menuItemRepository.findById(id);
    if (menuItem.isPresent()) {
      model.addAttribute("menuItem", menuItem.get());
      model.addAttribute("categoryList", menuCategoryRepository.findAll());
      return "edit_item";
    }
    return "error/404";
  }

  // @GetMapping("/new")
  // public String newMenuItem (Model model) {

  // @PostMapping
  // public String saveMenuItem (@Valid MenuItem newMenuItem,
  //  Errors errors, RedirectAttributes redirectAttributes) {

  @GetMapping("/delete/{id}")
  public String deleteMenuItemById (@PathVariable Long id, RedirectAttributes redirectAttributes) {

    Optional<MenuItem> menuItem = menuItemRepository.findById(id);
    if (menuItem.isPresent()) {
      menuItemRepository.delete(menuItem.get());
      redirectAttributes.addFlashAttribute("message", String.format("Item %d deleted", id));
    } else {
      redirectAttributes.addFlashAttribute("error", "True");
      redirectAttributes.addFlashAttribute("message", String.format("Item %d not found", id));
    }
    return "redirect:/items";
  }

}
