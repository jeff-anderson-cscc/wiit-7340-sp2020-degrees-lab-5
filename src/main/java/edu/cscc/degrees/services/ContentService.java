package edu.cscc.degrees.services;

import edu.cscc.degrees.api.MenuCategoryItems;
import edu.cscc.degrees.data.MenuCategoryRepository;
import edu.cscc.degrees.data.MenuItemRepository;
import edu.cscc.degrees.domain.MenuCategory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentService {

  private final MenuCategoryRepository menuCategoryRepository;
  private final MenuItemRepository menuItemRepository;

  public ContentService(
    MenuCategoryRepository menuCategoryRepository,
    MenuItemRepository menuItemRepository) {
    this.menuCategoryRepository = menuCategoryRepository;
    this.menuItemRepository = menuItemRepository;
  }

  public List<MenuCategoryItems> getMenuItems() {
    ArrayList<MenuCategoryItems> returnList = new ArrayList<>();

    Iterable<MenuCategory> menuCategories =
      menuCategoryRepository.findAll(Sort.by("sortOrder","categoryTitle"));

    for (MenuCategory menuCategory : menuCategories) {
      MenuCategoryItems menuCategoryItems = new MenuCategoryItems();
      menuCategoryItems.setMenuCategory(menuCategory);
      menuCategoryItems.setMenuItemList(menuItemRepository
        .findByMenuCategoryOrderBySortOrderAscNameAsc(menuCategory));
      returnList.add(menuCategoryItems);
    }

    return returnList;
  }



}
