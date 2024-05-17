# name of your application
APPLICATION = wakaama

INCLUDES += -I$(CURDIR)
BOARD = feather-nrf52840-sense
#Bootstrap
#CFLAGS += -DCONFIG_LWM2M_BOOTSTRAP=1

#Wakamaa logs
#CFLAGS += -DCONFIG_LWM2M_WITH_LOGS=1

SERVER_URI = '"coap://[2001:db8:1::1]"'

RIOTBASE ?= $(CURDIR)/../../RIOT

# Include packages that pull up and auto-init the link layer.
# NOTE: 6LoWPAN will be included if IEEE802.15.4 devices are present
USEMODULE += netdev_default
USEMODULE += auto_init_gnrc_netif
# Specify the mandatory networking modules
USEMODULE += gnrc_ipv6_router_default
# Additional networking modules that can be dropped if not needed
USEMODULE += gnrc_icmpv6_echo
# Add also the shell, some shell commands
USEMODULE += shell
USEMODULE += shell_cmds_default
USEMODULE += ps

# Comment this out to disable code in RIOT that does safety checking
# which is not needed in a production environment but helps in the
# development process:
DEVELHELP ?= 1

# add DTLS support
#USEMODULE += wakaama_client_dtls

# Specify sock DTLS implementation
#USEPKG += tinydtls
# tinydtls needs crypto secure PRNG
#USEMODULE += prng_sha1prng

# Specific the server URI  address (NOTE: Domain names not supported yet)
#SERVER_URI = "coap://2001:db8:1::1"
LWM2M_SERVER_SHORT_ID ?= 1

CFLAGS += -DEVENT_THREAD_MEDIUM_STACKSIZE='(3*1024)'

# NOTE: Add the package for wakaama
USEPKG += wakaama

# NOTE: Add the package for the timer
USEMODULE += xtimer

#DTLS_MAX_BUF=1024

include $(RIOTBASE)/Makefile.include

# Configure server via CFLAGS only if not done via Kconfig
ifndef CONFIG_LWM2M_SERVER_URI
  CFLAGS += -DCONFIG_LWM2M_SERVER_URI=$(SERVER_URI)
endif
