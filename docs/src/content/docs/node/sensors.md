---
title: sensors
description: list of possible features implemented via sensors
---

display status:
	blue LED
		=> own pet is currently on this device
	RGB LED (neopixel)
		=> visualizes pets emotions (also works while pet is away)
		
keep track of time:
	RTC
		=> day/night cycle
		=> time-based events
		=> create a log of previous actions and their time

measure environment:
	temperature sensor
		=> pet has to be warmed up/cooled down
	(humidity sensor)
		=> pet is unable to be walked outside when humidity is too high
	(light sensor)
		=> more active during bright environments
	(proximity sensor)
		=> pet detects owner
			=> pet wakes up from sleep
			=> pet gets happy if already awake
		=> pet can be petted

track orientation:
	| gyro
	| accelerometer
	| (magnetometer)
	=> wake the pet up
	=> move pet when device is tilted
	=> pet moves when device is in motion
		=> optional: pedometer
		

  
