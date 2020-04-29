package edu.cscc.degrees.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class MenuCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @NotNull
  @Size(min = 1, max = 80,
    message = "Please enter a category title up to 80 characters in length")
  private String categoryTitle;
  private String categoryNotes;
  private int sortOrder;

  public MenuCategory() {  }

  public MenuCategory(long id, String categoryTitle,
    String categoryNotes, int sortOrder) {
    this.id = id;
    this.categoryTitle = categoryTitle;
    this.categoryNotes = categoryNotes;
    this.sortOrder = sortOrder;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCategoryTitle() {
    return categoryTitle;
  }

  public void setCategoryTitle(String categoryTitle) {
    this.categoryTitle = categoryTitle;
  }

  public String getCategoryNotes() {
    return categoryNotes;
  }

  public void setCategoryNotes(String categoryNotes) {
    this.categoryNotes = categoryNotes;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }
}
