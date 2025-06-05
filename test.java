Minutes of Meeting: MongoDB Atlas Deployment Discussion
Key Discussion Points

Deployment ID Mapping to Assets

Issue: Documentation lacks clarity on how deployment IDs map to assets.
Clarification: Top-level organizations in MongoDB Atlas are created for GS (Global Services) and aligned with specific use cases. Sub-organizations are also created for specific use cases, under which application teams create projects.
Projects are equivalent to application deployments and akin to Snowflake accounts but assigned at the deployment level. For example, a PC3-classified project restricts underlying data to PC3 or lower (PC0, PC1, PC2).
Access from non-PC3 systems to PC3 projects is restricted, with connectivity managed through access controls.


Organization and Project Structure

Hierarchy: Organization → Project → Cluster (referred to as an instance). There are no intermediate constructs like folders (unlike GCP).
Current scale: ~160-170 projects and ~10-12 organizations (exact numbers to be confirmed).
Budgeting occurs at the organization level, aligned with specific use cases or business units (BUs). Unlike major cloud providers (AWS, GCP, Azure), MongoDB Atlas does not require large upfront commitments; budgets are flexible and case-specific.


Onboarding to MongoDB Atlas

Process:
Leadership approves budgets internally, followed by BU Tech Risk and design review approvals.
Middleware engineering sets up an organization aligned with the use case.
Application teams use the MongoDB Atlas portal (UI or API) to create projects.
Teams deploy clusters and resources using Terraform, specifying configurations (e.g., three-shard cluster) based on resiliency and capacity requirements.


MongoDB Atlas is a fully managed database-as-a-service (DBaaS). Clusters are managed by MongoDB, but teams customize configurations via Infrastructure-as-Code (IaC) in Terraform, accessible through the MongoDB Atlas portal.


Access Control and Governance

Projects serve as the boundary for security and access control.
Access is restricted to GS-classified systems via network policies and private links. Connectivity from non-GS systems is blocked to ensure security.
API keys, managed by middleware engineering and rotated regularly via a secret manager, enforce programmatic access. Only authorized teams (e.g., via SDLC processes) can access these keys.
Governance ensures deployments align with approved configurations, validated through risk reviews and SDLC processes.
Concern: Lack of enforcement to prevent unauthorized cluster creation outside GS contracts. Network policies and contract alignment mitigate this, but stricter controls are needed.


Inventory and Monitoring

All assets (organizations, projects, clusters) should be registered in Inventory Central for tracking.
Application teams access MongoDB Atlas dashboards for project-specific resource usage and metrics. No centralized dashboard exists for cross-cluster monitoring; teams rely on MongoDB-provided tools.
Privacy considerations are integrated from the design phase, with collections registered as needed. No revisits are required for existing privacy designs.


Terraform and CFT Integration

Terraform is the primary tool for provisioning MongoDB Atlas resources, with CloudFormation Templates (CFTs) used for AWS dependencies (e.g., IAM roles, connectivity).
Recent discussions explore supporting MongoDB-native deployments via CFTs, but Terraform remains the standard.
API keys are project-specific, stored in a secret manager, and accessible to authorized teams. Access controls are enforced via GS-specific allowlists and API key rotation.








