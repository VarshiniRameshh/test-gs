Below is a concise and professional Minutes of Meeting (MoM) based on the call transcript from June 23, 2025, held at approximately 00:10–54:13 IST. The MoM reflects the key discussions, decisions, and action items related to MongoDB Atlas project management, API keys, security, inventory, and operational processes. The date and time are adjusted to 11:11 AM IST on Tuesday, June 24, 2025, as the current context.

Minutes of Meeting (MoM)
Date & Time: June 23, 2025, 00:10–54:13 IST Prepared On: June 24, 2025, 11:11 AM IST Attendees: [Assumed participants from context—please add names if known] Subject: MongoDB Atlas Project Management, Security, and Operational Review

1. Project Creation and Management
	•	Discussion:
	◦	Projects are created under organizations managed by the platform team, with application teams requesting and provisioning projects via UI/API.
	◦	Deployment owners (linked to applications) approve tasks. Specific business functions drive project needs.
	◦	Oversight is provided by engineering teams.
	•	Decision: Application teams lead project creation with platform team support; engineering ensures oversight.
	•	Action Items:
	◦	[Responsible Party] to confirm middleware vs. application team roles in project provisioning.
2. API Keys: Purpose and Management
	•	Discussion:
	◦	API keys (public/private pairs) are used for application operations (e.g., pause/resume clusters) and DevOps provisioning.
	◦	Middleware engineering manages keys, storing them in a secret manager with regular rotation. Application teams request keys via IAM roles.
	◦	Testing privileges are defined per project; automation (e.g., weekend cluster pauses) is supported but requires configuration.
	•	Decision: Middleware oversees key lifecycle; automation is feasible with setup.
	•	Action Items:
	◦	[Middleware Team] to provide a plan for automating cluster operations (e.g., 8 PM Friday pause, Monday resume).
	◦	[Responsible Party] to clarify key request process for application teams.
3. Security and Access Control
	•	Discussion:
	◦	Break-Glass Account: Not yet integrated with IdP, used for emergencies (e.g., MongoDB support access for 24 hours).
	◦	PrivateLink: Enforced via contracts with application teams documenting usage; no internet access intended, but controls are immature.
	◦	Access: Data plane is restricted; control plane is managed by middleware with 99% read-only access and 1-2% privileged access for operations (e.g., troubleshooting).
	◦	Application teams must grant temporary infra access to MongoDB support.
	•	Decision: Security gaps (break-glass, PrivateLink) need proactive fixes; access is mostly read-only.
	•	Action Items:
	◦	[Security Team] to integrate break-glass with IdP and verify PrivateLink enforcement.
	◦	[Deployment Owners] to ensure timely revocation of MongoDB support access.
4. Inventory and Discovery
	•	Discussion:
	◦	Platform team discovers clusters every few hours (e.g., 2-hour intervals) and feeds data to Inventory Central (IC). Application teams must log their clusters.
	◦	IC frequency may adjust based on resource usage; no central dashboard exists.
	•	Decision: Discovery is regular, but application teams share inventory responsibility.
	•	Action Items:
	◦	[Platform Team] to confirm IC update frequency and app team compliance.
	◦	[Responsible Party] to explore a central dashboard feasibility.
5. Automation and Operations
	•	Discussion:
	◦	Operational activities (e.g., log access) rely on API keys. Automation for cluster management (e.g., pause/resume) is possible via Terraform but needs scheduling.
	•	Decision: Automation is supported with configuration.
	•	Action Items:
	◦	[Middleware Team] to define an automation schedule for operational tasks.
6. Responsibilities and Processes
	•	Discussion:
	◦	Application teams request projects/keys, deployment owners approve, and middleware manages infrastructure.
	◦	SDLC is shifting to proactive controls (e.g., Atlas Search design reviews); a separate page is proposed.
	◦	Customer Managed Keys (CMK) are required, with multi-region setups recommended to avoid outages.
	◦	Application teams enable alerts on their metrics.
	•	Decision: Processes are evolving; proactive governance and deadlines need defining.
	•	Action Items:
	◦	[SDLC Team] to set deadlines for proactive controls and CMK enforcement.
	◦	[Application Teams] to configure alerts and document CMK usage.
7. Additional Notes
	•	CMK Dependency: Application teams must create multi-region CMKs to ensure resilience.
	•	Public Road Strategy: Discussions ongoing; streamlining needed for developer experience.
	•	Next Steps: Capture action items and collaborate on governance improvements.

Overall Decisions
	•	Application teams lead with platform/middleware support; security and inventory processes need enhancement.
	•	Proactive controls and automation are prioritized for future implementation.
Pending Action Items
	•	Confirm middleware vs. app team roles in provisioning (Owner).
	•	Plan automation for cluster operations (Middleware Team).
	•	Integrate break-glass with IdP and verify PrivateLink (Security Team).
	•	Ensure IC compliance and explore dashboard (Platform Team).
	•	Set deadlines for proactive controls and CMK (SDLC Team).
	•	Configure alerts and document CMK usage (Application Teams).
Next Steps
	•	Follow up on action items by [insert date, e.g., July 1, 2025].
	•	Share MoM with all attendees for review and feedback.

Understanding with Analogies
	•	Projects: Like builders (apps) requesting plots from city planners (platform), with homeowners (deployment owners) approving.
	•	API Keys: Locksmith (middleware) makes keys, stored in a safe, used by apps to control houses.
	•	Security: Break-glass is a spare key needing a security system; PrivateLink is a private road with immature gates.
	•	Inventory: Detectives (platform) log houses in a directory (IC), but builders must report.
	•	Automation: A smart timer for house lights, needing setup.
	•	Process: Moving from reactive building inspections to planned reviews.
This MoM is ready for your team. Let me know if you’d like to adjust or add details!
