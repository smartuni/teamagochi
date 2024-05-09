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

# Enable bootstrap server based on NGINX_ENABLE environment variable.
nginx_conf=/etc/supervisor/conf.d/nginx.conf

if [ "$NGINX_ENABLE" = "true" ]; then
  if [ -f "${nginx_conf}.disabled" ]; then
    mv "${nginx_conf}.disabled" "${nginx_conf}"
    echo "Enabled bootstrap server."
  fi
else
  if [ -f "${nginx_conf}" ]; then
    mv "${nginx_conf}" "${nginx_conf}.disabled"
    echo "Disabled bootstrap server."
  fi
fi

exec "$@"