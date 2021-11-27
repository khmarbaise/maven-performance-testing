# Performance Scenario 1

This section will define a scenario which contains simple multi module
builds. Those contain only a single level of depth. The basic structure
is like the following:

```
 +-- root
      +-- mod-1
      +-- mod-2
      +-- mod-..
```
The different setups only change the number of modules from 10 upto 10.000 
modules.

 * [JDK 8](./results-JDK8.html)
 * [JDK 11](./results-JDK11.html)
 * [JDK 17](./results-JDK17.html)