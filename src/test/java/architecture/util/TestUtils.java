package architecture.util;

public class TestUtils {
    public static String escapeHTML(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c){
                case '\'':
                case '"':
                case '<':
                case '>':
                case '&': {
                    out.append("&#");
                    out.append((int) c);
                    out.append(';');
                }break;
                default:{
                    out.append(c);
                }
            }
        }
        return out.toString();
    }
}
