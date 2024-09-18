addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.16.0")

//addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.21.1")
addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.21.1")

addSbtPlugin("io.spray"                  % "sbt-revolver"              % "0.10.0") // fast development turnaround when using sbt ~reStart

//addSbtPlugin("com.eed3si9n"              % "sbt-assembly"              % "0.15.0")

//addSbtPlugin("com.typesafe.sbt"          % "sbt-native-packager"       % "1.7.5")   //"1.3.5")

addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "1.1.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.3")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.2")

addSbtPlugin("io.github.cquiroz" % "sbt-tzdb" % "4.2.0")

//addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.4.2")