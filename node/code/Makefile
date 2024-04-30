APPLICATION = teamagotchi
BOARD = feather-nrf52840-sense

# This has to be the absolute path to the RIOT base directory:
RIOTBASE ?= $(CURDIR)/RIOT

# Comment this out to disable code in RIOT that does safety checking
# which is not needed in a production environment but helps in the
# development process:
DEVELHELP ?= 1

FEATURES_REQUIRED += cpp
FEATURES_REQUIRED += libstdcpp

#Hardware specific configs
USEMODULE += ili9341
USEMODULE += ztimer
USEMODULE += ztimer_msec

# As there is an 'Kconfig' we want to explicitly disable Kconfig by setting
# the variable to empty
SHOULD_RUN_KCONFIG ?=

# Include "inc" directory for the header files
INCLUDES += -I$(CURDIR)/inc

CXXEXFLAGS +=

include $(RIOTBASE)/Makefile.include

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
