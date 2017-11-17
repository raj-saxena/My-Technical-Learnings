**Additional sbt plugins**
=====
* *sbt-native-packager*	 - To package the project into jar -  [sbt-native-packager](https://github.com/sbt/sbt-native-packager)
* *flyway-sbt* - for DB migrations  -  [flywaydb](http://flywaydb.org/)

---

Using flyway-sbt
---
* Add the following to `plugins.sbt`
``` scala
addSbtPlugin("org.flywaydb" % "flyway-sbt" % "3.2.1")
resolvers += "Flyway" at "http://flywaydb.org/repo"
```
* Add the following to `build.sbt`
```scala
seq(flywaySettings: _*)
flywayUrl := "jdbc:mysql://localhost:3306/bargainAppDB?useEncoding=true&useUnicode=true&characterEncoding=UTF-8&character_set_server=utf8mb4"
flywayUser := "mysqlUser"
flywayPassword := "mysqlPassword"
```
* Create the schema manually in DB.
Once the plugin is loaded, run the migration with ==>

```
flywayMigrate
---

* Clean with flywayClean
