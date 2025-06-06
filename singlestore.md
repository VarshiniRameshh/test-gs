# SingleStore Managed Service (S2MS) Architecture Pattern

**Created by Ravi Vittal, last updated by Rony John on May 29, 2025**

## Overview

SingleStore is a SaaS database that offers improved performance and scalability using a distributed OTP/AAP database platform. SingleStore hosts the database in their AWS account and provides a service called SingleStore Helios referred to as SingleStore Managed Service (S2MS) within the firm.

S2MS is in use for applications leveraging up to DP30 classified data post case-by-case approvals by Tech Risk. This page documents the Tech Risk approved pattern for using S2MS.

Adherence to this Tech Risk approved pattern ensures adherence to S2MS requirements and also minimizes the time needed to gain a Tech Risk approval for new or changed workloads using S2MS.

## Contact Team

| **Team**              | **Members**                              |
|-----------------------|------------------------------------------|
| **Tech Risk**         | Manjari Medha                            |
| **Middleware Engineering (ME)** | Ivan Santhumayor, Gnanarajan Subramanian, Saiyad Salman Amiruddin |
| **RBAC Slang Security Sync** | Shane Dunlea                            |

## S2MS Features

All features provided by SingleStore Helios which is not covered in this pattern will not be available for use. Usage of such features would require a Tech Risk review of the feature, business requirement, and use-case.

Managed Container Services such as notebooks, jobs, dashboards, apps, and cloud functions are not part of this pattern.

## Key Terms

### Workspace

A workspace is an independent deployment of compute resources which can be used to run a workload. It allows dynamic scaling of compute independently from storage. Each workspace can attach and detach databases as needed. A database may be attached to many workspaces simultaneously, allowing multiple workloads to operate on shared data. This allows data to scale across workloads without the complexity and cost of managing data across different data silos. Note that a Workspace Group can have up to 15 workspaces, and a database can have only one read-write workspace.

### Workspace Group (WG)

Each workspace is a part of the Workspace Group (WG), which allows for the configuration and management of access, updates, and data sharing. Each Workspace Group is deployed in an AWS region. It is a container that organizes multiple workspaces, providing a hierarchical structure for managing access control and resources across teams.

**The following diagram illustrates the relationships between these:**

- A Data Definition Language endpoint is attached to the Workspace Group (and this will be used for DACT-LITE and ME Portal internal connectivity).
- A Data Manipulation Language endpoint is attached to the Workspace Group.

## Reference Network Architecture Diagram

| **Private network requirement** |
|---------------------------------|
| Using VPC endpoints and Private links is the Tech Risk-approved way to ensure all traffic traverse private network paths. |


## SingleStore Customer Portal

The SingleStore customer portal authenticates via SAML (Ping Federate).

The portal provides a single UI to observe:

- Active Queries
- Billing
- Grafana dashboards with detailed performance monitoring of real-time and historical queries (CPU, memory, network, IO, etc.)
- Resource Profiling – Sampling the cluster to monitor and then catching any hidden problems under the hood. This is good for catching system processes like Garbage Collection, Column-store merger processes, or even queries which Active/Historical (Grafana) cannot catch.

More information can be found at: [SingleStore Cloud Onboarding/SingleStoreCustomerPortal](https://www.singlestore.com/).

## S2MS Cost Chargeback Model

Compute charges are only on CPU cores (no charge on memory or SSD).

S3 costs constitute daily backups and the bottomless S3 which is where the database resides at rest.

Ingress/egress costs are what AWS charges SingleStore (ingress is free). Unless there is huge traffic (very poor design) between AZ to AZ or from AWS to GS, this charge is negligible.

Read more: [SingleStore Cloud AWS Chargeback Model](https://www.singlestore.com/).

## S2MS Customer Support

Anyone with a GS account registered at [support.singlestore.com](https://support.singlestore.com) can create (SingleStore Cloud DB Support Ticket) and view customer support tickets. The ME team has weekly meetings with the S2MS team to review these tickets.

## Access Management (Authentication and Authorization)

### Authentication

#### Responsibilities

1. OAuth Client IDs obtained from GS (any environment), must not be used by human users. Any DB access made by client IDs must be made via the application.

#### DACT – Developer

Developers will obtain access through the DACT-Lite:

1. Users raise Jira tickets to get access through DACT-Lite.

#### User Permissions

There are two ways user entitlement is managed today for S2MS:

a. In absence of integration with ME Portal and approved entitlement mechanism, user access is manually granted/revoked by MW Eng, post approval/request by the database owners.

#### Responsibility

1. Ensure least privilege and immediately revoke S2MS access to leavers/movers.

b. Managed using the RBAC Slang Security Sync flow – Security Sync flow. See Security Sync flow details. Given the gaps in the first method and granularity of access provided by Security Sync, this is currently the recommended option where application functionality supports integration with security sync.

#### Responsibilities Specific for Security Sync Flow

1. BIU team must create a system account that will be the superuser for the S2MS Workspace Group. Only system accounts associated with production DIDs should be leveraged to access production WG to ensure PACT monitoring is in place.
2. Users must raise requests to ME team to provision Security Officer role in their S2MS Workspace Group for the system account in step 1.
3. Ensure that full access to the WG must be requested to only a single system account and that system account must not be used for other WGs or any other activities including on-prem.
4. Work with RBAC Slang Security Sync team to set security sync up for the application.
5. An approval flow must be setup which mandates DID owner’s approval in updating user privileges of databases belonging to a DID.
6. The Security Sync should be integrated with PERMIT to ensure ANY leavers’ or movers’ (from BIU or as applicable) access is revoked.
7. Permissions must be periodically updated in the Slang config (set up as part of #4) adhering to least privilege principle.
8. Non-prod system accounts must not have privileges on prod S2MS.
9. Deployment with a lower classification must not write to a lower classification database.
10. Deployment with a higher classification must not write to a lower classification database.
11. Ensure the Security Sync (backend-sync: Procmon) jobs job runs at least daily.

#### ME Onboards S2MS Workspaces to ME Portal

This is pre-requisite for DACT-lite access as well. The only supported authentication mechanism for S2MS is via JWT which users can obtain from PingFederate.

#### Responsibilities

1. Support Application team in setting up Authentication.
2. Ensure only OAuth token (JWT) generated by GS’s Ping Federate IdP is used for authentication into SingleStore.
3. Enable developer access (OAuth 2.0 and RO only).
4. Enforce controls related to Security Sync flow (for permission management):
   - A SingleStore Workspace Group must have only one account which has Security Officer role entitlement.
   - A system account must have Security Officer role on only one Workspace Group.
   - Permissions that allow for bulk export from S2MS must NOT be provisioned to any user/system accounts.

## Tech Risk-Approved Patterns

A shared responsibility model defines the requirements that are the responsibility of a BIU/app-owner team or the platform engineering team.

In cases where any of the requirement cannot be met, the team needs to review them with Tech Risk.

| **Category**                 | **BIU/App-Owner Team Responsibility**                                                                                              | **Platform Engineering Team/Middleware Engineering Responsibility**                                                                                     |
|------------------------------|------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Account Setup**            | Follow the steps to create a new Workspace in S2MS as documented at SingleStore Offering in GS#CloudOffering.<br><br>**Responsibilities:**<br>1. Request ME team to setup Workspace Groups, workspaces, and databases in SingleStore.<br>2. All Workspace Groups must be mapped to a Deployment Id (DID).<br>3. Inform ME team when the DID is updated or demised in the AppDir. | **Responsibilities:**<br>1. Support Application team in provisioning S2MS environment.<br>2. New features on SingleStore are not enabled without a Tech Risk review.<br>3. DACT Lite must be enabled for ALL Workspace Groups.<br>4. Each workspace group must be associated with one and only one Business Unit.<br>5. Each database in a Workspace Group must be associated to a DID belonging to the Business Unit that is associated with the Workspace Group.<br>6. Each Workspace must be associated with one DID which may or may not belong to the Business Unit. |
| **Data Storage**             | **Responsibilities:**<br>1. No sensitive DP20/DP30 data must be hosted on non-prod SingleStore instance.                     |                                                                                                                                                         |
| **Network Connectivity to SingleStore** | **Responsibilities:**<br>1. Get the IP address of provisioned S2MS endpoints (Workspace Group endpoint for DDL and workspace endpoint for DML) from ME team.<br>2. Create/Update connMan rules with the support of the tech-risk team to allow list outbound connectivity from NDS or DC nodes to only approved VPC endpoints.<br>3. Non-prod system accounts should not have access to prod S2MS.<br><br>**Note:** For distributed network solutions like K8R, Kubernetes, Hadoop, etc., the entire cluster needs to allow-listed to SingleStore connectivity endpoint. | **Responsibilities:**<br>1. Ensure the SingleStore endpoints for GS owned WGs and workspaces are accessible only from GS network (on-prem and cloud) by IP address whitelisting.<br>2. Middleware team must configure all new VPC endpoints. There must be a 1:1 mapping between VPC endpoint and a SingleStore endpoint.<br>3. Provide application teams with the IP addresses of the respective provisioned S2MS endpoints.<br>4. All traffic to and from S2MS must be private using VPC endpoints.<br>5. Firm-approved VPC Endpoint Policies must be used to restrict traffic to approved IP addresses (source i.e., NDS/DIDs) and approved endpoints (destination i.e., individual workspaces). |
| **SingleStore Portal Access** | **Responsibilities:**<br>1. Provide ME the list of users and IP address of their NDS for those who need access to the console to track observability metric for their workspaces. | **Responsibilities:**<br>1. Provision app teams users’ access to the console to track observability metric for their workspaces.<br>2. Whitelist the IPs for NDS of users to allow the connectivity to the SingleStore.<br>3. Ensure users will not be able to access the data via the portal. |
| **Encryption**        | **Responsibilities:**<br>1. Install the S2MS server’s public key certificate into client trust store.<br>2. AWS KMS key rotation must be enabled as per current standards. It is yearly rotation as per current standards. | **Responsibilities:**<br>1. Creating and managing AWS KMS keys for each prod Workspace Groups (separate keys for data and backup storage buckets). Dedicated keys will be created for each Workspace Group.<br>2. AWS KMS key rotation must be enabled as per Tech Risk guidelines. It is yearly rotation as per current standards.<br>3. TLS 1.2+ is enforced for encryption in transit.<br>4. For encryption in transit (TLS), distribute the public keys for the server-side certificates to clients.<br>SingleStore is DACT Lite-enabled which logs and monitors all developer access. |
| **Logging and Monitoring** | **Responsibilities:**<br>None. | **Responsibilities:**<br>1. Ensure the SingleStore is configured to log “ALL-QUERIES-PLAINTEXT” for all workspaces.<br>2. Logs are stored in GS environment for 13 months period. |
| **Inventory/Data Classification** | **Responsibilities:**<br>1. Ensure all data elements and their classification are registered and up to date in firm approved and supported registry as per the Data Classification Policy.<br>2. For databases containing Privacy data, ensure the database is clearly marked as “PRIVACY” in ME portal. | **Responsibilities:**<br>1. Ensure that Data Owners are aware of their responsibility to maintain Schema and other inventory data accurately.<br>2. Support app teams in registering data elements and their classification in the portal.<br>3. Maintain the inventory of Workspace Groups, databases, and classifications as the owners of S2MS. |

The mapping of these responsibilities to applicable Control 360 controls are documented [here](https://www.singlestore.com/control-360).

## Cloud Fast Track (CFT) to SingleStore Integration

Cloud Fast Track is a managed service that enables GS developers to develop software on AWS rapidly. The CFT platform enables many of the controls such as network ingress/egress and continuously evaluates applications against the latest guardrails which reduces the app teams’ compliance burden.

More details about Fast Track design can be found at [Cloud Fast Track](https://enghub.gs.com/cloud-platform/fasttrack/docs/tutorials).

Application teams sometimes need to access the S2MS for enabling connectivity from a particular CFT Account that is associated with a S2MS Workspace Group (for DDL endpoint, rarely) or a Workspace (for DML endpoint). VPC endpoints, dedicated to a SingleStore endpoint, need to be created for this in the Vendor VPC (a dedicated CFT Account that is associated with S2MS endpoint).

Once endpoints are created, application team needs to add the prefix list (which has the IP addresses of the VPC endpoints) to the context data after the Tech Review. Teams use this prefix to update security group rules to allow egress traffic to S2MS endpoints.

Refer the steps at [Access Community – Vendor Endpoints](https://enghub.gs.com/cloud-platform/fasttrack/docs/tutorials/access-community-vendor-endpoints).

Tech Risk team validates the use case and ensures the BIU responsible controls are enforced by the application team as part of the review.
