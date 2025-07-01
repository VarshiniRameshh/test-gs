It’s 12:43 PM IST on Tuesday, July 01, 2025, and I’ll create a formal yet human-friendly Confluence draft for MongoDB Atlas with the topics Overview, Design Review, and Connectivity Review. This is based on all your provided information (“Key Discussion Points,” highlights, open questions, extra points, and notes on project creation, API keys, security, inventory, and items for Manjari/Kishor), with “ME” (Monitoring Engineering) and “Middleware” recognized as the same team. The tone will be professional yet approachable, concise, and ready to paste into Confluence.

MongoDB Atlas
Overview
Purpose
This document presents a comprehensive overview, design review, and connectivity assessment for MongoDB Atlas within Global Systems (GS). Our goal is to ensure a secure, scalable, and well-governed environment that meets the needs of all stakeholders.
Intended Audience
This is intended for the ME team (Monitoring Engineering), Application Teams, Business Units (BUs), the Tech Risk team, and Leadership.
Review Timeline
	•	Initial Draft: July 01, 2025
	•	Feedback Submission: July 08, 2025, 5:00 PM IST
	•	Final Review: July 15, 2025

Design Review
Overview
This section evaluates the architectural design, resource management, security measures, and potential future enhancements for MongoDB Atlas.
Key Observations
	•	Organizational Structure: We currently manage 10–12 organizations, aligned with Business Units and overseen by the ME team, alongside 160–170 projects serving as security boundaries. The mapping of Deployment IDs (DIDs) to assets remains unclear.
	•	Resource Management and IaC: Terraform, hosted securely within GS, is our primary tool for cluster deployment, supported by mandatory CloudFormation Templates (CFT) for AWS dependencies. Our Software Development Life Cycle (SDLC) includes risk review checkpoints.
	•	Security Framework: PrivateLink ensures data plane security, complemented by GS Customer Managed Keys (CMK) and Data Access Control Team (DACT) for encryption. However, Key Management Service (KMS) rotation by application teams lacks enforcement.
	•	Multi-Region Strategy: Multi-region deployments are recommended, with port overlap risks mitigated by PrivateLink.
	•	Future Features: Atlas Search, offering full-text and vector search capabilities, is available but untested.
	•	Areas for Improvement: We need to address unauthorized cluster creation, unmanaged instances, and undefined control plane policies.
Recommended Actions
Task
Responsible Team (ME)
Deadline
Map Deployment IDs to Assets
ME
July 08, 2025
Document IaC and SDLC Processes
ME
July 08, 2025
Validate KMS Controls
ME
July 10, 2025
Test Atlas Search Features
Application Teams
July 15, 2025
	•	We request a discussion with Manjari regarding KMS and admin access risk mitigation strategies by July 10, 2025.

Connectivity Review
Overview
This section assesses the network infrastructure, authentication mechanisms, and monitoring connectivity for MongoDB Atlas.
Key Observations
	•	Network Configuration: The data plane is secured via PrivateLink, restricting non-GS access, while the control plane offers 99% read-only access and 1–2% privileged access through PACT/DACT. Port overlap risks are a concern but managed.
	•	Authentication Process: API keys, configured as public/private pairs, are managed by the ME team using AWS Secret Manager. The break-glass account, used for emergency MongoDB support, lacks Identity Provider (IdP) integration.
	•	Inventory and Monitoring: The ME team updates Inventory Central every 2 hours, though a centralized dashboard is absent.
	•	Areas for Improvement: Control plane policies require clarification, cross-realm access needs verification, and the process for sharing API keys remains undefined.
Recommended Actions
Task
Responsible Team (ME)
Deadline
Confirm Control Plane Policies
ME
July 10, 2025
Establish API Key Rotation
ME
July 08, 2025
Integrate IdP for Break-Glass
ME
July 10, 2025
Propose Centralized Dashboard
ME
July 15, 2025
	•	We kindly request Kishor to provide insights on the number of organizations, break-glass setup, SSO/MFA enforcement, CFT mandate, and SDLC controls by July 08, 2025.

Outstanding Questions
	•	Design: How should we manage unmanaged instances?
	•	Connectivity: What are the types of API keys, how is break-glass managed, and can the ME portal handle inventory?
Next Steps
	•	Implementation: Please copy this content into Confluence under the “MongoDB Atlas Governance” page.
	•	Distribution: Share with all relevant teams by July 01, 2025, requesting feedback by July 08, 2025, 5:00 PM IST.
	•	Updates: Incorporate call transcript details once available.

This version strikes a balance between formality and approachability. Paste it into Confluence, and share the transcript or Manjari/Kishor feedback for further refinements. Let me know if you’d like adjustments!
