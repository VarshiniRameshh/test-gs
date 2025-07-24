Here is the **refined and professional version** of the **Minutes of Meeting (MoM)** with clearly outlined **action items**, structured for internal or stakeholder distribution:

---

# **Minutes of Meeting (MoM)**

**Date:** July 24, 2025
**Subject:** MongoDB Atlas — Control Plane & Data Plane Access Governance
**Attendees:** Middleware Engineering (ME), Application Teams, Tech Risk, SDLC, Cloud & Platform Stakeholders

---

## **1. MongoDB Atlas Versioning & Feature Management**

* All deployments must use MongoDB versions officially supported by MongoDB Inc.
* MongoDB will proactively notify application teams when versions approach end-of-life and assist with upgrades.
* Application teams are not required to take action beyond following MongoDB's recommended upgrade paths.
* Activation of new MongoDB features (e.g., vector search) requires explicit Tech Risk approval.

---

## **2. Control Plane Access by Middleware Engineering (ME)**

* **Access Types:**

  * **REST API**: For system access via SDLC pipelines only.
  * **Graphical UI**: For individual users, restricted via permissions.
* **Authentication & Security Controls:**

  * All access uses Single Sign-On (SSO) and Multi-Factor Authentication (MFA).
  * API keys are tied to IAM roles; no direct key access for individuals.
* **Access Provisioning:**

  * Managed at the organizational level by ME.
  * Break-glass access is similarly SSO + MFA enforced.

---

## **3. CFT Account Usage**

* No separate CFT account is required per team for MongoDB Atlas.
* Application CFT accounts can be reused. Teams must provision their own KMS keys and IAM roles, regardless of AWS hosting.

---

## **4. SDLC Pipelines & API Key Management**

* Provisioning is performed exclusively through SDLC pipelines using Terraform Enterprise (TFE).
* API keys are:

  * Scoped to IAM roles only (not users).
  * Purpose-specific (e.g., SDLC, DevOps) and approved by Tech Risk.
* All pipelines require approval from the DID owner.
* Application teams cannot self-manage sensitive permissions outside of SDLC.

---

## **5. IP Whitelisting for Control Plane Access**

* IPs are whitelisted centrally using systems like AppProxy, iBoss, and SkyEgress.
* UI access is currently restricted to the GS network via SSO.
* Further enforcement through UI IP whitelisting is under evaluation for additional control.

---

## **6. Programmatic Access to Control Plane**

* Only IAM-role-bound API keys are allowed.
* Individuals do not receive these keys directly.
* Service accounts for MongoDB are not yet generally available (GA), so not currently in use.

---

## **7. UI Access (Web Interface)**

* UI access is protected via SSO and MFA.
* Access allows:

  * Viewing cluster health and metrics.
  * Managing cluster support settings.
  * Accessing connection strings (read-only for most users).
* Access permissions are:

  * **Read-only** by default.
  * **Project owners** can perform limited administrative tasks.
  * Operations like stopping/deleting clusters are restricted.
* Access requests flow through TMD tickets, auto-approved via manager review, and integrated with Permit and Atlas systems.

---

## **8. Data Plane Access & IAM Role Controls**

* Only **IAM roles** are approved for data plane access.
* Password and certificate-based methods are discouraged (not approved by Tech Risk) but are not technically blocked.
* On-prem to cloud access is via **SkyBroker**, which allows service accounts (not users) to assume IAM roles.
* Cross-environment access (e.g., non-prod → prod) is blocked via network and CFT guardrails.

---

## **9. Governance, Approvals, & Network Controls**

* Data classification and segregation policies apply during access provisioning.
* Creation of new MongoDB Atlas organizations requires ME approval.
* Network access from MongoDB Atlas is restricted by GS security designs.
* Cross-realm or cross-environment access is not allowed unless explicitly permitted and justified.

---

## **10. AWS KMS Key Enablement**

* Application teams are responsible for setting up AWS KMS keys.
* Internal guidance exists, but may lack clarity.
* Automation and monitoring tools are being evaluated to streamline KMS enablement and enforcement.

---

## ✅ **Action Items / Follow-ups**

| # | Item                                                                                             | Owner(s)                             | Due         |
| - | ------------------------------------------------------------------------------------------------ | ------------------------------------ | ----------- |
| 1 | Evaluate feasibility and implement IP whitelisting for Control Plane UI access                   | Tech Risk, Network Security          | TBD         |
| 2 | Share official Control Plane UI link and updated TMD/Permit request guide with application teams | Middleware Engineering               | ASAP        |
| 3 | Review and improve internal documentation on AWS KMS key setup for MongoDB Atlas                 | Cloud Security, Application Security | TBD         |
| 4 | Explore automation options for KMS key enablement and usage detection                            | Middleware Engineering, SDLC Team    | In Progress |
| 5 | Continue to monitor GA availability and security suitability of MongoDB service accounts         | Middleware Engineering               | Ongoing     |

---

**End of MoM**
Feel free to request the MoM in other formats (e.g., Word, Confluence-ready, PDF) or with specific stakeholder tagging.

Let me know if you’d like a **visual architecture overview** or **workflow diagram** for this.
