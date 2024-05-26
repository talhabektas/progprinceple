import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Main {

    public static void processInputFile(String filename, Printer printer, int inputNumber) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        boolean firstLine = true;
        for (String line : lines) {
            if (firstLine) {
                firstLine = false;
                continue; // Skip header line
            }

            String[] parts = line.split("\\s+");
            int requestTime = Integer.parseInt(parts[0]);
            String patternType = parts[1];
            int outputSize = Integer.parseInt(parts[2]);

            // Simulate the request time
            try {
                Thread.sleep(requestTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Determine the pattern type and create the appropriate pattern object
            Pattern pattern;
            if ("Alphabet".equals(patternType)) {
                pattern = new AlphabetPattern();
            } else if ("Star".equals(patternType)) {
                pattern = new StarPattern();
            } else {
                System.err.println("Unknown design pattern: " + patternType);
                continue;
            }

            // Add the request to the printer
            printer.addRequest(pattern, patternType, outputSize);
        }
    }

    public static void main(String[] args) {
        Printer printer = new Printer();

        try {
            processInputFile("input1.txt", printer, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}