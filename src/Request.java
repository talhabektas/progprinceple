public class Request {
    public final Pattern pattern;
    public final String patternType;
    public final int outputSize;

    public Request(Pattern pattern, String patternType, int outputSize) {
        this.pattern = pattern;
        this.patternType = patternType;
        this.outputSize = outputSize;
    }
}
