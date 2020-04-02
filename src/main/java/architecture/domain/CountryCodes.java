package architecture.domain;

public enum CountryCodes {

    BG("<svg viewBox=\"0 0 26 16\">\n" +
                "<rect width=\"26\" height=\"16\" style=\"fill:#fff\"></rect>\n" +
                "<rect y=\"5.33\" width=\"26\" height=\"10.67\" style=\"fill:#59b44b\"></rect>\n" +
                "<rect y=\"10.67\" width=\"26\" height=\"5.33\" style=\"fill:#c42127\"></rect>\n" +
            "</svg>"),
    FR("<svg viewBox=\"0 0 26 16\">\n" +
            "<rect width=\"8.67\" height=\"16\" style=\"fill:#2b3379\"></rect>\n" +
            "<rect x=\"8.67\" width=\"8.67\" height=\"16\" style=\"fill:#f4d130\"></rect>\n" +
            "<rect x=\"17.33\" width=\"8.67\" height=\"16\" style=\"fill:#bd1f34\"></rect>\n" +
            "</svg>"),
    DE("bg"),
    ES("bg"),
    EN("bg");

    private String svg;

    private CountryCodes(String svg) {
        this.svg = svg;
    }

    public String getSvg() {
        return svg;
    }
}
