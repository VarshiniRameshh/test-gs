It’s 12:37 PM IST on Tuesday, July 01, 2025, and thanks for clarifying that “ME” (Monitoring Engineering) and “Middleware” are the same team. I’ll update the Confluence draft for MongoDB Atlas accordingly, replacing all instances of “Middleware” with “ME” to reflect this. The topics remain Overview, Design Review, and Connectivity Review, based on all provided information (“Key Discussion Points,” your highlights, open questions, extra points, and notes on project creation, API keys, security, inventory, and items for Manjari/Kishor). The draft is crisp, actionable, and ready to paste into Confluence.

MongoDB Atlas
Overview
Purpose
This document provides an overview, design review, and connectivity review for MongoDB Atlas within Global Systems (GS) to ensure a secure, scalable, and governed environment.
Audience
ME (Middleware Engineering), Application Teams, BUs, Tech Risk, Leadership.
Review Schedule
	•	Draft: July 01, 2025
	•	Feedback: July 08, 2025, 5:00 PM IST
	•	Final: July 15, 2025

Design Review
Overview
Evaluates architecture, resource management, security, and future features.
Key Points
	•	Structure: 10–12 organizations (BU-managed by ME), 160–170 projects as security boundaries; DID mapping unclear.
	•	Resources/IaC: Terraform (GS-hosted) and mandatory CFT for clusters; SDLC with risk reviews.
	•	Security: PrivateLink, GS CMK, DACT for encryption; KMS rotation by app teams unenforced.
	•	Multi-Region: Recommended with port overlap risks mitigated by PrivateLink.
	•	Atlas Search: Full-text/vector search untested.
	•	Gaps: Unauthorized clusters, unmanaged instances, control plane policy gaps.
Actions
Task
Team
Deadline
Map DID to Assets
ME
July 08, 2025
Document IaC/SDLC
ME
July 08, 2025
Verify KMS Controls
ME
July 10, 2025
Test Atlas Search
App Teams
July 15, 2025
	•	Discuss KMS and admin access risks with Manjari by July 10, 2025.

Connectivity Review
Overview
Assesses network, authentication, and monitoring connectivity.
Key Points
	•	Network: PrivateLink secures data plane; control plane 99% read-only, 1–2% privileged (PACT/DACT); port overlaps a risk.
	•	Authentication: API keys (pairs) managed by ME via AWS Secret Manager; break-glass lacks IdP.
	•	Inventory/Monitoring: ME updates Inventory Central every 2 hours; no central dashboard.
	•	Gaps: Unclear control plane policies, cross-realm access, key socialization.
Actions
Task
Team
Deadline
Confirm Policies
ME
July 10, 2025
Define Key Rotation
ME
July 08, 2025
Integrate IdP
ME
July 10, 2025
Propose Dashboard
ME
July 15, 2025
	•	Ask Kishor: Org count, break-glass setup, SSO/MFA, CFT mandate, SDLC controls by July 08, 2025.

Open Questions
	•	Design: Unmanaged instance controls.
	•	Connectivity: API key types, break-glass management, inventory via ME portal.
Next Steps
	•	Paste: Copy into Confluence.
	•	Share: Notify teams by July 01, 2025, for feedback by July 08, 2025.
	•	Update: Add transcript details post-call.

This reflects ME as the unified team. Paste this into Confluence, and share the transcript or Manjari/Kishor inputs for further tweaks. Let me know if you need adjustments!

