1. Non-Prod to Prod Connectivity
Risk Highlighted: Non-prod system accounts must not have privileges or access to prod Atlas resources.

Action: Add this as an explicit requirement; currently not enforced across all data platforms.

Pending: Confirm if system accounts can be clearly classified as prod vs non-prod.

2. DLC Pipeline Requirements
Control: Privilege updates must have approval via pipeline.

Decision: Data owners must be involved in approvals.

Next Step: Raise this during Design Review and validate how each BU enforces it.

3. Atlas Access and Authentication
Observation: Access to MongoDB Atlas requires an AWS account.

Conflict: Architecture diagram marks AWS account as optional, while KMS constraints indicate it’s mandatory.

Action: Raise this as a question for clarification (check diagram + team thread).

4. Connectivity Review Rules
Approved Automatically If:

All targets are non-prod endpoints.

All sources are non-prod deployments, hosts, or collections.

Port range is within approved non-prod range.

To be Denied:

Any connection from prod to non-prod (due to higher risk of data exposure).

Add exception cases to be discussed with Kishore.

5. Write Restrictions by Classification
Query: Clarify if API key writes from lower-classification deployments to higher-classification collections are blocked.

Terminology Confirmation: Collection = Database Project in Atlas.

Action: Reconfirm with Kishore if classification is set at the collection or project level.

6. API Key Traceability
Must be used only via SDLC pipelines.

Stored securely using customer-managed KMS.

API keys must be rotated periodically.

7. Atlas Endpoints – Reference Location
No confluence page exists (unlike Snowflake or Singlestore).

Action: Follow up to obtain the definitive endpoint list for reference validation.

8. Pattern Page Coverage
Pattern page is primarily for production environments.

If something differs for non-prod, it should be called out explicitly.

Action: Clean up references where applicable and flag what's not covered on the current pattern page.

9. POC Phase 2: Terraform + CDK Integration
Goal: Manage both AWS and Atlas resources under same IaC (CDK/Terraform).

Observation: AWS resource provisioning done via CDK; Atlas via Terraform.

Action: Track progress and discuss dependency with cloud enablement team.

10. Access Restrictions
System accounts only for production – direct user access blocked.

For non-prod, direct user access is allowed.

Clarification: Ensure this is reflected only in prod-specific pattern pages.

📌 Outstanding Questions to Finalize Before Kishore Discussion
Can Atlas system accounts be explicitly classified as prod or non-prod?

Is the AWS account optional or mandatory for Atlas?

Are API keys mandatorily rotated and traceable in all environments?

Confirm if classification is set at project or collection level.

Validate if cross-region connectivity is blocked (especially in prod).

Is there any confluence or system to track Atlas endpoints like other platforms?

How is classification parity enforced in pipeline-level approvals (e.g., NDS, DP20, etc.)?
