#!/bin/bash 
SCRIPT=$(readlink -f "$0")
SCRIPTPATH=$(dirname "$SCRIPT")

echo $SCRIPTPATH

export RIOTTOOLS="$SCRIPTPATH/../RIOT/dist/tools"

$RIOTTOOLS/usb-cdc-ecm/start_network.sh  2001:db8::/64