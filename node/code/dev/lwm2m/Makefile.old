INCLUDES += -I$(CURDIR)
BOARD ?= feather-nrf52840-sense
#Bootstrap
CFLAGS += -DCONFIG_LWM2M_BOOTSTRAP=1

#Wakamaa logs
#CFLAGS += -DCONFIG_LWM2M_WITH_LOGS=1

SERVER_URI = '"coap://[2001:db8:1::1]"'

RIOTBASE ?= $(CURDIR)/../../RIOT
include $(RIOTBASE)/examples/lwm2m/Makefile