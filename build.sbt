import de.heikoseeberger.sbtheader.HeaderPattern

name := "f3"

scalaVersion in ThisBuild := "2.11.8"

lazy val `f3` =
  (project in file("."))
    .settings(commonSettings: _*)
    .aggregate(coreJVM, coreJS, `semantic-ui`, plotly, web, sampleDataJVM, sampleDataJS)
    .dependsOn(coreJVM, coreJS, `semantic-ui`, plotly, web, sampleDataJVM, sampleDataJS)

lazy val `core` =
  (crossProject.crossType(CrossType.Pure) in file("modules/core"))
    .enablePlugins(AutomateHeaderPlugin, BuildInfoPlugin)
    .settings(commonSettings: _*)
    .settings(buildInfoSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        "io.circe" %%% "circe-core"    % "0.6.0",
        "io.circe" %%% "circe-generic" % "0.6.0",
        "io.circe" %%% "circe-parser"  % "0.6.0"
      )
    )

lazy val coreJVM = core.jvm
lazy val coreJS  = core.js

lazy val `semantic-ui` =
  (project in file("modules/semantic-ui"))
    .enablePlugins(AutomateHeaderPlugin, ScalaJSPlugin)
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        "be.doeraene"                       %%% "scalajs-jquery" % "0.9.1",
        "com.github.japgolly.scalajs-react" %%% "core"           % "0.11.3"
      )
    )

lazy val plotly =
  (project in file("modules/plotly"))
    .enablePlugins(AutomateHeaderPlugin, ScalaJSPlugin)
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.1"
      )
    )

lazy val `web` =
  (project in file("modules/web"))
    .dependsOn(coreJS, sampleDataJS, `semantic-ui`, plotly)
    .enablePlugins(AutomateHeaderPlugin, ScalaJSPlugin)
    .settings(commonSettings: _*)
    .settings(
      persistLauncher := true,
      persistLauncher in Test := false,
      relativeSourceMaps := true,
      artifactPath in (Compile, packageJSDependencies) :=
        ((baseDirectory).value / ".." / ".." / "docs" / "javascripts" / ((moduleName in (Compile, packageJSDependencies)).value + "-jsdeps.js")),
      artifactPath in (Compile, fastOptJS) :=
        ((baseDirectory).value / ".." / ".." / "docs" / "javascripts" / ((moduleName in (Compile, fastOptJS)).value + ".js")),
      artifactPath in (Compile, fullOptJS) :=
        ((baseDirectory).value / ".." / ".." / "docs" / "javascripts" / ((moduleName in (Compile, fullOptJS)).value + ".js")),
      artifactPath in (Compile, packageScalaJSLauncher) :=
        ((baseDirectory).value / ".." / ".." / "docs" / "javascripts" / ((moduleName in (Compile, packageScalaJSLauncher)).value + "-launcher.js"))
    )
    .settings(
      fastSiteTask := { println("Generating (test) site...") },
      fastSiteTask := ((fastSiteTask) dependsOn (fastOptJS in Compile)).value,
      fullSiteTask := { println("Generating (prod) site...") },
      fullSiteTask := ((fullSiteTask) dependsOn (fullOptJS in Compile)).value
    )
    .settings(
      libraryDependencies ++= Seq(
        "me.chrons"                         %%% "diode"       % "1.1.0",
        "me.chrons"                         %%% "diode-react" % "1.1.0",
        "com.github.japgolly.scalacss"      %%% "core"        % "0.5.1",
        "com.github.japgolly.scalacss"      %%% "ext-react"   % "0.5.1",
        "com.github.japgolly.scalajs-react" %%% "core"        % "0.11.3"
      )
    )

lazy val `sample-data` =
  (crossProject.crossType(CrossType.Pure) in file("modules/sample-data"))
    .dependsOn(core)
    .enablePlugins(AutomateHeaderPlugin)
    .settings(commonSettings: _*)

lazy val sampleDataJVM = `sample-data`.jvm
lazy val sampleDataJS  = `sample-data`.js

lazy val commonSettings = Seq(
    cancelable in Global := true,
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
      "org.scalatest"          %% "scalatest"          % "3.0.1" % "test"
    ),
    scalacOptions ++= Seq(
      "-deprecation", // Emit warning and location for usages of deprecated APIs
      "-encoding",
      "UTF-8", // Specify character encoding used by source files
      "-feature", // Warn of usages of features that should be imported explicitly
      "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
      "-language:implicitConversions", // Allow definition of implicit functions called views
      "-language:higherKinds", // Allow higher-kinded types
      "-target:jvm-1.8", // Target platform for object files
      "-unchecked", // Enable additional warnings where generated code depends on assumptions
      "-Xfatal-warnings", // Fail the compilation if there are any warnings
      "-Xfuture", // Turn on future language features
      "-Xlint", // recommended additional warnings
      "-Ybackend:GenBCode", // New backend and optimizer
      "-Ydelambdafy:method", // Speed up anonymous function compilation
      "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
      "-Ywarn-dead-code", // Warn when dead code is identified
      "-Ypatmat-exhaust-depth",
      "off", // Unlimited pattern match analysis depth
      "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures
      "-Ywarn-numeric-widen", // Warn when numerics are widened
      "-Ywarn-unused-import", // Warn when imports are unused -- enabling this makes the REPL unusable
      "-Yno-imports", // Compile without importing scala.*, java.lang.*, or Predef
      "-Yno-predef" // Compile without importing Predef
      // "-Ywarn-value-discard"        // Warn when non-Unit expression results are unused
    ),
    scalacOptions in (Compile, console) ~= (_ filterNot { x =>
    x == "-Ywarn-unused-import" ||
    x == "-Yno-imports" ||
    x == "-Yno-predef"
  }),
    scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value,
    fork in run := true,
    coverageExcludedPackages := ".*BuildInfo.*",
    headers := Map(
      "scala" -> (
        HeaderPattern.cStyleBlockComment,
        headerComment
      )
    ),
    scalafmtConfig := Some(file(".scalafmt.conf"))
  ) ++ reformatOnCompileSettings

lazy val buildInfoSettings = Seq(
  buildInfoOptions += BuildInfoOption.BuildTime,
  buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
  buildInfoPackage := "f3",
  buildInfoKeys += BuildInfoKey.action("gitDescribe") {
    scala.util
      .Try(Process("git describe --always").lines.head)
      .toOption
      .getOrElse("0.1-SNAPSHOT")
  }
)

val headerComment = {
  val licenseComment =
    IO.readLines(file("LICENSE")).collect {
      case l if l.isEmpty => s" *"
      case l              => s" * $l"
    }

  ("/*" :: licenseComment ::: List(" */")).mkString("\n") + ("\n" * 2)
}

val fastSiteTask = TaskKey[Unit]("fastSite")
val fullSiteTask = TaskKey[Unit]("fullSite")
