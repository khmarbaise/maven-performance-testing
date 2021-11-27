# Performance Scenario 1

This scenario contains simple multi module builds. This contains only single
level structures. The basic structure is like the following:

```
 +-- root
      +-- mod-1
      +-- mod-2
      +-- mod-..
```
The above setup will be run with different number of modules from 10 upto 10.000.

This scenario only executes a simple: `mvn clean` on each of the build
to measure the initial project identification etc.

Number of modules: 10

 * [JDK 8](./results-JDK8-10.html)
 * [JDK 11](./results-JDK11-10.html)
 * [JDK 17](./results-JDK17-10.html)
 
Number of modules: 20

 * [JDK 8](./results-JDK8-20.html)
 * [JDK 11](./results-JDK11-20.html)
 * [JDK 17](./results-JDK17-20.html)

Number of modules: 30

* [JDK 8](./results-JDK8-30.html)
* [JDK 11](./results-JDK11-30.html)
* [JDK 17](./results-JDK17-30.html)

