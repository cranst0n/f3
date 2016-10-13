// Comment to get more information during initialization
logLevel := Level.Warn

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.3")
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "0.4.10")
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "1.6.0")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.2.0")
addSbtPlugin("com.scalapenos" % "sbt-prompt" % "1.0.0")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.13")
