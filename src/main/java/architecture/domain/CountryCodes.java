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
    EN("bg");

    private String svg;

    private CountryCodes(String svg) {
        this.svg = svg;
    }

    public String getSvg() {
        return svg;
    }
}
