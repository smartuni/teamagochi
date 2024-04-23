- Build the border router
- flash the border router
- connect the dongle to the VM or linux machine
- add to one of the interfaces of the machine this ip
```bash
sudo ip -6 addr add 2001:db8:1::1/64 dev <interface>
```

- start start.sh there with whole project cloned as root