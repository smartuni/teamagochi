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


if __name__ == "__main__":
    sys.exit(run(testfunc))
