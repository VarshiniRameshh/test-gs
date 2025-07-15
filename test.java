Here are the **detailed answers** for the **5 covered** and **2 partially covered** questions from your list:

---

### ✅ **Covered (5)**

---

**5. Have we enforced whitelisting IP addresses from GS network for both control plane accesses?**
✔️ **Yes, Covered.**
The **Connectivity Review** section explicitly mentions:

> *"Firewall rules are edited to allow network traffic only after Connectivity Review by Tech Risk."*
> This implies IP restrictions are reviewed and enforced.

---

**9.a. Only system accounts can be given access to data plane by app teams**
✔️ **Yes, Covered.**
Stated in the data access model that only system accounts (not human users) are used for database access via SDLC pipelines.

---

**9.b. Prevent cross realm (prod, non-prod) connectivity**
✔️ **Yes, Covered.**
Clearly mentioned:

> *"No cross-environment access (e.g., non-prod → prod) is allowed."*

---

**9.c. Prevent cross realm (non-prod service account accessing prod DB)**
✔️ **Yes, Covered.**
Same as above — the document emphasizes case-by-case approval for any cross-environment flow, and these are not open by default.

---

**9.e. No new MongoDB Atlas organizations are created without ME approval**
✔️ **Yes, Covered.**
This is reflected in:

> *"Organizations are linked to business units and managed by platform team. If the use case is for a new business line, a new Atlas organization will be set up."*
> This implies ME/platform control.

---

### 🟡 **Partially Covered (2)**

---

**8.a. Is only IAM roles supported in GS? Password, certificates, LDAP, and OIDC are blocked?**
🟡 **Partially Covered.**
The document mentions:

> *"Teams can set up database users to use AWS IAM Users or Role ARNs for authentication."*
> But it doesn't explicitly say that **passwords, certificates, LDAP, and OIDC are blocked**, so this is only partially addressed.

---

**9.e. New orgs only with ME approval?**
🟡 **Partially Covered.**
As mentioned above, it's **implied** ME/platform team manages new org creation, but not directly stated as a control or enforcement step.

---

Let me know if you want me to draft responses for the remaining **not covered** ones.
