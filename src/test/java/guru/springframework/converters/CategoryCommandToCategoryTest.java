package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {
    private CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp() {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    void convert() {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(2L);
        categoryCommand.setCategoryName("amr anwr");
        Category category = categoryCommandToCategory.convert(categoryCommand);
        assertNotNull(category);
        assertEquals(category.getId(), categoryCommand.getId());
        assertEquals(category.getCategoryName(),categoryCommand.getCategoryName());
    }
}