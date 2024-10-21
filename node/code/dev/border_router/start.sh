#!/bin/bash 
SCRIPT=$(readlink -f "$0")
SCRIPTPATH=$(dirname "$SCRIPT")

echo $SCRIPTPATH

export RIOTTOOLS="$SCRIPTPATH/../../RIOT/dist/tools"

$RIOTTOOLS/usb-cdc-ecm/start_network.sh  2001:db8::/64

ip link add d0 type dummy
ip link set up d0
ip addr add 2001:db8:1::1/64 dev d0
