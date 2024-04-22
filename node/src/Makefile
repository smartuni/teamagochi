APPLICATION = teamagotchi
BOARD = feather-nrf52840-sense

# This has to be the absolute path to the RIOT base directory:
RIOTBASE ?= $(CURDIR)/RIOT

# Comment this out to disable code in RIOT that does safety checking
# which is not needed in a production environment but helps in the
# development process:
DEVELHELP ?= 1

#include ../Makefile.drivers_common

#Hardware specific configs
USEMODULE += ili9341
USEMODULE += ztimer
USEMODULE += ztimer_msec

# As there is an 'Kconfig' we want to explicitly disable Kconfig by setting
# the variable to empty
SHOULD_RUN_KCONFIG ?=

# add include path for riot_logo.h
INCLUDES += -I$(RIOTBASE)/tests


INCLUDES += -I$(CURDIR)/inc

include $(RIOTBASE)/Makefile.include