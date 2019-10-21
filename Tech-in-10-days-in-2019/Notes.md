#### Service mesh
- Abstraction of common functionality like service discovery, encryption, load-balancing, fault-tolerance (resilience & retry), telemetry.
    - Netflix approach - via libraries - eg: Hysterix
    - sidecar approach - via sidecars  - eg: envoy // Better as it is agnostic of the service technology and language. Better control.
- 2 planes
    - data plane - the core functionality of the  network and it's behavior. This is where data flows.
    - control plane - configuration to control data plane behavior. Administration.
- https://thenewstack.io/history-service-mesh/
- https://www.nginx.com/blog/what-is-a-service-mesh/

___
#### Make and MakeFiles
**Make** is a tool which controls the generation of executables and other non-source files of a program from the program's source files. 
Make gets its knowledge of how to build your program from a file called the **makefile**, which lists each of the non-source files and how to compute it from other files. 
Syntax:
> target:  dependencies ...
> <tab>    commands
>           ...

A target might be a binary file that depends on prerequisites (source files). On the other hand, a prerequisite can also be a target that depends on other dependencies

References:
* GNU doc - https://www.gnu.org/software/make/
* https://opensource.com/article/18/8/what-how-makefile
* Cheatsheet - https://devhints.io/makefile
* Why is Make not used to compile Java - https://stackoverflow.com/questions/2209827/why-is-no-one-using-make-for-java