package biz.neustar;

import biz.neustar.categories.CategoryFile;

public class FileProcessService {
    public static void main(String... args) {
        String fileName;
        if (args.length > 0) {
            fileName = args[0];
        } else {
            System.out.println("Please enter file path!");
            return;
        }
        new CategoryFile().processTheFile(fileName);
    }
}
