package biz.neustar.categories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * @author Naveen
 *
 */
public class CategoryFileTest {

    @Test
    public void testGetFile() {
        CategoryFile categoryFile = new CategoryFile();
        File file = categoryFile.getFile("file_with_Proper_Content.txt");
        assertTrue(file != null);
        assertTrue(file.length() > 0);
        assertEquals(file.getName(), "file_with_Proper_Content.txt");
    }

    @Test
    public void testGetFileWilthInvalidInput() {
        CategoryFile categoryFile = new CategoryFile();
        File file = categoryFile.getFile("file_123.txt");
        assertTrue(file == null);
    }

    @Test
    public void testGetUniqueCategories() {
        CategoryFile categoryFile = new CategoryFile();
        Set<String> uniqueCategories = categoryFile.getUniqueLines(categoryFile.getFile("file_with_Proper_Content.txt"));
        assertTrue(uniqueCategories != null);
        assertTrue(uniqueCategories.size() > 0);
    }
    
    @Test
    public void testGetUniqueCategoriesForEmptyFile() {
        CategoryFile categoryFile = new CategoryFile();
        Set<String> uniqueCategories = categoryFile.getUniqueLines(categoryFile.getFile("file_with_empty_content.txt"));
        assertTrue(uniqueCategories != null);
        assertTrue(uniqueCategories.size() == 0);
    }
    
    @Test
    public void testCountRepeatedCatogories() {
        CategoryFile categoryFile = new CategoryFile();
        Set<String> uniqueCategories = new LinkedHashSet<String>();

        uniqueCategories.add("PERSON Bob Jones");
        uniqueCategories.add("PERSON Jones");
        uniqueCategories.add("Cat ANIMAL");
        uniqueCategories.add("ANIMAL Cat");
        uniqueCategories.add("FOOD Steak");

        Map<String, Integer> finalCategories = categoryFile.countRepeatedCatogories(uniqueCategories);
        assertTrue(finalCategories != null);
        assertTrue(finalCategories.size() > 0);
        assertEquals(finalCategories.get("PERSON").intValue(), 2);
        assertEquals(finalCategories.get("Cat"), null);
        assertEquals(finalCategories.get("ANIMAL").intValue(), 1);
        assertEquals(finalCategories.get("FOOD"), null);
    }
    
    @Test
    public void testCountRepeatedCatogoriesForWrongCategories() {
        CategoryFile categoryFile = new CategoryFile();
        Set<String> uniqueCategories = new LinkedHashSet<String>();

        uniqueCategories.add("ABC Bob Jones");
        uniqueCategories.add("123 Jones");
        uniqueCategories.add("Cat ANIMAL");
        uniqueCategories.add("XYZ Cat");
        uniqueCategories.add("FOOD Steak");

        Map<String, Integer> finalCategories = categoryFile.countRepeatedCatogories(uniqueCategories);
        assertTrue(finalCategories != null);
        assertTrue(finalCategories.size() > 0);
        assertEquals(finalCategories.get("PERSON").intValue(), 0);
        assertEquals(finalCategories.get("Cat"), null);
        assertEquals(finalCategories.get("ANIMAL").intValue(), 0);
        assertEquals(finalCategories.get("FOOD"), null);
    }
    
    @Test
    public void testCountRepeatedCatogoriesForEmptyFile() {
        CategoryFile categoryFile = new CategoryFile();
        Set<String> uniqueCategories = new LinkedHashSet<String>();

        Map<String, Integer> finalCategories = categoryFile.countRepeatedCatogories(uniqueCategories);
        assertTrue(finalCategories != null);
        assertTrue(finalCategories.size() == 5);
        assertEquals(finalCategories.get("PERSON").intValue(), 0);
        assertEquals(finalCategories.get("Cat"), null);
        assertEquals(finalCategories.get("ANIMAL").intValue(), 0);
        assertEquals(finalCategories.get("FOOD"), null);
    }
    
    @Test
    public void testCountRepeatedCatogoriesForEmptyLines() {
        CategoryFile categoryFile = new CategoryFile();
        Set<String> uniqueCategories = new LinkedHashSet<String>();
        uniqueCategories.add("ABC Bob Jones");
        uniqueCategories.add("123 Jones");
        uniqueCategories.add("");
        uniqueCategories.add("XYZ Cat");
        uniqueCategories.add("FOOD Steak");
        Map<String, Integer> finalCategories = categoryFile.countRepeatedCatogories(uniqueCategories);
        assertTrue(finalCategories != null);
        assertTrue(finalCategories.size() == 5);
        assertEquals(finalCategories.get("PERSON").intValue(), 0);
        assertEquals(finalCategories.get(""), null);
        assertEquals(finalCategories.get("ANIMAL").intValue(), 0);
        assertEquals(finalCategories.get("FOOD"), null);
        assertEquals(finalCategories.get("PLACE").intValue(), 0);
        assertEquals(finalCategories.get("COMPUTER").intValue(), 0);
        assertEquals(finalCategories.get("OTHER").intValue(), 0);
    }
    
    @Test
    public void testProcessTheFile() {
        CategoryFile categoryFile = new CategoryFile();
        categoryFile.processTheFile("file_with_empty_sub_categories.txt");
    }
}
