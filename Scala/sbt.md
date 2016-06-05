* Activator is a wrapper over sbt.
* Start with downloading scala-minimal-seed from Lightbend.

---
### SBT commands =>
* `clean` => deletes files.
* `complie` => compiles source files to create .class files.
* `test` => cleans and runs tests.
* `package` => creates jar artifact.
* `publish-local` => adds the jar to local .ivy cache.

---

### SBT-native-packager
Sbt-native-packager is an AutoPlugin. Add it to your `plugins.sbt`
```scala
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "x.y.z")
```
Addtionally, enable the plugin in `build.sbt`
```scala
enablePlugins(JavaAppPackaging)
```

#### Creating a package
* `universal:packageBin` - Generates a universal zip file
* `universal:packageZipTarball` - Generates a universal tgz file
* `debian:packageBin` - Generates a deb
* `docker:publishLocal` - Builds a Docker image using the local Docker server
* `rpm:packageBin` - Generates an rpm
* `universal:packageOsxDmg` - Generates a DMG file with the same contents as the universal zip/tgz.
* `windows:packageBin` - Generates an MSI


