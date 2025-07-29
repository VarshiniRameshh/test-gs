1. MongoDB Atlas Subdomain and Endpoint Risk
No Dedicated Subdomain. MongoDB Atlas currently does not provide a subdomain or private link endpoint that is specific to GS (Goldman Sachs). All access utilizes the common domain: cloud.mongodb.com.

Vendor Feedback. Subdomain endpoint support is on the MongoDB Atlas vendor’s roadmap but with no committed timeline.

Alternative (Snowflake Reference). Snowflake, by contrast, restricts GS accounts for access—showcasing better control compared to Atlas' one-endpoint-for-all model.

Mitigation Request. There is intent to follow up with the vendor for more granular endpoint controls, such as subdomain or private link-based access.

2. Data Exfiltration & DLP (Data Loss Prevention) Risks
Generalized Risk. The risk discussed is not Atlas/vendor-specific but general in nature; users might use open endpoints to upload or exfiltrate GS data to personal accounts or other platforms (e.g., AWS, Google, Dropbox).

Existing Controls:

There is a "site block" team (GS) that manages website access approvals.

For Atlas, the initial domain was unblocked following TechRisk design review and approval.

Whitelisting and blacklisting are managed (e.g., via Zscaler), but some platforms are broadly enabled for access—not necessarily for uploads.

Limitation. Not every blocked site or endpoint is controlled at a granular level; upload ability is not always tightly managed if a site is open for business use.

3. Personal Account Usage & Access Management
Personal Accounts. Risk increases as users might connect to Atlas using personal accounts/projects from their NDS (Network Desktop System) environments and upload sensitive documents/data.

Compensating Controls. There are mitigating controls requiring access approvals for projects/users, but these aren't foolproof against all scenarios, especially if GS data can be copied to permitted but unintended destinations.

Policy Perspective. Focused on a deny-by-default, approve-by-necessity approach, but enforcement may not cover all possible endpoints or user-initiated actions.

4. IAM User & Password Security
Current State. No easy way found to block IAM user/password certificate-based access in Atlas. This may need to be escalated to the vendor for possible tooling or workflow to restrict such access.

SSO and MFA. Control plane (UI) access is secured with SSO and MFA, but REST API access only has IP whitelist protection, not user/device whitelisting.

5. Network Connectivity & VPC Endpoint Controls
Data Plane Access. Enforced through private link (VPC endpoints only); internet-originated traffic is blocked by default.

Authentication:

On-prem: Connectivity brokered through Skype broker (Kerberos-linked) and managed by CFD/Sky teams.

Cloud: Direct role assumption by applications within GS-managed VPCs.

Cross-cloud (e.g., from GCP): Not allowed.

Connectivity Approval Flow:

On-prem: TechRisk reviews, then application teams deploy, after which connectivity is logged/approved (con-man).

Cloud: Merge requests are made, requiring TechRisk approval for CFD (Cloud Foundry Deploy) environments. No new non-CFD accounts are being deployed for Atlas.

Historical practice allowed legacy (non-CFD) deployments and some application teams managing their own endpoint services via Terraform.

Application teams, not central IT, manage endpoint services—all within the limitations of GS credentials and SDLC pipelines.

6. Inventory and Legacy Access
Inventory Gaps: A need was identified to obtain logs for existing connections, especially from historic non-CFD AWS accounts, to review the governance/gating (e.g., was TechRisk approval properly followed?).

Potential Gaps: Legacy connectivity may still exist, managed independently by teams, and not always fully governed or reviewed.

7. End User Controls & Process
Preventing Unauthorized Endpoint Creation:

Only allowed via SDLC pipelines accepting GS-managed credentials.

End users can't inject arbitrary credentials or account IDs to create endpoints from personal AWS environments.

Terraform Pipeline Security: Pipelines only recognize GS’s internal credentials/environment; external/personal AWS accounts are not supported.

8. Miscellaneous
Controlled Dev Environments. Ongoing efforts to restrict access to sensitive data only from tightly controlled developer environments—eventual goal: prevent any data exfiltration even for technically capable users.

Further Vendor Follow-Up. Planned for both Atlas subdomain features and methods to block certain IAM/password-based access patterns.

Action Item: Note to Rony: Compile inventory of legacy connectivity/access for audit and to clarify what was or wasn’t reviewed by TechRisk.





 
