Connectivity Review
Tech Risk reviews and approves the connectivity requests after multiple checks including validation of technical details like source/destination, ports, environments, data classification.
Firewall rules are edited to allow network traffic only after Connectivity Review by Tech Risk.

Triggered whenever a system needs to access a particular MongoDB Atlas Account
For Prod MongoDB Atlas Account, design review approval is a pre-requisite for connectivity approval
This serves as the actual environment poke, where connectivity is either granted or blocked

Following controls need to be self-attested by application teams or validated by Tech Risk during the Tech Risk connectivity review:
1. Secure Ports

Only Port 443 is allowed for data transfer and Port 80 for OSCP certifications checks allowed. (Already enforced for CFT connectivity to SF).

2. Cross-Environment Check

No cross-environment access (e.g., non-prod → prod). i.e., environment classification of source/deployment should match with the MongoDB Atlas Account environment. In order to get the MongoDB Atlas Account associated with destination IP addresses in the firewall allow rule, refer to ME Portal.
Do not allow prod to where you need access from a non-prod system to sensitive DP20+ data for testing purposes and exception is needed. Please see Non-prod to prod/sensitive data access section.

3. Data Classification Check

No cross-classification access
For example:

DP 30 to DP-10 (writes) is not allowed.
DP-10 reading from DP 30 (vs) allowed unless justified and inline with design reviewed.
