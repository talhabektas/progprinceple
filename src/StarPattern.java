public class StarPattern extends Pattern {
    @Override
    public String generate(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            // Boşlukları ekle
            for (int j = 0; j < size - i - 1; j++) {
                sb.append("  ");
            }
            // Yıldızları ekle
            for (int k = 0; k < 2 * i + 1; k++) {
                sb.append("* ");
            }
            sb.append("\n"); // Satır sonunu ekle
        }
        return sb.toString();
    }
}
