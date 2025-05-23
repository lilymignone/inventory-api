package inventory.inventory.controller;

import inventory.inventory.model.Category;
import inventory.inventory.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CategoryController {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    
    @Autowired
    private CategoryService categoryService;
    
    @PostMapping(consumes = { 
        MediaType.APPLICATION_JSON_VALUE,
        "application/json;charset=UTF-8"
    })
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            logger.debug("Attempting to create category: {}", category.getName());
            Category createdCategory = categoryService.createCategory(category);
            logger.info("Successfully created category with ID: {}", createdCategory.getId());
            return ResponseEntity.ok(createdCategory);
        } catch (Exception e) {
            logger.error("Error creating category: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            logger.debug("Attempting to update category with ID: {}", id);
            Category updatedCategory = categoryService.updateCategory(id, category);
            logger.info("Successfully updated category with ID: {}", id);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            logger.error("Error updating category: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            logger.debug("Fetching all categories");
            List<Category> categories = categoryService.getAllCategories();
            logger.debug("Found {} categories", categories.size());
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Error fetching categories: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try {
            logger.debug("Fetching category with ID: {}", id);
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            logger.error("Error fetching category: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            logger.debug("Attempting to delete category with ID: {}", id);
            categoryService.deleteCategory(id);
            logger.info("Successfully deleted category with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting category: {}", e.getMessage(), e);
            throw e;
        }
    }
}