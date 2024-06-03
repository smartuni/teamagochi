---
title: Lwm2m Object
description: Documentation of the Lwm2m Object
---
# The Pet
 
Every virtual pet is a digital entity that simulates the behavior and characteristics of a pet, designed for interaction through the Teamagochi devices and applications.
 
## A pet's state
 
The pet state is a comprehensive representation of the virtual pet's overall situation and consists of three main components: status, conditions, interactions. It is complete in a sense that a syncronisation of the state, in the sense of a digital twin, results in a identical representation.
 
### Status
 
Refers to **permanent characteristics** of a pet, such as its species and to **quantitative attributes**.
 
All status terms are **nouns** and are written capitalized, e.g. "Color", "Happiness", "Health".
 
Examples:
 
- The Color of a pet is green.
- The Happiness of a pet is 23.
 
#### Implementation
 
The status is owned by the Web-Backend application.
The device and the frontend application may request the backend for changes through it's defined interfaces.
 
| LwM2M      | Value |
|------------|-------|
| ID-Range   | 0-19  |
| Operations | RW    |
 
### Condition
 
Represents the **current physical and emotional state** of a pet and is an evaluation of the pet status.
 
All condition terms are **adjectives**, e.g. "hungry", "ill", "dirty".
 
Examples:
 
- The pet is currently hungry.
- The pet is currently dirty.
 
#### Implementation
 
The condition is owned by the Web-Backend application.
The device and the frontend application may request the backend for changes through it's defined interfaces.
How the condition is evaluated from the status is defined by the backend application.
 
| LwM2M      | Value |
|------------|-------|
| ID-Range   | 20-39 |
| Operations | RW    |
 
### Interactions
 
Describes interaction with a virtual pet.
 
All interaction terms are **verbs**, e.g. "feed", "medicate".
 
Examples:
 
- I feed the pet.
- I clean the pet.
 
#### Implementation
 
The interactions are owned by the device and the frontend.
The backend must evaluate changes and update a pet's state accordingly.
How interactions affect the state is defined by the backend application.
 
| LwM2M      | Value |
|------------|-------|
| ID-Range   | 40-59 |
| Operations | RW    |

### Object Id 32769
|         Name            | ID | Mandatory |  Type   |  Range | Units | Operations |
| ----------------------- | -- | --------- | ------- | ------ | ----- | ---------- | 
| Id                      |  0 |    Yes    | Integer |        |       |     RW     |
| Name                    |  1 |    Yes    | String  |        |       |     RW     |   
| Color                   |  2 |    Yes    | Integer |        |       |     RW     |
| Happiness               |  3 |    Yes    | Integer |        |       |     RW     |
| Wellbeing               |  4 |    Yes    | Integer |        |       |     RW     |
| Health                  |  5 |    Yes    | Integer |        |       |     RW     |
| XP                      |  6 |    Yes    | Integer |        |       |     RW     |
| Hunger                  |  7 |    Yes    | Integer |        |       |     RW     |
| Cleanliness             |  8 |    Yes    | Integer |        |       |     RW     |
| Fun                     |  9 |    Yes    | Integer |        |       |     RW     |
| Hungry                  | 20 |    Yes    | Boolean |        |       |     RW     |  
| Ill                     | 21 |    Yes    | Boolean |        |       |     RW     |   
| Bored                   | 22 |    Yes    | Boolean |        |       |     RW     |   
| Dirty                   | 23 |    Yes    | Boolean |        |       |     RW     |
| feed                    | 40 |    Yes    | Integer |        |       |     RW     |    
| medicate                | 41 |    Yes    | Integer |        |       |     RW     |
| play                    | 42 |    Yes    | Integer |        |       |     RW     |
| clean                   | 43 |    Yes    | Integer |        |       |     RW     |  
