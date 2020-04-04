package architecture.domain;

public enum CountryCodes {

    BG("<rect width=\"26\" height=\"16\" style=\"fill:#fff\"></rect>\n" +
            "<rect y=\"5.33\" width=\"26\" height=\"10.67\" style=\"fill:#59b44b\"></rect>\n" +
            "<rect y=\"10.67\" width=\"26\" height=\"5.33\" style=\"fill:#c42127\"></rect>\n"),

    FR("<rect width=\"8.67\" height=\"16\" style=\"fill:#0055A4\"></rect>\n" +
            "<rect x=\"8.67\" width=\"8.67\" height=\"16\" style=\"fill:#FFFFFF\"></rect>\n" +
            "<rect x=\"17.33\" width=\"8.67\" height=\"16\" style=\"fill:#EF4135\"></rect>\n"),

    DE("<rect width=\"26\" height=\"16\" style=\"fill:#000000\"></rect>\n" +
            "<rect y=\"5.33\" width=\"26\" height=\"10.67\" style=\"fill:#FF0000\"></rect>\n" +
            "<rect y=\"10.67\" width=\"26\" height=\"5.33\" style=\"fill:#FFCC00\"></rect>\n"),
    ES("bg"),
    EN("<rect width=\"26\" height=\"16\" style=\"fill:#fff\"></rect>\n" +
            "<polygon points=\"17.41 5.79 26 0.71 26 0 25.41 0 15.61 5.79 17.41 5.79\" style=\"fill:#bd1f38\"></polygon>\n" +
            "<polygon points=\"17 10.54 26 15.85 26 14.79 18.8 10.54 17 10.54\" style=\"fill:#bd1f38\"></polygon>\n" +
            "<polygon points=\"0 1.22 7.7 5.79 9.49 5.79 0 0.16 0 1.22\" style=\"fill:#bd1f38\"></polygon>\n" +
            "<polygon points=\"9.09 10.54 0 15.91 0 16 1.64 16 10.88 10.54 9.09 10.54\" style=\"fill:#bd1f38\"></polygon>\n" +
            "<polygon points=\"24.22 0 15.12 0 15.12 5.38 24.22 0\" style=\"fill:#2c3077\"></polygon>\n" +
            "<polygon points=\"11.1 0 2.03 0 11.1 5.38 11.1 0\" style=\"fill:#2c3077\"></polygon>\n" +
            "<polygon points=\"26 5.79 26 2.11 19.82 5.79 26 5.79\" style=\"fill:#2c3077\"></polygon>\n" +
            "<polygon points=\"26 14.17 26 10.54 19.82 10.54 26 14.17\" style=\"fill:#2c3077\"></polygon>\n" +
            "<polygon points=\"2.55 16 11.1 16 11.1 10.94 2.55 16\" style=\"fill:#2c3077\"></polygon>\n" +
            "<polygon points=\"15.12 16 23.7 16 15.12 10.94 15.12 16\" style=\"fill:#2c3077\"></polygon>\n" +
            "<polygon points=\"0 10.54 0 14.33 6.39 10.54 0 10.54\" style=\"fill:#2c3077\"></polygon>\n" +
            "<polygon points=\"0 5.79 6.39 5.79 0 1.98 0 5.79\" style=\"fill:#2c3077\"></polygon>\n" +
            "<polygon points=\"11.9 0 11.9 6.74 0 6.74 0 9.59 11.9 9.59 11.9 16 14.31 16 14.31 9.59 26 9.59 26 6.74 14.31 6.74 14.31 0 11.9 0\" style=\"fill:#bd1f38\"></polygon>");

    private String svg;

    private CountryCodes(String svg) {
        this.svg = svg;
    }

    public String getSvg() {
        return svg;
    }
}
