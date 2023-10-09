val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-test",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies  ++= Seq (
      "dev.zio" %% "zio" % "2.0.13",
      "org.scalameta" %% "munit" % "0.7.29" % Test
    )
  )
