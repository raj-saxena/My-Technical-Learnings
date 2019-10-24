# Java 9 to 13 - Top features

Programming tools, frameworks are becoming more and more developer friendly and offer better and modern features to boost developer productivity.
Java was earlier infamous for having slow release train. However, keeping up with times, Java has moved to a cadence of releasing new features with a version upgrade every 6 months. 
Since then there have been a lot of cool features and tools that have been added. This is just a quick summary of the them starting from Java 9 to Java 13.

Please note, the features that I talk about here are the ones that I believe either add cool features or increase developer productivity the most. This is not an exhaustive list. 

## Java 9
**Module system**: Helps in modularisation of large apps. This helps to limit exposure of classes that are public in the module vs the true public api of the module. 
Explicitly define dependencies and exports in `module-info.java`. Eg:
```
    module chef {
      exports com.tbst.recipe;
    
      requires kitchen;
    }
```
Here, the module `chef` depends on module `kitchen` and exports module `recipe`.

**Jlink**: Having explicit dependencies and modularized JDK means that we can easily come up with the entire dependency graph of the application. This, in turn, enables having a minimal runtime environment containing only the necessary to run the application. This can help to reduce the overall size of the executable jar.


**jshell**: An interactive REPL (Read-Eval-Print-Loop) for quickly playing with java code. Just say `jshell` on the terminal after installing JDK 9+.

**Collection factory methods**: Earlier, one had to initialize an implementation of a collection (Map, Set, List) first and then add objects to it. Finally, it's possible to create the ***immutable*** collections with static factory methods of the signature `<Collection>.of()`. This is achieved by having a bunch of static methods in each of the respective interfaces. Eg:
```java
    // Creates an immutable list
    List<String> abcs = List.of("A", "B", "C");

    // Creates an immutable set
    Set<String> xyzs = Set.of("X", "Y", "Z");

    // Creates an immutable Map
    Map<String, String> mappings = Map.of("key1", "value1", "key2", "value2");
```

**Other features**
    - Stream API gets more functions like `dropWhile`, `takeWhile`, `ofNullable`.
    - Private interface methods to write clean code and keep things DRY when using default methods in interfaces.
    - new HTTP2 API that supports streams and server based pushes.
    
____
## Java 10
**Local-Variable Type Inference**: This enables us to write more modern Kotlin/Scala/Typescript like syntax where you don't have to explicitly declare the variable type without compromising type safety. Here, the compiler is able to figure out the type because of the type of the value on the right hand side in case of assignments. Eg:
```java
    var list = new ArrayList<String>();  // infers ArrayList<String>
    var stream = list.stream();          // infers Stream<String>
```

In cases where the compiler cannot infer the value or it's ambiguous, you need to explicitly declare it. More details [here](http://openjdk.java.net/jeps/286)

**Parallel Full GC for G1**: Improves G1 worst-case latencies by making the full GC parallel.

**Experimental Java-Based JIT Compiler**: Enables the Java-based JIT compiler, Graal, to be used as an experimental JIT compiler on the Linux/x64 platform.

**Heap Allocation on Alternative Memory Devices**: Enables the HotSpot VM to allocate the Java object heap on an alternative memory device, such as an NV-DIMM, specified by the user.

**Root certificates in JDK**: Open-source the root certificates in Oracle's Java SE Root CA program in order to make OpenJDK builds more attractive to developers, and to reduce the differences between those builds and Oracle JDK builds.

____
## Java 11

**New String methods**: String class gets new methods like `isBlank()`, `lines()`, `repeat(int)`, unicode aware `strip()`, `stripLeading()` and `stripTrailing()`.

**New File Methods**: `writeString()`, `readString()` and `isSameFile()`.

**Local-Variable Syntax for Lambda Parameters**: Allow var to be used when declaring the formal parameters of implicitly typed lambda expressions. 
This is introduced to have uniformity with use of `var` for local variables. Eg:

```java
    (var x, var y) -> x.process(y)   // implicit typed lambda expression

// One benefit of uniformity is that modifiers, notably annotations, can be applied to local variables and lambda formals without losing brevity:

(@Nonnull var x, @Nullable var y) -> x.process(y)
```
**JEP 328: Flight Recorder**: JFR is a profiling tool used to gather diagnostics and profiling data from a running Java application.
Its performance overhead is negligible and that’s usually below 1%. Hence it can be used in production applications.

**Remove the Java EE and CORBA Modules**:  Following packages are removed: `java.xml.ws`, ` java.xml.bind`, ` java.activation`, ` java.xml.ws.annotation`, ` java.corba`, ` java.transaction`, ` java.se.ee`, ` jdk.xml.ws`, ` jdk.xml.bind`

____
## Java 12


____
## Java 13


___
### References:
* Java 9 
    - https://www.pluralsight.com/blog/software-development/java-9-new-features
* Java 10
    - https://www.techworld.com/developers/java-10-features-whats-new-in-java-10-3680317/
    - https://dzone.com/articles/java-10-new-features-and-enhancements
* Java 11
    - https://www.geeksforgeeks.org/java-11-features-and-comparison/
    - https://www.journaldev.com/24601/java-11-features
* Java 12
    - https://www.journaldev.com/28666/java-12-features
    - https://stackify.com/java-12-new-features-and-enhancements-developers-should-know/