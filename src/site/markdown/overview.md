# Overview

The basic idea is to create different scenario for building project to identify
particular areas of Apache Maven to see the performance for those areas from a
user perspective.

The setup of the testing machine is currently being done via Ansible using the
`setup-performance-system.yml` playbook.

[Scenario 1](./scenario-1.html)

Test description.

TODO: More detailed description how we test.

Currently, we are using [hyperfine](https://github.com/sharkdp/hyperfine) to do
the performance measuring for calling Apache Maven a number of times.

Initially we run a warmup 5 times to make sure everything is downloaded (You
know the thing about downloading the whole internet) etc.

Each testcase will be run with three different JDK versions JDK8, JDK11 and
JDK17. 
