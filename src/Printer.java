import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.nio.file.*;

public class Printer {
    private final Queue<PrintRequest> requests = new LinkedList<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Object lock = new Object();
    private boolean isPrinting = false;
    private String currentPatternType = "";

    public void addRequest(Pattern pattern, String patternType, int size) {
        synchronized (lock) {
            requests.add(new PrintRequest(pattern, patternType, size));
            if (!isPrinting) {
                processRequests();
            }
        }
    }

    private void processRequests() {
        synchronized (lock) {
            if (requests.isEmpty()) {
                isPrinting = false;
                return;
            }

            PrintRequest request = requests.poll();
            currentPatternType = request.patternType;
            isPrinting = true;

            List<PrintRequest> sameTypeRequests = new ArrayList<>();
            sameTypeRequests.add(request);

            Iterator<PrintRequest> iterator = requests.iterator();
            while (iterator.hasNext()) {
                PrintRequest nextRequest = iterator.next();
                if (nextRequest.patternType.equals(currentPatternType)) {
                    sameTypeRequests.add(nextRequest);
                    iterator.remove();
                }
            }

            for (PrintRequest sameTypeRequest : sameTypeRequests) {
                executorService.submit(() -> printTask(sameTypeRequest.pattern, sameTypeRequest.size));
            }
        }
    }

    private void printTask(Pattern pattern, int size) {
        try {
            Thread.sleep(size * size); // Simulate printing time
            String output = pattern.generate(size);
            synchronized (lock) {
                Files.write(Paths.get("output.txt"), output.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                Files.write(Paths.get("log.txt"), ("Printing is done for " + (pattern instanceof StarPattern ? "Star-" : "Alphabet-") + size + "\nPrinter is free\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            synchronized (lock) {
                if (requests.isEmpty()) {
                    isPrinting = false;
                } else {
                    processRequests();
                }
            }
        }
    }
}
