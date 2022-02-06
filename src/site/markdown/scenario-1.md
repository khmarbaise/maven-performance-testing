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

The [log file for the full run of the measurement (ca. 1.9 MiB in size):](./run.log)

In each entry you have two links. The first one shows the result in a table.
The link `JSON File` links to the results json file which is generated 
by `hyperfine` which contains the raw measured date.

Number of modules: 10:

* [8.0.302-open](./results-8.0.302-open-10.html) [JSON File](./results-8.0.302-open-10.json)
* [11.0.12-open](./results-11.0.12-open-10.html) [JSON File](./results-11.0.12-open-10.json)
* [17.0.1-open](./results-17.0.1-open-10.html) [JSON File](./results-17.0.1-open-10.json)
* [18.ea.33-open](./results-18.ea.33-open-10.html) [JSON File](./results-18.ea.33-open-10.json)

Number of modules: 20:

* [8.0.302-open](./results-8.0.302-open-20.html) [JSON File](./results-8.0.302-open-20.json)
* [11.0.12-open](./results-11.0.12-open-20.html) [JSON File](./results-11.0.12-open-20.json)
* [17.0.1-open](./results-17.0.1-open-20.html) [JSON File](./results-17.0.1-open-20.json)
* [18.ea.33-open](./results-18.ea.33-open-20.html) [JSON File](./results-18.ea.33-open-20.json)

Number of modules: 50:

* [8.0.302-open](./results-8.0.302-open-50.html) [JSON File](./results-8.0.302-open-50.json)
* [11.0.12-open](./results-11.0.12-open-50.html) [JSON File](./results-11.0.12-open-50.json)
* [17.0.1-open](./results-17.0.1-open-50.html) [JSON File](./results-17.0.1-open-50.json)
* [18.ea.33-open](./results-18.ea.33-open-50.html) [JSON File](./results-18.ea.33-open-50.json)

Number of modules: 100:

* [8.0.302-open](./results-8.0.302-open-100.html) [JSON File](./results-8.0.302-open-100.json)
* [11.0.12-open](./results-11.0.12-open-100.html) [JSON File](./results-11.0.12-open-100.json)
* [17.0.1-open](./results-17.0.1-open-100.html) [JSON File](./results-17.0.1-open-100.json)
* [18.ea.33-open](./results-18.ea.33-open-100.html) [JSON File](./results-18.ea.33-open-100.json)

Number of modules: 200:

* [8.0.302-open](./results-8.0.302-open-200.html) [JSON File](./results-8.0.302-open-200.json)
* [11.0.12-open](./results-11.0.12-open-200.html) [JSON File](./results-11.0.12-open-200.json)
* [17.0.1-open](./results-17.0.1-open-200.html) [JSON File](./results-17.0.1-open-200.json)
* [18.ea.33-open](./results-18.ea.33-open-200.html) [JSON File](./results-18.ea.33-open-200.json)

Number of modules: 500:

* [8.0.302-open](./results-8.0.302-open-500.html) [JSON File](./results-8.0.302-open-500.json)
* [11.0.12-open](./results-11.0.12-open-500.html) [JSON File](./results-11.0.12-open-500.json)
* [17.0.1-open](./results-17.0.1-open-500.html) [JSON File](./results-17.0.1-open-500.json)
* [18.ea.33-open](./results-18.ea.33-open-500.html) [JSON File](./results-18.ea.33-open-500.json)

Number of modules: 1000:

* [8.0.302-open](./results-8.0.302-open-1000.html) [JSON File](./results-8.0.302-open-1000.json)
* [11.0.12-open](./results-11.0.12-open-1000.html) [JSON File](./results-11.0.12-open-1000.json)
* [17.0.1-open](./results-17.0.1-open-1000.html) [JSON File](./results-17.0.1-open-1000.json)
* [18.ea.33-open](./results-18.ea.33-open-1000.html) [JSON File](./results-18.ea.33-open-1000.json)

Number of modules: 2000:

* [8.0.302-open](./results-8.0.302-open-2000.html) [JSON File](./results-8.0.302-open-2000.json)
* [11.0.12-open](./results-11.0.12-open-2000.html) [JSON File](./results-11.0.12-open-2000.json)
* [17.0.1-open](./results-17.0.1-open-2000.html) [JSON File](./results-17.0.1-open-2000.json)
* [18.ea.33-open](./results-18.ea.33-open-2000.html) [JSON File](./results-18.ea.33-open-2000.json)

Number of modules: 2200:

* [8.0.302-open](./results-8.0.302-open-2200.html) [JSON File](./results-8.0.302-open-2200.json)
* [11.0.12-open](./results-11.0.12-open-2200.html) [JSON File](./results-11.0.12-open-2200.json)
* [17.0.1-open](./results-17.0.1-open-2200.html) [JSON File](./results-17.0.1-open-2200.json)
* [18.ea.33-open](./results-18.ea.33-open-2200.html) [JSON File](./results-18.ea.33-open-2200.json)

Number of modules: 2500:

* [8.0.302-open](./results-8.0.302-open-2500.html) [JSON File](./results-8.0.302-open-2500.json)
* [11.0.12-open](./results-11.0.12-open-2500.html) [JSON File](./results-11.0.12-open-2500.json)
* [17.0.1-open](./results-17.0.1-open-2500.html) [JSON File](./results-17.0.1-open-2500.json)
* [18.ea.33-open](./results-18.ea.33-open-2500.html) [JSON File](./results-18.ea.33-open-2500.json)

Number of modules: 3000:

* [8.0.302-open](./results-8.0.302-open-3000.html) [JSON File](./results-8.0.302-open-3000.json)
* [11.0.12-open](./results-11.0.12-open-3000.html) [JSON File](./results-11.0.12-open-3000.json)
* [17.0.1-open](./results-17.0.1-open-3000.html) [JSON File](./results-17.0.1-open-3000.json)
* [18.ea.33-open](./results-18.ea.33-open-3000.html) [JSON File](./results-18.ea.33-open-3000.json)

Number of modules: 4000:

* [8.0.302-open](./results-8.0.302-open-4000.html) [JSON File](./results-8.0.302-open-4000.json)
* [11.0.12-open](./results-11.0.12-open-4000.html) [JSON File](./results-11.0.12-open-4000.json)
* [17.0.1-open](./results-17.0.1-open-4000.html) [JSON File](./results-17.0.1-open-4000.json)
* [18.ea.33-open](./results-18.ea.33-open-4000.html) [JSON File](./results-18.ea.33-open-4000.json)

Number of modules: 5000:

* [8.0.302-open](./results-8.0.302-open-5000.html) [JSON File](./results-8.0.302-open-5000.json)
* [11.0.12-open](./results-11.0.12-open-5000.html) [JSON File](./results-11.0.12-open-5000.json)
* [17.0.1-open](./results-17.0.1-open-5000.html) [JSON File](./results-17.0.1-open-5000.json)
* [18.ea.33-open](./results-18.ea.33-open-5000.html) [JSON File](./results-18.ea.33-open-5000.json)

Number of modules: 10000:

* [8.0.302-open](./results-8.0.302-open-10000.html) [JSON File](./results-8.0.302-open-10000.json)
* [11.0.12-open](./results-11.0.12-open-10000.html) [JSON File](./results-11.0.12-open-10000.json)
* [17.0.1-open](./results-17.0.1-open-10000.html) [JSON File](./results-17.0.1-open-10000.json)
* [18.ea.33-open](./results-18.ea.33-open-10000.html) [JSON File](./results-18.ea.33-open-10000.json)

