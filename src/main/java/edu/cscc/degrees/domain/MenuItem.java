package edu.cscc.degrees.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class MenuItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  @ManyToOne
  private MenuCategory menuCategory;
  @NotNull
  @Size(min = 1, max = 80,
    message = "Please enter a name up to 80 characters in length")
  private String name;
  private String description;
  @NotNull
  @Size(min = 1, max = 20,
    message = "Please enter a price up to 20 characters in length")
  private String price;
  private int sortOrder;

  public MenuItem() {
  }

  public MenuItem(Long id, MenuCategory menuCategory, String name,
    String description, String price, int sortOrder) {
    this.id = id;
    this.menuCategory = menuCategory;
    this.name = name;
    this.description = description;
    this.price = price;
    this.sortOrder = sortOrder;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public MenuCategory getMenuCategory() {
    return menuCategory;
  }

  public void setMenuCategory(MenuCategory menuCategory) {
    this.menuCategory = menuCategory;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int displayOrder) {
    this.sortOrder = displayOrder;
  }
}
