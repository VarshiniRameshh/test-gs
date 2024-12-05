import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class SanitizationUtil {

    // Combine sanitization policies for safe HTML
    private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    /**
     * Sanitizes the given input to remove potential XSS payloads.
     *
     * @param input the user-provided input
     * @return sanitized output
     */
    public static String sanitize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        // Sanitize the input using OWASP Java HTML Sanitizer
        return POLICY.sanitize(input);
    }
}
