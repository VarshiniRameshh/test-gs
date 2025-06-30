MongoDB Atlas uses API keys (a pair of public and private keys) to securely connect and manage resources through automation tools like Terraform and other approved DevOps systems.

Each API key is tied to a specific project, so it can’t be used across multiple projects — this helps keep access tightly controlled.

These keys are created and managed by the Middleware Engineering (ME) team, securely stored in AWS Secrets Manager, and are automatically rotated on a regular basis to keep them safe.

Access to these keys is restricted using strict allowlists and access policies, so only authorized teams or systems can use them.
