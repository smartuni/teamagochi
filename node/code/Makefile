APPLICATION = teamagotchi
BOARD ?= feather-nrf52840-sense

# This has to be the absolute path to the RIOT base directory:
RIOTBASE ?= $(CURDIR)/RIOT

# Comment this out to disable code in RIOT that does safety checking
# which is not needed in a production environment but helps in the
# development process:
DEVELHELP ?= 1

# Internal Modules
# Teamagotchi MODULES
USEMODULE += fsm
USEMODULE += io_handler
USEMODULE += display_handler
USEMODULE += lwm2m_handler
EXTERNAL_MODULE_DIRS += modules

# External modules
# USEMODULE += cpp11-compat
USEMODULE += ztimer
USEMODULE += ztimer_msec
USEMODULE += shell
USEMODULE += ps
# As there is an 'Kconfig' we want to explicitly disable Kconfig by setting
# the variable to empty
#SHOULD_RUN_KCONFIG ?=

# Include "inc" directory for the header files
INCLUDES += -I$(CURDIR)/inc

# Set the C standard to C17 [C23 (even experimental) is not yet supported]
# and enable all warnings
CFLAGS += -std=c17 -Wall -Wextra

BOARD_BLACKLIST += native64

## DEBUG
CFLAGS += -DDEBUG_ASSERT_VERBOSE
FEATURES_BLACKLIST +=  tinyusb

##### LWM2M #####
LWM2M_SERVER_SHORT_ID ?= 1 
CFLAGS += -DEVENT_THREAD_MEDIUM_STACKSIZE='(3*1024)'
# Uncomment to enable Wakaama debug log
#CFLAGS += -DCONFIG_LWM2M_WITH_LOGS=1
# Specific the server URI  address (NOTE: Domain names not supported yet)
# LWM2M_SERVER_URI ?= '"coap://[2001:db8:1::1]:5683"'
LWM2M_SERVER_URI ?= '"coap://[fd00:dead:beef::1]:5683"'
# Configure via CFLAGS only if not done via Kconfig
ifndef CONFIG_LWM2M_SERVER_URI
  CFLAGS += -DCONFIG_LWM2M_SERVER_URI=$(LWM2M_SERVER_URI)
  CFLAGS += -DCONFIG_LWM2M_SERVER_SHORT_ID=$(LWM2M_SERVER_SHORT_ID)
  CFLAGS += -DCONFIG_DTLS_PEER_MAX=2
  CFLAGS += -DCONFIG_MAX_BUF=1024
  # Uncomment to enable Wakaama debug log
  
endif
##### /LWM2M #####


include $(RIOTBASE)/Makefile.include

CFLAGS += -DDISPLAY_STACKSIZE=2*1024

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

