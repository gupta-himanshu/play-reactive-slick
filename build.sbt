name := """play-reactive-slick"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.typesafe.slick" %% "slick"      % "3.0.0-RC1",
  "org.slf4j"           % "slf4j-nop"  % "1.6.4",
  "postgresql"          % "postgresql" % "9.1-901.jdbc4"
)
