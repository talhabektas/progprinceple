public class PrintRequest {
    public Pattern pattern;
    public String patternType;
    public int size;
    public int inputNumber;

    public PrintRequest(Pattern pattern, String patternType, int size, int inputNumber) {
        this.pattern = pattern;
        this.patternType = patternType;
        this.size = size;
        this.inputNumber = inputNumber;
    }
}
