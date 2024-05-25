public class StarPattern extends Pattern {
    @Override
    public String generate(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int stars = 2 * i + 1;
            sb.append(" ".repeat(size - i - 1));
            sb.append("* ".repeat(stars));
            sb.append("\n");
        }
        return sb.toString();
    }
}
