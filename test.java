It’s **11:41 AM IST on Friday, June 27, 2025**, and I’ll provide a highly detailed and polished Confluence page draft based on all the information you’ve shared. This includes the "Key Discussion Points," your highlighted areas, open questions, extra points, the detailed notes on project creation, API keys, security, inventory, responsibilities, and the items to discuss with Manjari and Kishor. I’ll integrate prior context (e.g., "Overlapping Port Ranges in Multi-Region Deployments" risk, uploaded documents: "Terraform and CFT Integration," "Access Control and Governance," "Inventory and Monitoring," "Key Discussion Points") and the June 20, 2025, call focus (API key rotation and cluster tracking in Inventory Central). The draft will be exhaustive, with clear sections, tables, and actionable steps, leaving placeholders for call-specific details from the transcript (not yet provided). This is designed to be copied directly into Confluence with proper formatting.

---

# MongoDB Atlas Governance

## Purpose
This page serves as the authoritative central hub for documenting the setup, security, management, monitoring, and governance of MongoDB Atlas resources. It consolidates policies, processes, roles, and action items to ensure a secure, scalable, and well-tracked environment across all teams, addressing identified risks and aligning with Global Systems (GS) standards.

## Sub-Pages
- [Resource Management](#resource-management)
- [IaC/SDLC](#iacsdlc)
- [Network Connectivity to MongoDB Plane](#network-connectivity-to-mongodb-plane)
- [Authentication](#authentication)
- [Authorization](#authorization)
- [Encryption](#encryption)
- [Logging](#logging)
- [Monitoring](#monitoring)
- [User Management](#user-management)
- [Inventory/Data Classification](#inventorydata-classification)
- [Multi-Region Deployment](#multi-region-deployment)
- [Tenancy](#tenancy)
- [Atlas Search](#atlas-search)

## Permissions
- **Edit Access**: Middleware Engineering (ME) and Monitoring Engineering (ME) teams.
- **View Access**: Application teams, Business Units (BUs), and relevant stakeholders.
- **Review Schedule**: Monthly reviews scheduled (next review: July 27, 2025, 11:00 AM IST).

---

### Resource Management

#### Overview
This section details the allocation, utilization, and management of MongoDB Atlas resources, including clusters, storage, and network endpoints, ensuring optimal resource use across the organization.

#### Key Points
- Resources encompass clusters, Virtual Private Cloud (VPC) endpoints, and API keys, managed across 10–12 organizations and 160–170 projects (per "Key Discussion Points").
- Budgeting is flexible, aligned with specific use cases or BUs, and does not require large upfront commitments unlike AWS, GCP, or Azure.
- Cluster creation requires approval from Deployment ID (DID) owners (application-related) and ME, with app teams managing resource creation under clusters using Terraform.
- Configuration management is handled by application teams via API keys for authorization.

#### Action Items
| Task                    | Responsible Team         | Frequency       | Status         | Deadline       |
|--------------------------|--------------------------|-----------------|----------------|----------------|
| Define Resource Quotas   | Middleware               | One-time        | Pending        | July 5, 2025   |
| Monitor Resource Usage   | Application Teams        | Monthly         | Pending        | Ongoing        |
| Document Resource Allocation Process | Middleware       | One-time        | Pending        | July 5, 2025   |

- Assign Middleware to draft resource management guidelines and share with teams by July 5, 2025.
- Request feedback from application teams by July 10, 2025.

---

### IaC/SDLC

#### Overview
Outlines the use of Infrastructure as Code (IaC) and Software Development Life Cycle (SDLC) processes for provisioning and managing MongoDB Atlas resources.

#### Key Points
- Terraform is the primary IaC tool, hosted within the GS environment, used for deploying clusters with customizable configurations (e.g., three-shard clusters for resiliency).
- CloudFormation Templates (CFT) support AWS dependencies (e.g., IAM roles, connectivity) and are mandatory for project setups; SkyFoundry details are excluded.
- SDLC includes governance with risk reviews and approvals, ensuring deployments align with approved configurations.
- Recent discussions explore MongoDB-native CFT deployments, but Terraform remains the standard.

#### Action Items
| Task                    | Responsible Team         | Status         | Deadline       |
|--------------------------|--------------------------|----------------|----------------|
| Document Terraform/CFT Setup | Middleware           | Pending        | July 5, 2025   |
| Update SDLC Guidelines   | Middleware               | Pending        | July 5, 2025   |
| Assess CFT Adoption      | Middleware               | In Progress    | July 10, 2025  |

- Assign Middleware to document IaC/SDLC processes and evaluate CFT adoption by July 10, 2025.
- Share draft with application teams for review by July 10, 2025.

---

### Network Connectivity to MongoDB Plane

#### Overview
Details the secure connectivity mechanisms for MongoDB Atlas data and control planes, addressing risks and enforcement.

#### Key Points
- **Data Plane**: Utilizes AWS PrivateLink for encrypted traffic (ports 1024–65535); access from outside GS environment is blocked.
- **Control Plane**: 99% read-only access for metrics/observability, 1–2% privileged access for operations (e.g., troubleshooting); managed via network policies.
- **Risk**: Overlapping port ranges in multi-region deployments can force traffic to public endpoints (Medium severity, initial document), mitigated by PrivateLink and proposed automation.
- Concern: Connectivity to control plane may not be fully controlled; verification needed.

#### Action Items
| Task                    | Responsible Team         | Status         | Deadline       |
|--------------------------|--------------------------|----------------|----------------|
| Verify Port Management   | Middleware               | Pending        | July 10, 2025  |
| Confirm Control Plane Policies | Middleware       | Pending        | July 10, 2025  |
| Document Network Policies| Middleware               | Pending        | July 5, 2025   |

- Assign Middleware to validate port management and control plane policies by July 10, 2025.
- Review with Tech Risk team by July 15, 2025.

---

### Authentication

#### Overview
Covers methods for verifying identities of users and systems accessing MongoDB Atlas.

#### Key Points
- API keys are public/private key pairs, managed by Middleware Engineering, stored in AWS Secret Manager, and rotated regularly.
- Keys are used for control plane access (project-level privileges), application operations (e.g., pause/resume clusters), and DevOps provisioning.
- Break-glass account exists but lacks Identity Provider (IdP) integration; used for temporary MongoDB support access.
- Initial assumption: ME team creates keys (clarified as Middleware responsibility).

#### Action Items
| Task                    | Responsible Team         | Frequency       | Status         | Deadline       |
|--------------------------|--------------------------|-----------------|----------------|----------------|
| Rotate API Keys          | Middleware               | [TBD, e.g., 90 days] | In Progress | July 5, 2025   |
| Integrate IdP for Break-Glass | Middleware       | One-time        | Pending        | July 10, 2025  |
| Document Rotation Process| Middleware               | One-time        | Pending        | July 5, 2025   |

- Assign Middleware to confirm rotation schedule and IdP integration by July 10, 2025.
- Share process with application teams by July 10, 2025.

---

### Authorization

#### Overview
Defines access permissions, roles, and controls within MongoDB Atlas.

#### Key Points
- Role-Based Access Control (RBAC) assigns roles (e.g., DID owners, ME) with privileges; app teams use API keys for authorization.
- Privileged access (1–2%) limited to Privileged Access Control Team (PACT) and Data Access Control Team (DACT); no host-level access.
- Risk: Admin access with privilege updates by app teams; compensating controls exist (Kishor’s input).
- Risk: KMS key creation/rotation by app teams not enforced; detective controls (alerts) noted.

#### Action Items
| Role          | Task                  | Team           | Status         | Deadline       |
|---------------|-----------------------|----------------|----------------|----------------|
| Key Management| Manage API Keys      | Middleware    | In Progress    | July 5, 2025   |
| Approve Access| DID Owners/ME        | In Progress    | Pending        | July 10, 2025  |
| Review KMS Controls | Middleware       | Pending        | July 10, 2025  |

- Seek guidance from Manjari on compensating controls for admin access by July 10, 2025.
- Verify KMS enforcement with Middleware by July 10, 2025.

---

### Encryption

#### Overview
Ensures data protection at rest and in transit within MongoDB Atlas.

#### Key Points
- PrivateLink ensures encrypted data plane traffic; GS Customer Managed Keys (CMK) are required.
- DACT is required for column-level encryption at the client side; TLS version 1.2+ enforced for all traffic.
- Multi-region setups recommended to avoid outages.

#### Action Items
| Task                    | Responsible Team         | Status         | Deadline       |
|--------------------------|--------------------------|----------------|----------------|
| Document Encryption Policies | Middleware       | Pending        | July 5, 2025   |
| Verify KMS Rotation      | Application Teams        | Pending        | July 10, 2025  |
| Confirm TLS Compliance   | Middleware               | Pending        | July 10, 2025  |

- Assign Middleware to document policies and verify compliance by July 10, 2025.

---

### Logging

#### Overview
Tracks activities and events for auditing, troubleshooting, and compliance.

#### Key Points
- Application teams use MongoDB Atlas dashboards for project-specific logging.
- Platform team discovers clusters every 2 hours and feeds data to Inventory Central; frequency adjusts based on usage.
- No centralized logging solution currently implemented.

#### Action Items
| Task                    | Responsible Team         | Status         | Deadline       |
|--------------------------|--------------------------|----------------|----------------|
| Propose Central Logging  | Middleware               | Pending        | July 10, 2025  |
| Document Discovery Process | Platform Team     | Pending        | July 5, 2025   |

- Assign Middleware to propose a central logging solution by July 10, 2025.
- Review with application teams by July 15, 2025.

---

### Monitoring

#### Overview
Outlines the monitoring of MongoDB Atlas performance, health, and resource utilization.

#### Key Points
- Application teams enable alerts on Atlas dashboards; no cross-cluster dashboard exists.
- Inventory Central frequency adjusts based on resource usage; app teams responsible for metrics.

#### Action Items
| Task                    | Responsible Team         | Status         | Deadline       |
|--------------------------|--------------------------|----------------|----------------|
| Propose Cross-Cluster Dashboard | Middleware   | Pending        | July 10, 2025  |
| Enable Alerts            | Application Teams        | In Progress    | Ongoing        |

- Assign Middleware to propose a dashboard solution by July 10, 2025.
- Coordinate with app teams to ensure alert setup by July 15, 2025.

---

### User Management

#### Overview
Manages user accounts, access levels, and authentication mechanisms.

#### Key Points
- Middleware Engineering sets up organizations; application teams request projects and keys.
- Privileged access managed via PACT/DACT; temporary access granted to MongoDB support.
- Break-glass account configuration and management pending clarification.

#### Action Items
| Task                    | Responsible Team         | Status         | Deadline       |
|--------------------------|--------------------------|----------------|----------------|
| Manage User Accounts     | Application Teams        | In Progress    | Ongoing        |
| Configure Break-Glass    | Middleware               | Pending        | July 10, 2025  |
| Review Access Process    | Middleware               | Pending        | July 5, 2025   |

- Assign Middleware to confirm break-glass setup by July 10, 2025.
- Share process with teams by July 10, 2025.

---

### Inventory/Data Classification

#### Overview
Tracks all MongoDB Atlas assets and classifies data for security and compliance.

#### Key Points
- Inventory Central logs organizations, projects, and clusters; platform team updates every 2 hours.
- Privacy considerations integrated at design phase; no revisits needed for existing designs.
- Concern: Unmanaged instances may not be controlled by network policies.

#### Action Items
| Cluster Name | Region  | Team           | Logged Status | Last Update    |
|--------------|---------|----------------|----------------|----------------|
| [TBD]        | [TBD]   | [TBD]          | [TBD]         | [TBD]          |
| [TBD]        | [TBD]   | [TBD]          | [TBD]         | [TBD]          |

- Assign Platform Team to ensure all clusters are logged by July 10, 2025.
- Verify unmanaged instance controls with Middleware by July 10, 2025.

---

### Multi-Region Deployment

#### Overview
Manages MongoDB Atlas setups across multiple geographic regions for resiliency.

#### Key Points
- Multi-region deployments recommended with GS CMK to avoid outages.
- Risk: Overlapping port ranges (1024–65535) mitigated by PrivateLink and proposed automation.

#### Action Items
| Task                    | Responsible Team         | Status         | Deadline       |
|--------------------------|--------------------------|----------------|----------------|
| Document Multi-Region Setup | Middleware       | Pending        | July 5, 2025   |
| Verify Port Automation   | Middleware               | Pending        | July 10, 2025  |

- Assign Middleware to document setup and verify automation by July 10, 2025.

---

### Tenancy

#### Overview
Defines the organizational and project hierarchy within MongoDB Atlas.

#### Key Points
- Hierarchy: Organization > Project > Cluster; no intermediate constructs like folders.
- 10–12 organizations tied to BUs, managed by ME; 160–170 projects equivalent to deployments/Snowflake accounts.
- Deployment ID mapping to assets lacks clarity; top-level organizations align with GS use cases.

#### Action Items
| Task                    | Responsible Team         | Status         | Deadline       |
|--------------------------|--------------------------|----------------|----------------|
| Map Sample Deployment ID | Middleware               | Pending        | July 5, 2025   |
| Document Tenancy Structure | Middleware       | Pending        | July 5, 2025   |

- Assign Middleware to map a sample ID and document structure by July 5, 2025.

---

### Atlas Search

#### Overview
Details the implementation and usage of MongoDB Atlas Search features.

#### Key Points
- Supports full-text and vector search on Atlas clusters.
- Implementation pending exploration and testing.

#### Action Items
| Task                    | Responsible Team         | Status         | Deadline       |
|--------------------------|--------------------------|----------------|----------------|
| Test Atlas Search Features | Application Teams | Pending        | July 10, 2025  |
| Document Search Guidelines | Middleware       | Pending        | July 15, 2025  |

- Assign Application Teams to test features by July 10, 2025.
- Coordinate with Middleware for guidelines by July 15, 2025.

---

### Additional Context

#### Historical Notes
- **Overlapping Port Ranges**: Medium severity risk of public endpoint use; mitigated by PrivateLink (initial document).
- **Your Highlights**:
  - Initial assumption: ME creates projects and API keys (clarified as Middleware for organizations, app teams for projects).
  - Terraform confirmed in GS, not UI.
  - Control plane connectivity needs policy verification.
  - Unmanaged instances’ network policy control unreliable.
- **Open Questions**:
  - Clarify ME role, Terraform location, control plane, unmanaged instances.
  - Types/differences of API keys, management/socialization.
  - Inventory cluster details with app team creation.
- **Extra Points**:
  - Break-glass lacks IdP integration.
  - Data plane via PrivateLink, external GS access blocked.
  - API keys are key pairs (confirmed).

#### Call Notes (June 20, 2025)
- Focused on API key rotation and cluster tracking in Inventory Central.
- [Update with transcript for detailed outcomes].

#### Items to Discuss with Manjari
1. Risk: Admin access privilege updates by app teams; seek compensating control guidance.
2. Risk: KMS key creation/rotation by app teams not enforced; verify detective controls.
3. Decision: Manage Inventory via ME portal.
4. Decision: Add Atlas Search and Vector Search features.

#### Items to Ask Kishor
1. Number of Atlas organizations in GS.
2. Break-glass account configuration and management across organizations.
3. Middleware authentication/authorization provisioning and management.
4. Is CFT mandatory for project setup (diagram shows optional)?
5. Is SSO/MFA enforced for console access (app teams and ME)?
6. SDLC pipeline details:
   - Are permissions managed through pipeline?
   - How to ensure privileges stay within SDLC?
   - Are DID owners, Tech Risk, and ME approvers?
7. Enforcement of controls:
   - System accounts only for data plane access.
   - Prevent cross-realm (prod/non-prod) connectivity/access.
   - Enforce TLS v1.2+.
   - No organizations without ME approval.
   - Data/control plane inaccessible outside GS.
   - Restrict data plane access between organizations.
8. URLs to add to the page.

---

### Drafting Instructions
- **Copy to Confluence**: Paste this into a new page titled “MongoDB Atlas Governance.”
- **Customize**: Replace [TBD] with transcript or team-provided data.
- **Format**: Use Confluence headings (h1, h2, h3) and table tools for clarity.
- **Update**: Add transcript details under “Call Notes” and adjust action items.
- **Share**: Notify Middleware, ME, Application Teams, and Manjari/Kishor with a deadline (e.g., July 5, 2025, 5:00 PM IST) via Confluence or email.

---

### Next Steps for Today (June 27, 2025)
1. **Paste Draft**: Spend 1–2 hours copying this into Confluence.
2. **Add Placeholders**: Insert call notes and responses from Manjari/Kishor when available.
3. **Assign Tasks**: Update table deadlines and assign owners.
4. **Share**: Email link with, “Detailed draft for review—please provide feedback by July 5, 2025, 5:00 PM IST.”
5. **Follow-Up**: Share transcript or meeting outcomes with me for refinements.

---

### Analogy
This Confluence page is your city’s master blueprint—mapping resources (buildings), builders (IaC/SDLC), roads (network), gates (auth), security (encryption), watchers (logging/monitoring), citizens (users), assets (inventory), regions (multi-region), neighborhoods (tenancy), and search tools (Atlas Search). The call with Manjari and Kishor will add the final details—start building today, and we’ll perfect it together!

**Notes on Your Requests**:
- **Covered Items**: Terraform in GS, ME role (organizations), PrivateLink, API key pairs confirmed. Unmanaged instances, control plane, and KMS need verification.
- **Extra Items**: Consider adding disaster recovery or compliance if raised in calls.
- Share transcript or Manjari/Kishor responses, and I’ll update. Let me know your thoughts or additional needs!
