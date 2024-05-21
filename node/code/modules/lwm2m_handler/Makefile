include $(RIOTBASE)/Makefile.base

#Wakamaa logs
#CFLAGS += -DCONFIG_LWM2M_WITH_LOGS=1

SERVER_URI = '"coap://[2001:db8:1::1]"'


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


# Uncomment to enable Wakaama debug log
#CFLAGS += -DCONFIG_LWM2M_WITH_LOGS=1

# Configure server via CFLAGS only if not done via Kconfig
ifndef CONFIG_LWM2M_SERVER_URI
  CFLAGS += -DCONFIG_LWM2M_SERVER_URI=$(SERVER_URI)
endif
