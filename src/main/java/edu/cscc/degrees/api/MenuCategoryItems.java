package edu.cscc.degrees.api;

import edu.cscc.degrees.domain.MenuCategory;
import edu.cscc.degrees.domain.MenuItem;

public class MenuCategoryItems {

  private MenuCategory menuCategory;
  private Iterable<MenuItem> menuItemList;

  public MenuCategoryItems() {
  }

  public MenuCategoryItems(MenuCategory menuCategory,
    Iterable<MenuItem> menuItemList) {
    this.menuCategory = menuCategory;
    this.menuItemList = menuItemList;
  }

  public MenuCategory getMenuCategory() {
    return menuCategory;
  }

  public void setMenuCategory(MenuCategory menuCategory) {
    this.menuCategory = menuCategory;
  }

  public Iterable<MenuItem> getMenuItemList() {
    return menuItemList;
  }

  public void setMenuItemList(
    Iterable<MenuItem> menuItemList) {
    this.menuItemList = menuItemList;
  }
}
