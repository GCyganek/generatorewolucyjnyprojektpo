package outputFileService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileManagement {

    public static void clearFile(String path) {
        try {
            FileWriter fw = new FileWriter(path, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("");
            bw.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void appendToFile(String path, String textToAppend) {
        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(textToAppend + '\n');
            bw.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
