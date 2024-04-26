#!/bin/sh
set -e

# Enable bootstrap server based on LESHAN_ENABLE_BS environment variable.
leshan_bs_conf=/etc/supervisor/conf.d/leshan-bs.conf
leshab_bs_jar=/leshan/leshan-bsserver-demo.jar

if [ "$LESHAN_ENABLE_BS" = "true" ]; then
  if [ -f "${leshan_bs_conf}.disabled" ] && [ -f "$leshab_bs_jar" ]; then
    mv "${leshan_bs_conf}.disabled" "${leshan_bs_conf}"
    echo "Enabled bootstrap server."
  fi
else
  if [ -f "${leshan_bs_conf}" ]; then
    mv "${leshan_bs_conf}" "${leshan_bs_conf}.disabled"
    echo "Disabled bootstrap server."
  fi
fi

exec "$@"