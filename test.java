The application is the root cause:

The application fails to sanitize/encode the input.
This allows the payload to execute in the browser, regardless of how the payload was injected.
Browser behavior is standard:

The browser executes the payload because the application delivered unsanitized content. The browser assumes the application is delivering safe content.
Not a Man-in-the-Middle Attack:

This is a legitimate vulnerability assessment where the request and response flow is under your control.
Security Impact:

If the issue is exploited, attackers can inject scripts that can lead to data theft, session hijacking, or other malicious activities.
Response to the Developer
You can explain the following to the developer:

It’s the application’s responsibility to sanitize data:

Unsanitized data should never be sent to the browser. If it is, the application is inherently insecure.
Browsers rely on the application to send safe content:

The browser is doing what it is designed to do—rendering content. It does not distinguish between malicious and safe scripts.
Real-World Exploitability:

If an attacker injects malicious scripts (e.g., through a form, query parameter, or header), the unsanitized response will execute in users’ browsers, leading to potential exploitation.
Mitigation:

Use proper input validation and output encoding (e.g., escaping special characters like <, >, and ").
Implement a Content Security Policy (CSP) to prevent inline scripts.
