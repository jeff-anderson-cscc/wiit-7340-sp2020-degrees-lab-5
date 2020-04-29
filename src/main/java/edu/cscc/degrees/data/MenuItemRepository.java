package edu.cscc.degrees.data;

import edu.cscc.degrees.domain.MenuCategory;
import edu.cscc.degrees.domain.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MenuItemRepository extends PagingAndSortingRepository<MenuItem, Long> {
  Iterable<MenuItem> findByMenuCategoryOrderBySortOrderAscNameAsc(MenuCategory category);
}
