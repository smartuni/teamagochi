---
title: Lightweight M2M (LwM2M)
description: Introduction to the LightweightM2M protocol.
---
OMA Lightweight M2M (LwM2M) is a protocol from the Open Mobile Alliance for machine to machine (M2M) or Internet of things (IoT) device management and service enablement.

- Device management capabilities include remote provisioning of security credentials, firmware updates, remote device diagnostics and troubleshooting.
- Service enablement capabilities include sensor and meter readings, remote actuation and configuration of host devices. 

## Documents & Resources

- [Lightweight Machine to Machine Technical Specification: Core (1.2.1 / 2022-12-09)][lwm2m_spec_core]
- [Lightweight Machine to Machine Technical Specification: Transport Bindings (1.2.1 / 2022-12-09)][lwm2m_spec_transport]
- [Lightweight Machine to Machine Requirements (1.2 / 2020-11-10)][lwm2m_requirements]
- [OMA Object and Resource Registry][lwm2m_registry]

[lwm2m_spec_core]: https://www.openmobilealliance.org/release/LightweightM2M/V1_2_1-20221209-A/OMA-TS-LightweightM2M_Core-V1_2_1-20221209-A.pdf
[lwm2m_spec_transport]: https://www.openmobilealliance.org/release/LightweightM2M/V1_2_1-20221209-A/OMA-TS-LightweightM2M_Transport-V1_2_1-20221209-A.pdf
[lwm2m_requirements]: https://www.openmobilealliance.org/release/LightweightM2M/V1_2_1-20221209-A/OMA-RD-LightweightM2M-V1_2-20201110-A.pdf
[lwm2m_registry]: https://technical.openmobilealliance.org/OMNA/LwM2M/LwM2MRegistry.html

## Introduction

The **LwM2M Enabler** has two components: **LwM2M Server** and **LwM2M Client**.

The **LwM2M Device** acts as a LwM2M Client and the **M2M service, platform or application** acts as the LwM2M Server.

![Shows the overall architecture](./LwM2M-Enabler.jpg)

Four **interfaces** are designed between these two components as shown below:

- Bootstrap
- Client Registration
- Device management and service enablement
- Information Reporting

Each of these interfaces is related to a set of **operations**. The **uplink** operatations are initiated by the device and the **downlink** operations
are initiated by the server.

| Interface                                | Direction | Operation                                                                                         |
|------------------------------------------|-----------|---------------------------------------------------------------------------------------------------|
| Bootstrap                                | Uplink    | Bootstrap-Request, Bootstrap-Pack-Request                                                         |
| Bootstrap                                | Downlink  | Bootstrap-Write, Bootstrap-Read, Bootstrap-Discover, Bootstrap-Delete, Bootstrap-Finish           |
| Client Registration                      | Uplink    | Register, Update, De-register                                                                     |
| Device Management and Service Enablement | Downlink  | Create, Read, Read-Composite, Write, Delete, Execute, Write-Attributes, Write-Composite, Discover |
| Information Reporting                    | Downlink  | Observe, Observe-Composite, Cancel Observation, Cancel Observation-Composite                      |
| Information Reporting                    | Uplink    | Notify, Send                                                                                      |

## Resource Model

The LWM2M Enabler defines a simple **resource model** where each piece of information made available by the LWM2M Client is a **Resource**.

Resources are logically organized into **Objects**. The figure illustrates this structure, and the relationship between Resources, Objects, and the LWM2M Client.

The LWM2M Client may have any number of Resources, each of which belongs to an Object.

Resources and Objects have the capability to have **multiple instances** of the Resource or Object.

Resources are defined per Object, and each Resource is given a unique identifier within that Object.

Each Object and Resource is defined to have one or more **operations** that it supports.

A Resource MAY consist of multiple instances called a **Resource Instance** as defined in the Object specification.

![Shows model of object and resources](./LwM2M-Objects-Resources.png)

<!--
https://wiki.openmobilealliance.org/display/TOOL/What+is+LwM2M

https://github.com/eclipse-leshan/leshan/tree/master

SCHEMA?: Not that easy:
http://www.openmobilealliance.org/tech/profiles/LWM2M-v1_1.xsd
-->