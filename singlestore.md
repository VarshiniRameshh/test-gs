# SingleStore Managed Service (S2MS) Architecture Pattern (Draft)

Created by Ravi Vittal, last updated by Rony John on May 29, 2025

## Overview

SingleStore is a SaaS database that offers improved performance and scalability using a distributed OLTP/AP database platform. SingleStore hosts the database in their AWS account and provides a service called SingleStore Helios referred to as SingleStore Managed Service (S2MS) within the firm.

S2MS is different from on-prem instance of SingleStore which is called MemSQL.

S2MS is in use for applications leveraging up to DP30 classified data post case-by-case approvals by Tech Risk. This page documents the Tech Risk approved pattern for using S2MS.

Adherence to the Tech Risk approved pattern here ensures adherence requirement and also minimizes the time needed to gain a Tech Risk approval for new or changed workloads using S2MS.

## Contact

**Tech Team**

- Contact Name: Manjari Medha

**Tech Risk**

- Ivan Santhumayor
- Gnanarajan Subramanian
- Saiyad Salman Amiruddin

**Middleware Engineering (ME)**

- Shane Dunlea

**RBAC Slang Security Sync**

## S2MS Features

Apps provided by SingleStore Helios which is not covered in this pattern will not be available for users to use. These features would require a Tech Risk review of the feature, business requirement and use-case. Managed Container Services such as notebooks, jobs, dashboards apps and cloud functions are not part of this pattern.

## Key Terms

**Workspace**

A workspace is an independent deployment of compute resources which can be used to run a workload. It allows dynamic scaling of compute independently from storage. Each workspace can attach and detach databases as needed. A database may be attached to many workspaces simultaneously, allowing multiple workloads to operate on shared data. This allows data to scale across workloads without the complexity and cost of managing data across different data silos. Note that a Workspace Group can have up to 15 workspaces and a database can have only one read-write workspace.

**Workspace Group (WG)**

Each workspace is a part of the Workspace Group (WG), which allows for the configuration and management of access, updates, and data sharing. Each Workspace Group is deployed in an AWS region. It is a container that organizes multiple workspaces, providing a hierarchical structure for managing access control and resources across teams.

**The following diagram illustrates the relationships between these.**

- A Data Definition Language endpoint is attached to the Workspace Group (and this will be used for DACT-Lite and ME Portal internal connectivity).
- A Data Manipulation Language endpoint is attached to the Workspace.

## Reference Network Architecture Diagram

**Private network requirement**

Using VPC endpoints and Private links is the Tech Risk-approved way to ensure all traffic traverse private network paths.

## SingleStore Customer Portal

The SingleStore customer portal authenticates via SAML (Ping Federate).

The portal provides a simple UI to observe:

- Active Queries
- Billing
- Grafana dashboards with detailed performance monitoring of real-time and historical queries (CPU, memory, network, IO etc.)
- Resource Profiling – Sampling the cluster to monitor and then catching any hidden system process like Garbage Collection, Column-store merger processes, or even queries which Active/Historical (Grafana) cannot catch.

More information can be found at: [SingleStore Cloud Onboarding/SingleStoreCustomerPortal](https://example.com).

## S2MS Cost Chargeback Model

Compute charges are only on CPU cores (no charge on memory or SSD).

S3 costs constitute daily backups and the bottomless S3 which is where the database resides at rest.

Ingress/egress costs are what AWS charges SingleStore (ingress is free). Unless there is huge traffic (very poor design) between AZ to AZ or from AWS to GS, this charge is negligible.

Read the doc: [SingleStore Cloud AWS Chargeback Model](https://example.com).

## S2MS Customer Support

Anyone with the GS account registered at support.singlestore.com can create (SingleStore Cloud DB Support Ticket) and view customer support tickets. The ME team has weekly meets with the S2MS team to review these tickets.

## Access Management (authentication and authorization)

ME onboards S2MS workspaces to ME Portal. This is pre-requisite for DACT-lite access as well. The only supported authentication mechanism for S2MS is via JWT which users can obtain from PingFederate.

### Authentication

**Responsibilities:**

- OAuth Client IDs obtained from GS (any environment) must not be used by Human users. Any DB access made by client IDs MUST only be made via the Application.

**DACT**

Developers will obtain access through the DACT-lite:

**Responsibilities specific for Developer Access Flow:**

- Users raise Jira tickets to get access through DACT-lite for developer access.

**User Permissions**

There are two ways user entitlement is managed today for S2MS:

a. In absence of integration with ME Portal and approved entitlement mechanism, user access is manually granted/revoked by MW Eng. post approval/request by the database owners.

**Responsibilities:**

- Ensure least privilege and immediacy to RBAC Slang Security Sync to revoke access for leavers/movers.

b. Managed by using the RBAC Slang Security Sync/Security Sync flow. See Security Sync flow for details. Given the gaps in the first method and granularity of access provided by Security Sync, this is currently the recommended option where application functionality supports integration with security sync.

**Responsibilities specific for Security Sync flow:**

- BIU team must create a system account that will be the superuser for the S2MS Workspace Group. Only system accounts associated with production DIDs should be leveraged to access production WG to ensure PACT/monitoring is in place.
- Users must raise requests to ME team to provision Security Officer role in their S2MS Workspace Group for the system account in step 1.
- Group for the system account in step 1.
- Ensure that full access to the WG must be requested to only a single system account and that system account MUST NOT be used for other WGs or any other activities including on-prem.
- Work with RBAC Slang Security Sync team to set security sync for the application.
- An approval flow must be set up which mandates DID owner’s approval in updating user privileges of databases belonging to a DID.
- The Security Sync should be integrated with PERMIT to ensure ANY leavers’ or movers’ (from BIU or as applicable) access is revoked.
- Permissions must be periodically updated in the Slang config (set up as part of #4) adhering to least privilege principle.
- Non-prod system accounts must not have privileges on prod S2MS.
- Deployment with a lower classification must not write to a higher classification database.
- Deployment with a higher classification must not write to a lower classification database.
- Ensure the Security Sync (backend-sync/Ezcom) jobs job runs at least daily.

**Platform Engineering Team/Middleware Engineering Responsibilities:**

- Support Application team in setting up Authentication.
- Ensure only OAuth token (JWT) generated by GS's Ping Federate IdP is used for authentication into SingleStore.
- Enable developer access (OAuth 2.0 and RO only).
- Enforce controls related to Security Sync flow (for permission management):
  - A SingleStore Workspace Group must have only one account which has Security Officer role entitlement.
  - A system account must have Security Officer role on only one Workspace Group.
  - Permissions that allow for bulk export from S2MS MUST NOT be provisioned to ANY user/system accounts.

## Tech Risk-Approved Patterns

A shared responsibility model defines the requirements that are the responsibility of a BIU/app-owner team or the platform engineering team.

In cases where any of the requirement cannot be met, the team needs to review them with Tech Risk.

### Account Setup

**BIU application owner:**

- Follow the steps to create a new Workspace in S2MS as documented at SingleStore Offering in GS#CloudOffering.
- Request ME team to setup Workspace Groups, workspaces and databases in SingleStore.
- All Workspace Groups must be mapped to a Deployment Id (DID).
- Inform ME team when the DID is updated or demised in the AppDir.

**Platform management team:**

- Support Application team in provisioning S2MS environment.
- New features on SingleStore are not enabled without a Tech Risk review.
- DACT Lite must be enabled for ALL Workspace Groups.
- Each workspace group must be associated with one and only one Business Unit.
- Each database in a Workspace Group must be associated to a DID belonging to the Business Unit that is associated with the Workspace Group.
- Each Workspace must be associated with one DID which may or may not belong to the Business Unit.

### Data Storage

**BIU/application owner Responsibilities:**

- No sensitive DP20/DP30 data must be hosted on non-prod SingleStore instance.

### Network connectivity to SingleStore

**BIU/application owner Responsibilities:**

- Get the IP address of provisioned S2MS endpoints (Workspace Group endpoint for DDL and workspace endpoint for DML) from ME team.
- Create/Update commMan rules with the support of the tech-risk team to allow list outbound connectivity from NDS or DC nodes to only approved VPC endpoints.
- Non-prod system accounts should not have access to prod S2MS.

**Note:** For distributed network solutions like NSR, Kubernetes, Hadoop, etc., the entire cluster needs to allow-listed to network connectivity to SingleStore endpoint.

**Platform Engineering Team/Middleware Engineering Responsibilities:**

- Ensure the SingleStore endpoints for GS owned WGs and workspaces are accessible only from GS network (on-prem and cloud) by IP address whitelisting.
- Middleware team must configure all new VPC endpoints. There must be a 1:1 mapping between VPC endpoint and a SingleStore endpoint.
- Provide application teams with the IP address of the respective provisioned S2MS endpoints.
- All traffic to and from S2MS must be private using VPC endpoints.
- Firm-approved VPC Endpoint Policies must be used to restrict traffic to approved IP addresses (source i.e. NDS/DIDs) and approved endpoints (destination i.e. individual workspaces).

### SingleStore Portal Access

**BIU/application owner Responsibilities:**

- Provide ME the list of users and IP address of their NDS for those who need access to the console to track observability metric for their workspaces.

**Platform Engineering Team/Middleware Engineering Responsibilities:**

- Provision app teams users’ access to the console to track observability metric for their workspaces.
- Whitelist the IPs for NDS of users to allow the connectivity to the SingleStore.
- Ensure users will not be able to access the data via the portal.

### Encryption

**BIU/application owner Responsibilities:**

- Install the S2MS server’s public key certificate into client’s trust store.
- AWS KMS key rotation must be enabled as per Tech Risk guidelines. It is yearly rotation as per current standards.

**Platform Engineering Team/Middleware Engineering Responsibilities:**

- Creating and managing AWS KMS keys for each prod Workspace Groups (separate keys for data and backup storage buckets). Dedicated keys will be created for each Workspace Group.
- TLS 1.2+ is enforced for encryption in transit.
- For encryption in transit (TLS), distribute public keys for the server-side certificates to clients.

### Logging and monitoring

**BIU/application owner Responsibilities:**

None.

**Platform Engineering Team/Middleware Engineering Responsibilities:**

- Ensure the SingleStore is configured to log **ALL-QUERIES-PLAINTEXT** for all workspaces.
- Logs are stored in GS environment for 13 months period.

SingleStore is DACT Lite-enabled which logs and monitors all developer access. These logs are audited.

### Inventory/data classification

**BIU/application owner Responsibilities:**

- Ensure all data elements and their classification are registered and up to date in firm approved and supported registry as per the Data Classification Policy.
- For databases containing Privacy data, ensure the database is clearly marked as **PRIVACY** in ME portal.

**Platform Engineering Team/Middleware Engineering Responsibilities:**

- Ensure that Data Owners are aware of their responsibility to maintain Schema and other Inventory data accurately.
- Support app teams in registering data elements and their classifications in the portal.
- Maintain the inventory of workspaces, databases and classification as per Data Owners of S2MS.

The mapping of these responsibilities to applicable Control 360 controls are documented [here](https://example.com).

## Cloud Fast Track (CFT) to SingleStore Integration

Cloud Fast Track is a managed service that enables GS developers to develop software on AWS rapidly. The CFT platform enables many of the controls such as network ingress/egress and continuously evaluates applications against the latest guardrails which reduces the app teams’ compliance burden.

More details about Fast Track can be found at [Fast Track](https://enghub.gs.com/cloud-platform/fasttrack/docs/tutorials/access-community-vendor-endpoints).

Application teams sometimes need to access the S2MS for enabling connectivity from a particular CFT Account that is associated with a S2MS Workspace Group (for DDL endpoint, rarely) or a Workspace (for DML endpoint). VPC endpoints, dedicated to a SingleStore endpoint, need to be created for this in the Vendor VPC (a VPC to facilitate vendor connectivity in a dedicated CFT Account that is outside of CFT Communities).

Once endpoints are created, application team needs to add the prefix list (which has the IP address of the VPC endpoints) to the context data after the Tech Review. Teams use this prefix list to configure the security group rules to allow egress traffic to S2MS endpoints.

Refer the steps at [CFT Access to the S2MS](https://enghub.gs.com/cloud-platform/fasttrack/docs/tutorials/access-community-vendor-endpoints).

Tech Risk team validates the use case and ensures the BIU responsible controls are enforced by the application team as part of the review.
