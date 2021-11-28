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

Number of modules: 50

* [JDK 8](./results-JDK8-50.html)
* [JDK 11](./results-JDK11-50.html)
* [JDK 17](./results-JDK17-50.html)

Number of modules: 100

* [JDK 8](./results-JDK8-100.html)
* [JDK 11](./results-JDK11-100.html)
* [JDK 17](./results-JDK17-100.html)

Number of modules: 200

* [JDK 8](./results-JDK8-200.html)
* [JDK 11](./results-JDK11-200.html)
* [JDK 17](./results-JDK17-200.html)

Number of modules: 500

* [JDK 8](./results-JDK8-500.html)
* [JDK 11](./results-JDK11-500.html)
* [JDK 17](./results-JDK17-500.html)

Number of modules: 1000

* [JDK 8](./results-JDK8-1000.html)
* [JDK 11](./results-JDK11-1000.html)
* [JDK 17](./results-JDK17-1000.html)

Number of modules: 2000

* [JDK 8](./results-JDK8-2000.html)
* [JDK 11](./results-JDK11-2000.html)
* [JDK 17](./results-JDK17-2000.html)

Number of modules: 5000

* [JDK 8](./results-JDK8-5000.html)
* [JDK 11](./results-JDK11-5000.html)
* [JDK 17](./results-JDK17-5000.html)


Number of modules: 10000

* [JDK 8](./results-JDK8-10000.html)
* [JDK 11](./results-JDK11-10000.html)
* [JDK 17](./results-JDK17-10000.html)


