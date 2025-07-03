Design Review
Design review focuses on the use case, data classification, access pattern, and integration security of the design. It is mostly a self-assessment (must-based on the app team) where the app team shares details which is documented in Confluence.

1. Network connectivity from a NDS to both prod/non-prod SP collections already opened up.
2. Network connectivity from Dev Workspaces to both prod/non-prod SP collections already opened up. Any cross environment connectivity still requires case by case approval.
3. Network connectivity is established between the R&D lab flows and non-prod DIDs and the VPC endpoints configured in AWS for MongoDB Atlas connectivity.

Ensure that the account will be managed in addition to review, additional controls not enforced by default will be requested by Tech Risk team to lower classified SP accounts. Majority of controls are incorporated by platform teams, please ask for a design review below controls are in place before approving connectivity:

- Resource hierarchy in Atlas is organizations, projects, clusters.
- Application teams require an AWS CFT account to onboard Atlas projects. MongoDB Atlas resources are created under different organizations (1-12) managed by platform engineering team. Organizations are linked to business units and are managed by platform team. If the use case is for a new business line, a new Atlas organization will be set up.
- App teams manage resource creation and configuration management under projects using SDLC pipelines leveraging Terraform (IaC). This includes managing clusters, database users, managing network connectivity, managing alerts, failover orchestration, pause/resume clusters.
- Data Encryption at Rest: MongoDB Atlas supports GS CMK / BYOK for MongoDB Clusters. BYOK is mandatory for all atlas clusters. Application teams need to manage this via SDLC. Provision KMS key for encryption and leverage re-keying at least annually. Encryption in Transit: MongoDB enforces TLS/SSL version 1.2+ automatically. Client Side Encryption: Sensitive data could be encrypted client side using MongoDB's queryable encryption method before being sent to MongoDB as defined in the Extended Data Classification and Protection Model.
- MongoDB Atlas captures activity and events to help with auditing and troubleshooting. App teams can use MongoDB Atlas dashboards for their own project-level logs. No centralized logging system is currently in place for all projects.
- Application teams can leverage MongoDB Atlas dashboards for real-time insights like CPU usage, memory, disk I/O, and slow queries. Monitoring data is project-specific and not aggregated centrally at the organization level.
- The Platform team runs scans every 2 hours to detect clusters and send that data to inventory Central. For datasets containing privacy data, they should be registered in the ME Privacy inventory as well. See MERP Wiki instructions here. Note that registrations are at the Collection level in MongoDB.
- Application Team to ensure that AWS regions, nodes and their deployment order adheres to the requirements for multi-region deployments.
- Atlas recommends M30 or above for production use cases while shared clusters could be used non-production use cases.
- IP whitelisting is supported for MongoDB control plane at the organization level.
- MongoDB Atlas supports private endpoints on dedicated clusters. Private endpoints allow a private connection between AWS and Atlas.
- 2 types of users are supported in Atlas. Database users can access MongoDB databases while Atlas users can access the Atlas application (control plane). For control plane (Atlas users), APIs are created by ME and access is provisioned to application teams. For UI access, GS SSO is enabled. For data plane (database users), teams can set up database users to use AWS IAM Users or Role ARNs for authentication.
- For control plane programmatic access, API keys are tied to specific projects and given different roles by ME. For console access, users create TMD requests. For data plane, authorization is managed by application teams using terraform in SDLC pipelines.

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
