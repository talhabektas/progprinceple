import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void processInputFile(String filename, Printer printer) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        boolean firstLine = true;
        for (String line : lines) {
            if (firstLine) {
                firstLine = false;
                continue;
            }

            String[] parts = line.split("\\s+");
            int requestTime = Integer.parseInt(parts[0]);
            String patternType = parts[1];
            int outputSize = Integer.parseInt(parts[2]);

            try {
                Thread.sleep(requestTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Pattern pattern;
            if ("Star".equals(patternType)) {
                pattern = new StarPattern();
            } else if ("Alphabet".equals(patternType)) {
                pattern = new AlphabetPattern();
            } else {
                System.err.println("Unknown pattern type: " + patternType);
                continue;
            }

            printer.addRequest(pattern, patternType, outputSize);
        }
    }

    public static void main(String[] args) {
        Printer printer = new Printer();

        try {
            processInputFile("input1.txt", printer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
