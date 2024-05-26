import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.nio.file.*;

/**
 * A printer class that manages print requests using concurrency techniques.
 */
public class Printer {
    private final Queue<PrintRequest> requests = new LinkedList<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Object lock = new Object();
    private boolean isPrinting = false;
    private String currentPatternType = "";

    /**
     * Adds a print request to the queue.
     * @param pattern The pattern to print.
     * @param patternType The type of pattern.
     * @param size The size of the pattern.
     */
    public void addRequest(Pattern pattern, String patternType, int size) {
        synchronized (lock) {
            requests.add(new PrintRequest(pattern, patternType, size, size*size));
            if (!isPrinting) {
                processRequests();
            }
        }
    }

    /**
     * Processes the print requests.
     */
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

            CountDownLatch latch = new CountDownLatch(sameTypeRequests.size());
            for (PrintRequest sameTypeRequest : sameTypeRequests) {
                executorService.submit(() -> {
                    printTask(sameTypeRequest.pattern, sameTypeRequest.size);
                    latch.countDown();
                });
            }

            executorService.submit(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (lock) {
                    isPrinting = false;
                    processRequests();
                }
            });
        }
    }

    /**
     * Simulates printing a pattern.
     * @param pattern The pattern to print.
     * @param size The size of the pattern.
     */
    private void printTask(Pattern pattern, int size) {
        try {
            Thread.sleep(size * size); // Simulate print time
            String output = pattern.generate(size);
            synchronized (lock) {
                String filename = "output.txt";
                String logname = "log.txt";

                // Log printing start
                Files.write(Paths.get(logname), ("Printing is started for " + currentPatternType + "-" + size + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

                // Perform the printing task
                Files.write(Paths.get(filename), (output + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

                // Log printing completion
                Files.write(Paths.get(logname), ("Printing is done for " + currentPatternType + "-" + size + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                Files.write(Paths.get(logname), ("Printer is free\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
