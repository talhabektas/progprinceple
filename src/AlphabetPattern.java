//Generates an alphabet pyramid pattern.
public class AlphabetPattern implements Pattern {
    @Override
    public String generate(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(" ".repeat(2 * (size - i - 1)));
            char ch = (char) ('A' + i);
            for (int j = 0; j <= i; j++) {
                sb.append(ch++).append(" ");
            }
            ch -= 2;
            for (int j = i - 1; j >= 0; j--) {
                sb.append(ch--).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}