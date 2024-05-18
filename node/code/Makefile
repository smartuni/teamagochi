APPLICATION = teamagotchi
BOARD = feather-nrf52840-sense

# This has to be the absolute path to the RIOT base directory:
RIOTBASE ?= $(CURDIR)/RIOT
TEAMAGOTCHI_NODES_BASE = $(CURDIR)

# Comment this out to disable code in RIOT that does safety checking
# which is not needed in a production environment but helps in the
# development process:
DEVELHELP ?= 1

# Include test_folder example to showcase directories being included
DIR += test_folder

FEATURES_REQUIRED += cpp
FEATURES_REQUIRED += libstdcpp

# Internal Modules
EXTERNAL_MODULE_DIRS += $(CURDIR)/modules
USEMODULE += external_module
USEMODULE += lwm2m_handler
#USEMODULE += display

# External modules
USEMODULE += cpp11-compat
USEMODULE += ili9341
USEMODULE += ztimer
USEMODULE += ztimer_msec
USEMODULE += shell
USEMODULE += xtimer

# As there is an 'Kconfig' we want to explicitly disable Kconfig by setting
# the variable to empty
SHOULD_RUN_KCONFIG ?=

# Include "inc" directory for the header files
INCLUDES += -I$(CURDIR)/inc

# Set the C standard to C17 [C23 (even experimental) is not yet supported]
# and enable all warnings
CFLAGS += -std=c17 -Wall -Wextra

# Set the C++ standard to C++20 [C++23 with all the cool stuff is not yet supported]
# and enable all warnings
CXXEXFLAGS += -std=c++20 -Wall -Wextra

# Disable volatile warnings
# C++20 introduced a deprecation for this, which was then
# reverted in C++23 because it wasn't thought through
CXXEXFLAGS += -Wno-volatile

#include $(CURDIR)/modules/lwm2m/Makefile.include
include $(RIOTBASE)/Makefile.include


BOARD_BLACKLIST += native64


# SDL requires more stack
ifneq (,$(filter native native64,$(BOARD)))
  CFLAGS += -DTHREAD_STACKSIZE_MAIN=64*1024
else ifneq (,$(filter esp%,$(CPU_FAM)))
  CFLAGS += -DTHREAD_STACKSIZE_MAIN=4*1024
else
  CFLAGS += -DTHREAD_STACKSIZE_MAIN=2*1024
endif

# Note that this will probably only work for me <3
windows: all
	@echo "Creating UF2"
	@python RIOT/dist/tools/uf2/uf2conv.py -f 0xADA52840 bin/feather-nrf52840-sense/teamagotchi.hex --base 0x1000 -c
	@echo "Flashing UF2 via Windows (Make sure your device is actually at D)"
	@powershell.exe -command "cp ./flash.uf2 d:/flash.uf2"
	@echo "Connecting COM via USBIPD (Make sure you have the USBIPD installed + your device bound to the WSL)"
	@sleep 3
	@usbipd.exe attach --wsl -i 1209:7d00 
	@sleep 3
	@echo "Connected :D"
	@echo "Cleaning up uf2 flash file"
	@rm flash.uf2

# Login Process
# Frontend Mockup
# Web Backend Authetnication showcase
# Hardware Details
# Flow Diagram of App (Node)
# Lower M2M Objects (Node)
# Timeline

## Timeline
# Registration
# - Frontend
#   - User enters ID from Device
# - Backend
#  - User ID is stored in DB
#  - Displays ID on Device
# - Device
#   - Get and Display ID
#   - Have some unqiue ID for each device
# Deadline: 27. May 2024

# Step One
# - Getting Login to Work in Frontend 
# - Helping with login for Frontend if neccessary (Web Backend)
# - Starting the setup for Milestone 2 (Web Backend)
# - Starting the setup of Node for Milestone 2
# Deadline: 20 May 2024

# Step Two
# - Getting Device Registration to Work
# - Frontend: Modal for Registration
# - Backend: Store Device ID in DB and send Registration ID to Device
# - Node: Display Registration ID and authenticate with Backend
# Deadline 27 May 2024
