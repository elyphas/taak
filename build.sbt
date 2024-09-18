import sbtcrossproject.CrossType
import sbtcrossproject.CrossPlugin.autoImport.crossProject

val http4s = "0.23.24"
val http4sDom = "0.2.11"

reStart / javaOptions += "-Xmx2g"

/**Resolving a snapshot version. It's going to be slow unless you use `updateOptions := updateOptions.value.withLatestSnapshots(false)` options* */
updateOptions := updateOptions.value.withLatestSnapshots(false)

lazy val outwatch_components = RootProject(file("../outwatch_components/"))

lazy val server = (project in file("server"))
  .settings(commonSettings)
  .settings(
    scalacOptions ++= Seq("-language:postfixOps"),
    scalaJSProjects := Seq(client),
    Assets / pipelineStages := Seq(scalaJSPipeline),
    //LessKeys.compress in Assets := true,
    // triggers scalaJSPipeline when using compile or continuous compilation
    Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value,
    Compile / run / fork := true,
    //pipelineStages := Seq(digest, gzip),
    libraryDependencies ++= Seq (

      "org.typelevel" %% "cats-effect-testing-scalatest" % "1.5.0" % Test,

      //"org.scalatest" %% "scalatest" % "3.2.2" % Test,

      "org.http4s" %% "http4s-ember-server" % http4s,
      "org.http4s" %% "http4s-ember-client" % http4s,
      "org.http4s" %% "http4s-dsl" % http4s,
      "io.circe" %% "circe-literal" % "0.14.6",
      "com.vmunier" %% "scalajs-scripts" % "1.2.0",   //"1.1.4",

      "org.slf4j" % "slf4j-api" % "2.0.13",
      "org.slf4j" % "slf4j-simple" % "2.0.13" % Test,
      "org.apache.logging.log4j" % "log4j-core" % "2.18.0",

      "com.outr" %% "scarango-driver" % "3.20.0",
      //"org.slf4j" % "slf4j-log4j12" % "2.0.13" % Test pomOnly(),
      "com.norbitltd" %% "spoiwo" % "2.2.1",
      //"fr.opensagres.xdocreport" % "fr.opensagres.xdocreport.converter.docx.xwpf" % "2.0.6",

      //"io.github.cquiroz" %% "scala-java-time" % "2.6.0",

      "com.typesafe" % "config" % "1.4.3",

    ),
    Assets / WebKeys.packagePrefix := "public/",

    Runtime / managedClasspath += (Assets / packageBin).value,

    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    Test / javaOptions  += s"-Dconfig.file=${sourceDirectory.value}/test/resources/application.test.conf",  // When running tests, we use this configuration
    Test / fork := true,     // We need to fork a JVM process when testing so the Java options above are applied
  )
  .enablePlugins(SbtWeb, WebScalaJSBundlerPlugin  /*, JavaAppPackaging*/)
  .dependsOn(sharedJvm)

lazy val client = (project in file("client"))
  .settings(commonSettings)
  .settings(
    webpack / version := "5.75.0",

    Test / requireJsDomEnv := true,
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)), // configure Scala.js to emit a JavaScript module instead of a top-level script


    fastOptJS / webpackConfigFile := Some(baseDirectory.value / "my.custom.webpack.config.js"), // siguiendo ejemplo de outwatch.

    fullOptJS / webpackEmitSourceMaps := true,

    //fastOptJS / webpackBundlingMode   := BundlingMode.LibraryOnly(),

    useYarn := true,
    yarnExtraArgs                     += "--prefer-offline",

    installJsdom / version := "22.1.0",

    Test / requireJsDomEnv := true,

    scalacOptions ++= Seq(
                  "-language:postfixOps"
    ),
    /**       The baseDirectory is "tsoolik/client/"      */

    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-fake-insecure-java-securerandom" % "1.0.0",

      "org.scalatest" %%% "scalatest" % "3.2.2", // % "test",

      "org.slf4j" % "slf4j-api" % "2.0.9",
      "org.slf4j" % "slf4j-simple" % "2.0.9" % Test,

      "org.http4s" %%% "http4s-dom" % "0.2.11",

      "io.github.outwatch.outwatch" %%% "outwatch" % "1.0.0", //If you want to use utilities for Store, WebSocket or Http, add the following:
      "com.github.cornerman" %%% "colibri" % "0.8.4",
      "io.github.cquiroz" %%% "scala-java-time" % "2.6.0",
      "com.chuusai" %% "shapeless" % "2.3.12",
      "io.github.cquiroz" %%% "scala-java-time" % "2.6.0",
      "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.6.0",
    ),
    zonesFilter := {(z: String) => z == "America/Mexico_City"},
    //emitSourceMaps := false,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
  )
  .enablePlugins( ScalaJSPlugin,/* ScalaJSWeb,*/ ScalaJSBundlerPlugin, TzdbPlugin)
  .dependsOn(sharedJs)
  .dependsOn(outwatch_components)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType ( CrossType.Pure )
  .in(file ( "shared" ) )
  .settings ( commonSettings )
  .settings (
    //resolvers += "jitpack" at "https://jitpack.io",
    scalacOptions ++= Seq(
      //"-Ypartial-unification",
      "-language:postfixOps",
      "-encoding",
      "UTF-8",
      "-unchecked",
      "-deprecation",
      "-explaintypes",
      "-feature",
      "-language:_",
      //"-Xlint",
      "-Xlint:adapted-args",
      "-Xlog-implicits",
      //"-Wextra-implicit",
      "-Xlint:infer-any",
      //"-Wvalue-discard",
      //"-Xlint:nullary-override",
      "-Xlint:nullary-unit"
    ),
    ///scalacOptions in ThisBuild ++= Seq("-Ypartial-unification"),
    libraryDependencies ++= Seq (
      "org.scalatest" %%% "scalatest" % "3.2.2" % Test,
      "io.circe" %%% "circe-generic" % "0.14.6",
      "org.http4s" %%% "http4s-circe" % http4s,
      "org.typelevel" %%% "cats-core" % "2.9.0",
      "io.github.cquiroz" %%% "scala-java-time" % "2.6.0",
      "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.6.0",
      //"com.github.marklister" % "base64" % "0.2.5",
      //"org.slf4j" % "slf4j-api" % "2.0.13",
      //"org.slf4j" % "slf4j-simple" % "2.0.13",
    )//,
    //zonesFilter := {(z: String) => z == "America/Mexico_City"},
  )
  .jsConfigure(_ enablePlugins ScalaJSWeb)
  .jsConfigure(_ enablePlugins ScalaJSBundlerPlugin)
  .jsConfigure(_ enablePlugins SbtWeb)
  .jsConfigure(_ enablePlugins TzdbPlugin)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
    scalaVersion := "2.13.13",
    organization := "taak" /** almacenes de  unidades medicas.*/      //https://es.freelang.net/enlinea/maya.php?lg=es
)

// loads the server project at sbt startup
onLoad in Global := ( onLoad in Global ).value andThen { s: State => "project server" :: s }

