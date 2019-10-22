# Kotlin scrips

Kotlin can be used to write typesafe scripts and execute them from terminal.
### Prerequirements
* Kotlin (which needs Java).

### Pros and cons
\+ Familiarity - no need to learn and remember different syntax (shell, python, ruby) and their edge-case behavior.
\+ Type-safety - less chance of errors.
\+ code completion - possible due to great tooling and static type analysis.
\+ Being able to reuse libs.
\- Need `Kotlin` installation (or docker container?) and not already present on the box.

### Create and run
Create a `*.kts` file with
```kt 
    import java.io.File

    val folders = File(args[0]).listFiles { file -> file.isDirectory() }
    folders?.forEach { folder -> println(folder) }
```
Run with
```sh
    kotlinc -script list_folders.kts `pwd`
```

There's a bug in version [1.3.50](https://youtrack.jetbrains.com/issue/KT-33529). Workaround using Java 
```sh
    java -jar /usr/local/Cellar/kotlin/1.3.50/libexec/lib/kotlin-compiler.jar -script ListFolders.kts `pwd`
```

### References
* [KEEP for KotlinScript](https://github.com/Kotlin/KEEP/blob/master/proposals/scripting-support.md)
* [Kscript project](https://github.com/holgerbrandl/kscript)