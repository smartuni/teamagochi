#!/usr/bin/env python3

# Copyright (C) 2024 HAW Hamburg
#
# This file is subject to the terms and conditions of the GNU Lesser
# General Public License v2.1. See the file LICENSE in the top level
# directory for more details.

import sys
import pexpect
from testrunner import run


def testfunc(child: pexpect.spawn):
    child.expect("PASS")
    
    # Test if the module is initialized correctly
    child.expect("Example Module Init: 1")
    
    # Test if the module is started correctly
    child.expect("Starting shell loop")
    
    # Test if shell commands work correctly
    child.sendline("send_event 0")
    child.expect("💣 Received TERMINATE event, exiting...")

if __name__ == "__main__":
    sys.exit(run(testfunc))
