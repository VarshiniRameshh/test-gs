# SingleStore Managed Service (S2MS) Architecture Pattern (Draft)

*Created by [Ravi Vittal](#), last updated by [Rony John](#) on **May 29, 2025** • 8 minute read*

## Overview

SingleStore is a SaaS database that offers improved performance and scalability using a distributed OLTP/OLAP database platform.  
SingleStore hosts the database in their AWS account and provides a service called SingleStore Helios referred to as SingleStore Managed Service (S2MS) within the firm.

S2MS is different from on-prem instance of SingleStore which is called [MemSQL](#).  
S2MS is in use for applications leveraging up to DP30 classified data post case-by-case approvals by Tech Risk.  

This page documents the Tech Risk approved pattern for using S2MS.  
Adherence to approved pattern documented here ensures adherence to required controls and also minimizes the time needed to gain a Tech Risk approval for new or changed workloads using S2MS.

## Contacts

| Contact Team                        | Contact Name                                                                 |
|------------------------------------|------------------------------------------------------------------------------|
| Tech Risk                          | `@Manjari Medha`                                                             |
| Middleware Engineering (ME)        | `@Ivan Santhumayor` `@Gnanarajan Subramanian` `@Saiyad Salman Amiruddin`   |
| RBAC Slang Security Sync           | `@Shane Dunlea`                                                              |

## S2MS Features

Any feature provided by SingleStore Helios which is not covered in this pattern will not be available for users.  
Usage of such features would require a Tech Risk review of the feature, business requirement and use-case.

Managed Container Services such as notebooks, jobs, dashboards apps and cloud functions are not part of this pattern.
