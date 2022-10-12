package readability;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {

    public static String readFileToString(String fName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fName)));
    }

    public static void main(String[] args) {

        try {
            String text = readFileToString(args[0]);

            Processor processor = new Processor(text, 2);

            String textSummary = processor.getSummary();
            System.out.println(textSummary);

            String textScores = processor.getIndex();
            System.out.println(textScores);

        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }
}
