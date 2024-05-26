//Generates a star pyramid pattern.
public class StarPattern implements Pattern {
    @Override
    public String generate(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                sb.append("  ");
            }
            for (int k = 0; k < 2 * i + 1; k++) {
                sb.append("* ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
