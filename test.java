Yes, API keys for control plane access are tied to specific roles and projects and created by the Platform (ME) team.

For console access, TMD requests are needed, which is standard internal access control flow.

Data plane authorization (e.g., who can access collections, read/write permissions) is done by the application teams using Infrastructure-as-Code (IaC) tools like Terraform.

These changes are done via SDLC pipelines, ensuring traceability and approvals.
