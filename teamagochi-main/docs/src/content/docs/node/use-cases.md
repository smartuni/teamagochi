---
title: Use Cases
description: The Use Cases for the MVP
---

||UC01|
|----|---|
|title|display the pet|
|pre condition|Display,Data from Backend|
|post condition|the pet from the backend is displayed on the screen|

||UC02|
|----|---|
|title|display the stats of the pet|
|pre condition|Display,Data from Backend|
|post condition|the stats pet from the backend is displayed on the screen|

||UC03|
|----|---|
|title|feed the pet|
|pre condition|Display,Button, trigger from backend|
|post condition|the pet is fed|
|basic path|1. trigger from backend "hungry" </br> 2. notification to user </br> 3. user can feed the pet over the menu </br> 4. status update is sent to the backend|

||UC04|
|----|---|
|title|menu|
|pre condition|Display,Button|
|post condition|menu is usable|
|basic path|1. press button </br> 2. menu usable </br> 3. close menu|

||UC05|
|----|---|
|title|feedback|
|pre condition|vibration motor|
|post condition|haptic feedback|
|basic path|1. trigger (hungry, ill) </br> 2. vibrate|
