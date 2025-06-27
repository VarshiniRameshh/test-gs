It’s **11:25 AM IST on Friday, June 27, 2025**, and I’ll create a comprehensive Confluence page draft based on the updated content you provided, including the "Key Discussion Points," your highlighted areas, open questions, extra points, and the detailed notes on project creation, API keys, security, inventory, responsibilities, and items to discuss with Manjari and Kishor. This consolidates all prior context (e.g., overlapping port ranges, uploaded documents) and incorporates the latest call-related insights. The draft is structured, neat, and ready to copy into Confluence, with placeholders for any missing call-specific details.

---

# MongoDB Atlas Governance

## Purpose
This page serves as the central hub for documenting the setup, security, management, and monitoring of MongoDB Atlas resources, ensuring alignment across teams and addressing identified risks and processes.

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
- **Edit Access**: Middleware and ME teams.
- **View Access**: Application teams.
- **Review Schedule**: Monthly (next review: July 27, 2025).

---

### Resource Management

#### Overview
Manages allocation and utilization of MongoDB Atlas resources (e.g., clusters, storage).

#### Key Points
- Resources include clusters, VPC endpoints, and API keys across 10–12 organizations and 160–170 projects.
- Budgeting is flexible, aligned with BUs, no upfront commitments (per "Key Discussion Points").
- Cluster creation requires DID owner and ME approval; app teams manage resources via Terraform.

#### Action Items
| Task            | Responsible Team | Status       |
|-----------------|------------------|--------------|
| Define Quotas   | Middleware       | Pending     |
| Monitor Usage   | Application Teams| Pending     |

- Assign middleware to set guidelines by July 5, 2025.

---

### IaC/SDLC

#### Overview
Outlines Infrastructure as Code (IaC) and Software Development Life Cycle (SDLC) processes.

#### Key Points
- Terraform is primary IaC tool in GS for clusters; CFT supports AWS dependencies (per "Terraform and CFT Integration").
- SDLC includes risk reviews; app teams use Terraform with API keys for configurations.
- CFT accounts are mandatory; SkyFoundry details removed.

#### Action Items
- Document Terraform/CFT setup and SDLC steps.
- Update by July 5, 2025.

---

### Network Connectivity to MongoDB Plane

#### Overview
Details secure connectivity to MongoDB data and control planes.

#### Key Points
- Data plane uses PrivateLink (ports 1024–65535); non-GS access blocked.
- Control plane: 99% read-only, 1–2% privileged access for ops/troubleshooting.
- Risk: Overlapping ports in multi-region setups mitigated by PrivateLink (initial document).

#### Action Items
- Verify port management and policy enforcement.
- Assign middleware by July 10, 2025.

---

### Authentication

#### Overview
Covers methods for verifying user and system identities.

#### Key Points
- API keys (public/private pairs) managed by middleware, rotated via AWS secret manager.
- Break-glass account lacks IdP integration; used for temporary MongoDB support access.
- Keys support app ops (e.g., pause/resume clusters) and DevOps.

#### Action Items
| Task            | Responsible Team | Status       |
|-----------------|------------------|--------------|
| Rotate API Keys | Middleware       | In Progress |
| Integrate IdP   | Middleware       | Pending     |

- Confirm rotation schedule by July 5, 2025.

---

### Authorization

#### Overview
Defines access permissions and roles within MongoDB Atlas.

#### Key Points
- RBAC roles (e.g., DID owners, ME) control access; app teams use API keys.
- Privileged access limited to PACT/DACT; no host-level access.
- Risk: Admin access with privilege updates by app teams; compensating controls exist.

#### Action Items
| Role          | Task                  | Team           |
|---------------|-----------------------|----------------|
| Key Management| Manage API Keys      | Middleware    |
| Approve Access| DID Owners/ME        | In Progress   |

- Seek guidance on compensating controls by July 10, 2025.

---

### Encryption

#### Overview
Ensures data protection at rest and in transit.

#### Key Points
- PrivateLink ensures encrypted data traffic; GS CMK required.
- DACT required for column-level encryption; TLS v1.2+ enforced.
- Risk: KMS key creation/rotation by app teams not enforced.

#### Action Items
- Document encryption policies and KMS controls.
- Assign middleware to verify by July 10, 2025.

---

### Logging

#### Overview
Tracks activities and events for auditing.

#### Key Points
- App teams use Atlas dashboards; no central logging.
- Platform team discovers clusters every 2 hours for Inventory Central.

#### Action Items
- Propose central logging solution.
- Review by July 10, 2025.

---

### Monitoring

#### Overview
Outlines monitoring of MongoDB Atlas performance.

#### Key Points
- App teams enable alerts on dashboards; no cross-cluster dashboard.
- Inventory Central frequency adjusts based on usage.

#### Action Items
- Propose central dashboard by July 10, 2025.

---

### User Management

#### Overview
Manages user accounts and access levels.

#### Key Points
- Middleware sets organizations; app teams request projects/keys.
- Privileged access via PACT/DACT; temporary access for MongoDB support.

#### Action Items
| Task            | Responsible Team | Status       |
|-----------------|------------------|--------------|
| Manage Users    | Application Teams| In Progress |
| Review Access   | Middleware       | Pending     |

- Confirm process by July 5, 2025.

---

### Inventory/Data Classification

#### Overview
Tracks assets and classifies data.

#### Key Points
- Inventory Central logs all assets; platform team updates every 2 hours.
- Privacy integrated at design; no revisits needed.

#### Action Items
| Cluster Name | Region  | Team           | Logged Status |
|--------------|---------|----------------|----------------|
| [TBD]        | [TBD]   | [TBD]          | [TBD]         |

- Assign middleware to ensure logging by July 10, 2025.

---

### Multi-Region Deployment

#### Overview
Manages MongoDB setups across multiple regions.

#### Key Points
- Multi-region recommended for resiliency; GS CMK required.
- Risk: Port overlaps mitigated by PrivateLink.

#### Action Items
- Document multi-region setup.
- Review by July 5, 2025.

---

### Tenancy

#### Overview
Defines organizational and project hierarchy.

#### Key Points
- Hierarchy: Organization > Project > Cluster; 10–12 organizations, 160–170 projects.
- Organizations tied to BUs, managed by ME.

#### Action Items
- Map sample deployment ID to assets.
- Update by July 5, 2025.

---

### Atlas Search

#### Overview
Details usage of MongoDB Atlas Search.

#### Key Points
- Enables full-text and vector search on clusters.
- Implementation pending exploration.

#### Action Items
- Assign app teams to test by July 10, 2025.

---

### Additional Context

#### Historical Notes
- **Overlapping Ports**: Mitigated by PrivateLink; verify automation.
- **Your Highlights**: ME manages organizations, not projects/keys; Terraform in GS; control plane needs policy; unmanaged instances need tracking.
- **Extra Points**: Break-glass needs IdP; PrivateLink blocks external access; API keys are pairs.

#### Call Notes (June 20, 2025)
- API key rotation and cluster tracking discussed; awaiting transcript for details.
- [Update with transcript].

#### Items to Discuss with Manjari
1. Risk: Admin access privilege updates by app teams; seek compensating control guidance.
2. Risk: KMS key management by app teams not enforced; verify detective controls.
3. Decision: Manage Inventory via ME portal.
4. Decision: Add Atlas Search and Vector Search features.

#### Items to Ask Kishor
1. Number of Atlas organizations in GS.
2. Break-glass account configuration and management.
3. Middleware authentication/authorization provisioning.
4. Is CFT mandatory for project setup?
5. Is SSO/MFA enforced for console access?
6. SDLC pipeline details (permissions, approvers).
7. Enforcement of controls (e.g., TLS v1.2+, no cross-realm access).
8. URLs to add to the page.

---

### Drafting Instructions
- **Copy to Confluence**: Paste into a new page titled “MongoDB Atlas Governance.”
- **Customize**: Replace [TBD] with transcript or team data.
- **Format**: Use Confluence headings and tables.
- **Update**: Add transcript details under “Call Notes.”
- **Share**: Notify teams with a deadline (e.g., July 5, 2025).

---

### Next Steps for Today (June 27, 2025)
1. **Paste Draft**: Spend 1 hour copying into Confluence.
2. **Add Placeholders**: Insert call notes when available.
3. **Assign Tasks**: Set deadlines in tables.
4. **Share**: Email link with, “Draft for review by July 5, 2025.”
5. **Follow-Up**: Share transcript for refinements.

---

### Analogy
This is your city blueprint—mapping resources, builders (IaC), roads (network), gates (auth), and tools (search). The call with Manjari and Kishor will fill in the details—start today, and we’ll perfect it!

**Notes on Your Requests**:
- **Covered Items**: Terraform in GS, ME role, PrivateLink, API key pairs confirmed. Unmanaged instances and control plane need verification.
- **Extra Items**: Consider adding disaster recovery and compliance sections if relevant.
- Share transcript or answers from Manjari/Kishor, and I’ll update. Let me know your thoughts!
