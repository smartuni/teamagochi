APPLICATION = teamagotchi
BOARD = feather-nrf52840-sense

# This has to be the absolute path to the RIOT base directory:
RIOTBASE ?= $(CURDIR)/RIOT

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
#USEMODULE += external_module

# External modules
USEMODULE += cpp11-compat
USEMODULE += ztimer
USEMODULE += ztimer_msec
USEMODULE += shell
USEMODULE += xtimer

#USEMODULE += lwm2m_handler
USEMODULE += display_handler
USEMODULE += io_handler

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


CFLAGS += -DDEBUG_ASSERT_VERBOSE
SERVER_URI = '"coap://[2001:db8:1::1]"'
# Configure server via CFLAGS only if not done via Kconfig
ifndef CONFIG_LWM2M_SERVER_URI
  CFLAGS += -DCONFIG_LWM2M_SERVER_URI=$(SERVER_URI)
endif

CFLAGS += -DDISPLAY_STACKSIZE=2*1024

# SDL requires more stack
ifneq (,$(filter native native64,$(BOARD)))
  CFLAGS += -DTHREAD_STACKSIZE_MAIN=64*1024
else ifneq (,$(filter esp%,$(CPU_FAM)))
  CFLAGS += -DTHREAD_STACKSIZE_MAIN=4*1024
else
  CFLAGS += -DTHREAD_STACKSIZE_MAIN=2*1024
endif
