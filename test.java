import org.owasp.encoder.Encode;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class SanitizationUtil {

    // Use OWASP HTML Sanitizer to sanitize user input
    private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    /**
     * Sanitizes the input to remove dangerous HTML/JS and escapes it for safe storage.
     *
     * @param value The input string to sanitize and escape.
     * @return The sanitized and escaped input.
     */
    public static String sanitizeAndEscapeInput(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Invalid content: input is blank");
        }

        // Sanitize the input using the OWASP sanitizer
        String sanitizedValue = POLICY.sanitize(value);

        // Escape the sanitized input to ensure it's safe for storage or further processing
        return Encode.forHtml(sanitizedValue);
    }

    /**
     * Escapes input for HTML rendering.
     *
     * @param value The input string to escape.
     * @return The escaped string for safe HTML rendering.
     */
    public static String escapeForHtml(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        // Escape the input to make it safe for HTML rendering
        return Encode.forHtml(value);
    }

    /**
     * Escapes input for JavaScript rendering.
     *
     * @param value The input string to escape.
     * @return The escaped string for safe JavaScript usage.
     */
    public static String escapeForJavaScript(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        // Escape the input to make it safe for JavaScript
        return Encode.forJavaScript(value);
    }

    /**
     * Escapes input for use in URLs.
     *
     * @param value The input string to escape.
     * @return The escaped string for safe use in URLs.
     */
    public static String escapeForUrl(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        // Escape the input to make it safe for URLs
        return Encode.forUriComponent(value);
    }
}
