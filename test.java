import org.owasp.encoder.Encode;

public class SanitizationUtil {
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // Basic sanitization can still be applied (optional)
        input = input.replaceAll("<script>", "").replaceAll("</script>", "");
        
        // Perform output encoding
        return Encode.forHtml(input); // Encode for HTML context
    }
}
