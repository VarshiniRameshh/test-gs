Design Review
Design review focuses on the use case, data classification, access pattern, and integration security of the design. It is mostly a self-assessment (must-based on the app team) where the app team shares details which is documented in Confluence.

1. Network connectivity from a NDS to both prod/non-prod SP collections already opened up.
2. Network connectivity from Dev Workspaces to both prod/non-prod SP collections already opened up. Any cross environment connectivity still requires case by case approval.
3. Network connectivity is established between the R&D lab flows and non-prod DIDs and the VPC endpoints configured in AWS for MongoDB Atlas connectivity.

Ensure that the account will be managed in addition to review, additional controls not enforced by default will be requested by Tech Risk team to lower classified SP accounts. Majority of controls are incorporated by platform teams, please ask for a design review below controls are in place before approving connectivity:

Got it! Here's a **clean, one-line, point-wise version** — no headings, just direct statements, exactly as you requested:

---

* MongoDB Atlas resource hierarchy follows: Organization → Project → Cluster.
* Atlas Organizations (1–12) are managed by the Platform Engineering Team and mapped to business units.
* New business lines require creation of a new Atlas Organization.
* App teams need an AWS CFT account to onboard Atlas Projects.
* App teams use Terraform in SDLC pipelines to manage cluster provisioning and configuration.
* Configuration includes managing clusters, users, network connectivity, alerts, failover, and pause/resume.
* BYOK is mandatory for all Atlas clusters using customer-managed KMS keys.
* Annual re-keying is required and managed through SDLC.
* Atlas enforces TLS/SSL v1.2+ for encryption in transit.
* Client-side encryption is supported via MongoDB’s queryable encryption for sensitive data.
* Atlas captures project-level activity and logs for auditing and troubleshooting.
* There is no centralized logging system across all Atlas organizations.
* MongoDB dashboards provide metrics like CPU, memory, disk I/O, and slow queries.
* Platform team scans clusters every 2 hours and syncs to Inventory Central.
* Privacy datasets must be registered in ME Privacy Inventory at the collection level.
* App teams must follow multi-region deployment standards for region and node selection.
* M30 or higher clusters are required for production workloads.
* Shared clusters are allowed only for non-production use cases.
* IP whitelisting is supported at the organization level for the control plane.
* Private endpoints are supported for dedicated clusters via AWS PrivateLink.
* VPCEs must be provisioned via SkyTransit or Application SkyFoundry accounts.
* Atlas supports two user types: Atlas users (control plane) and database users (data plane).
* API keys for control plane are created by ME and scoped per project.
* Console access is provisioned through TMD requests.
* Control plane access is protected with GS SSO and MFA.
* Database users are configured using Terraform and can use AWS IAM users or Role ARNs.
* Authorization is enforced via Terraform in SDLC pipelines by app teams.
* Least privilege access model must be followed for all users and services.
* No non-prod DIDs should access production Atlas clusters without case-by-case Tech Risk approval.
* No DP20 or DP30 data should reside in non-prod environments unless explicitly reviewed and approved.

---

Let me know if you want this same list exported into a table or formatted for Confluence macros!

- Connectivity review and approval
Tech Risk reviews and approves the connectivity requests after multiple checks including validation of technical details (source/destination, ports, environments, data classification).
Firewall rules are edited to allow network traffic only after Connectivity Review by Tech Risk.
If a new system needs access to a particular Snowflake Account, design review approval is a pre-requisite for connectivity approval.

- No cross-classification access
- Only Part 403 is allowed for data transfer and for OSCP certificates checks allowed. Already enforced for CFT connectivity to SP.
- Cross-Environment Check
No cross-environment access, i.e., non-prod to prod, is allowed. Environment classification of source deployment should match the Snowflake Account environment, in order to get the Snowflake Account associated with destination IP addresses in the firewall allow rule (refer to ME Portal).
- Do you have a use case where you need access from non-prod system to sensitive DP20+ data testing purposes and exception is needed. Please see Non-prod to prod/sensitive data access section.

Non-prod to prod/sensitive data access
- For example no DP-10 (derivative) is not allowed.
- DP-10 reading from DP-30 not allowed unless justified and inline with design reviewed

Responses to the following questions should be captured in the Discussion section on the review ticket and a finding should be raised at the level of the finding should be to Medium depending on the responses to the below questions.

1. What is the name of the SF account and database(s), is the database named with suffix "_prod" to differentiate from real production?
2. What is the data and privacy classification of the data being accessed by the non-prod DID?
3. What is the purpose for the non-prod deployment accessing sensitive data?
4. Are there any open MySQL findings associated with the non-prod DID?
5. How many people have access to the non-prod DID?
6. What are the DP identifiers to ensure the database used for production?
7. What are the DP identifiers to ensure the database used for production?

Connectivity Review
- Network Connectivity to Control Plane
IP whitelisting is supported for MongoDB control plane at the organization level.
- Network Connectivity to Data Plane
MongoDB Atlas supports private endpoints on dedicated clusters.
Private endpoints allow a private connection between AWS and Atlas that doesn’t send information through a public network. You can use private endpoints to create a unidirectional connection to Atlas clusters from your virtual network.
Port Ranges used for MongoDB PrivateLink VPC are 1024-65535 (high ports).
This range has been approved by Tech Risk for Atlas.
MongoDB Atlas utilizes PrivateLink endpoints in a way that does not allow port to traffic type affinity. For a given Atlas project, each MongoDB cluster is assigned an incremental incrementing port number within the port ranges used-for-private-endpoint
https://www.mongodb.com/docs/atlas/security/private-endpoint/#port-ranges-used-for-private-endpoint
NDS access to Production MongoDB Atlas is not allowed.
- Authentication
2 types of users are supported in Atlas. Database users can access MongoDB databases while Atlas users can access the Atlas application (control plane).
For control plane (Atlas users), APIs are created by ME and access is provisioned to application teams. For UI access, GS SSO is enabled.
For data plane (database users), teams can set up database users to use AWS IAM Users or Role ARNs for authentication.
- Authorization
For control plane programmatic access, API keys are tied to specific projects and given different roles by ME. For console access, users create TMD requests.
For data plane, authorization is managed by application teams using terraform in SDLC pipelines The control plane API keys are used for this.

Application Team Responsibility
- Ensure PrivateLink in AWS and private endpoints in Atlas to ensure all traffic is private.
- Provision and manage VPC endpoints (VPCe) in CFT or SkyFoundry Account and allow outbound traffic.
- Create or update ConnMan rules to allow outbound traffic from application deployments in GSINet to MongoDB Atlas VPCes on ports 1024-65535.
- For non-prod Atlas, if needed, request Tech Risk for NDS Access to Atlas connectivity approval.

Platform Team Responsibility
- Enforce IP Access List for the Atlas Administration API to ensure that all calls to the Atlas administration API originate from an approved IP address space (GS network).
- Enforce IP Access List for the Atlas UI to ensure that Atlas UI for GS organizations is accessible only from an approved IP address space (GS network).
- Enable SSO + MFA for Atlas Console access.
- Provide app teams access to API keys used for programmatic access to the control plane.
