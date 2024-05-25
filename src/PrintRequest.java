public class PrintRequest {
    Pattern pattern;
    String patternType;
    int size;

    public PrintRequest(Pattern pattern, String patternType, int size) {
        this.pattern = pattern;
        this.patternType = patternType;
        this.size = size;
    }
}
