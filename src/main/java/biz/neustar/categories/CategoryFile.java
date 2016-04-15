package biz.neustar.categories;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class CategoryFile {

    public static final List<String> LEGAL_CATEGORIES = Arrays.asList("PERSON", "PLACE", "ANIMAL", "COMPUTER", "OTHER");

    /**
     * It Will Process the given file.
     * 
     * @param fileName
     *            is from the resource folder
     */
    public void processTheFile(String fileName) {

        File file = getFile(fileName);
        if (file == null) {
            System.out.println("Please pass the file from resource folder!");
            return;
        }
        Set<String> uniqueLinesSet = getUniqueLines(file);

        Map<String, Integer> validCategories = countRepeatedCatogories(uniqueLinesSet);

        printOutput(uniqueLinesSet, validCategories);
    }

    /**
     * It will print the output to console
     * 
     * @param uniqueCategories This set contains Unique valid categories and sub categories.
     * @param validCategories This map contains Count for all the valid categories here Category as key and Count as value. 
     */
    private void printOutput(Set<String> uniqueCategories, Map<String, Integer> validCategories) {
        System.out.println("CATEGORY          COUNT");
        validCategories.keySet().stream()
                        .forEach(key -> System.out.println(key + "            " + validCategories.get(key)));
        System.out.println();
        uniqueCategories.stream().forEach(line -> System.out.println(line));
    }

    /**
     * Returns a set with unique lines of the given file.
     * 
     * @param file is from resource folder to process.
     * @return a set with unique lines of the given file.
     */
    public Set<String> getUniqueLines(File file) {
        Set<String> uniqueLinesSet = new LinkedHashSet<String>();
        try (Stream<String> stream = Files.lines(file.toPath())) {
            stream.forEach(validValue -> uniqueLinesSet.add(validValue.trim()));
            return uniqueLinesSet;

        } catch (IOException e) {
            System.out.println("Problem while processing the given file, below is the stackTrace.");
            e.printStackTrace();
        }
        return uniqueLinesSet;
    }

    /**
     * It Loads the File from resource folder if the given filename is correct.
     * 
     * @param fileName is to load file from resource folder.
     * @return File object if the given filename is correct, otherwise returns null
     */
    public File getFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(fileName);

        if (url != null) {
            return new File(url.getFile());
        } else {
            return null;
        }
    }

    /**
     * It Count the repeated valid categories and set the values into MAP and removes invalid categories from uniqueLinesSet
     * 
     * @param uniqueLinesSet is contains unique lines of the file.
     * @return Map. Count the repeated valid categories and set the values into MAP. here category
     * is key, count is value
     */
    public Map<String, Integer> countRepeatedCatogories(Set<String> uniqueLinesSet) {
        Map<String, Integer> finalCategories = getEmptyCountCategories();
        for (Iterator<String> itr = uniqueLinesSet.iterator(); itr.hasNext();) {
            String line = itr.next();
            if (line.isEmpty()) {
                itr.remove();
            } else {
                String fileCategory = line.split(" ")[0];
                if (isValidCategory(line, fileCategory)) {
                    if (finalCategories.containsKey(fileCategory)) {
                        finalCategories.put(fileCategory, finalCategories.get(fileCategory).intValue() + 1);
                    }
                } else {
                    itr.remove();
                }
            }
        }
        return finalCategories;
    }

    /**
     * @return returns true if line not equals to fileCategory, and if fileCategory present in
     *         LEGAL_CATEGORIES.
     * @param line
     *            from the file
     * @param fileCategory
     *            Category from the file.
     */
    private boolean isValidCategory(String line, String fileCategory) {
        return fileCategory.length() != line.length() && !fileCategory.isEmpty()
                        && LEGAL_CATEGORIES.contains(fileCategory);
    }

    /**
     * Returns a map legal_categories as keys with values as 0
     * @returns a map legal_categories as keys with values as 0
     */
    public Map<String, Integer> getEmptyCountCategories() {
        Map<String, Integer> finalCategories = new LinkedHashMap<String, Integer>();

        for (String category : LEGAL_CATEGORIES) {
            finalCategories.put(category, 0);
        }
        return finalCategories;
    }
}
