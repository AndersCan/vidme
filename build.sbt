name := """vidme"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaSource in Compile := baseDirectory.value / "app"

scalaSource in Test := baseDirectory.value / "test"

sourcesInBase := false

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23",
  "org.mongodb" %% "casbah" % "2.8.0",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "com.google.inject" % "guice" % "3.0"
)
