file://<WORKSPACE>/build.sbt
### file%3A%2F%2F%2Fhome%2Felyphas%2FPrjs%2Ftaak%2Fbuild.sbt:44: error: ) expected but string constant found
        "org.http4s" %% "http4s-ember-server" % http4s,
        ^

occurred in the presentation compiler.

action parameters:
uri: file://<WORKSPACE>/build.sbt
text:
```scala
import _root_.scala.xml.{TopScope=>$scope}
import _root_.sbt._
import _root_.sbt.Keys._
import _root_.sbt.nio.Keys._
import _root_.sbt.ScriptedPlugin.autoImport._, _root_.sbt.plugins.JUnitXmlReportPlugin.autoImport._, _root_.sbt.plugins.MiniDependencyTreePlugin.autoImport._, _root_.bloop.integrations.sbt.BloopPlugin.autoImport._, _root_.org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._, _root_.scalajsbundler.sbtplugin.WebScalaJSBundlerPlugin.autoImport._, _root_.spray.revolver.RevolverPlugin.autoImport._, _root_.play.twirl.sbt.SbtTwirl.autoImport._, _root_.scalajscrossproject.ScalaJSCrossPlugin.autoImport._, _root_.com.typesafe.sbt.less.SbtLess.autoImport._, _root_.com.typesafe.sbt.digest.SbtDigest.autoImport._, _root_.com.typesafe.sbt.gzip.SbtGzip.autoImport._, _root_.org.jetbrains.sbt.indices.SbtIntellijIndicesPlugin.autoImport._, _root_.org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._, _root_.scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._, _root_.webscalajs.ScalaJSWeb.autoImport._, _root_.webscalajs.WebScalaJS.autoImport._, _root_.sbtcrossproject.CrossPlugin.autoImport._, _root_.com.typesafe.sbt.jse.SbtJsEngine.autoImport._, _root_.com.typesafe.sbt.jse.SbtJsTask.autoImport._, _root_.com.typesafe.sbt.web.SbtWeb.autoImport._, _root_.org.scalajs.jsdependencies.sbtplugin.JSDependenciesPlugin.autoImport._
import _root_.sbt.plugins.IvyPlugin, _root_.sbt.plugins.JvmPlugin, _root_.sbt.plugins.CorePlugin, _root_.sbt.ScriptedPlugin, _root_.sbt.plugins.SbtPlugin, _root_.sbt.plugins.SemanticdbPlugin, _root_.sbt.plugins.JUnitXmlReportPlugin, _root_.sbt.plugins.Giter8TemplatePlugin, _root_.sbt.plugins.MiniDependencyTreePlugin, _root_.bloop.integrations.sbt.BloopPlugin, _root_.org.scalajs.sbtplugin.ScalaJSJUnitPlugin, _root_.org.scalajs.sbtplugin.ScalaJSPlugin, _root_.scalajsbundler.sbtplugin.WebScalaJSBundlerPlugin, _root_.spray.revolver.RevolverPlugin, _root_.play.twirl.sbt.SbtTwirl, _root_.scalajscrossproject.ScalaJSCrossPlugin, _root_.com.typesafe.sbt.less.SbtLess, _root_.com.typesafe.sbt.digest.SbtDigest, _root_.com.typesafe.sbt.gzip.SbtGzip, _root_.org.jetbrains.sbt.indices.SbtIntellijIndicesPlugin, _root_.org.portablescala.sbtplatformdeps.PlatformDepsPlugin, _root_.scalajsbundler.sbtplugin.ScalaJSBundlerPlugin, _root_.webscalajs.ScalaJSWeb, _root_.webscalajs.WebScalaJS, _root_.sbtcrossproject.CrossPlugin, _root_.com.typesafe.sbt.jse.SbtJsEngine, _root_.com.typesafe.sbt.jse.SbtJsTask, _root_.com.typesafe.sbt.web.SbtWeb, _root_.org.scalajs.jsdependencies.sbtplugin.JSDependenciesPlugin
import sbtcrossproject.{CrossType, crossProject}
import com.typesafe.sbt.less.Import.LessKeys

ideaPort in Global := 65337

val http4s = "0.23.24"
val http4sDom = "0.2.11"

/**Resolving a snapshot version. It's going to be slow unless you use `updateOptions := updateOptions.value.withLatestSnapshots(false)` options* */
updateOptions := updateOptions.value.withLatestSnapshots(false)

lazy val outwatch_components = RootProject(file("../outwatch_components/"))

//lazy val teotl = RootProject(file("../teotl/"))

lazy val server = (project in file("server"))
  .settings(commonSettings)
  .settings(
    scalacOptions ++= Seq("-language:postfixOps"),
    //scalacOptions in ThisBuild ++= Seq("-Ypartial-unification"),
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    LessKeys.compress in Assets := true,
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    //pipelineStages := Seq(digest, gzip),
    //resolvers += "jitpack" at "https://jitpack.io",
    libraryDependencies ++= Seq (
        //"com.github.cornerman.covenant" %%% "covenant-http" % "a35fd8a", //"3d41e5f",
        //"com.github.cornerman.covenant" %%% "covenant-ws" %  "a35fd8a", //"3d41e5f",

        
      	"com.typesafe.akka" %% "akka-http" % "10.2.10",
      	"com.typesafe.akka" %% "akka-stream" % "2.6.16",
        "com.typesafe.akka" %% "akka-actor" % "2.6.16",
        */

        "org.http4s" %% "http4s-ember-server" % http4s,
        "org.http4s" %% "http4s-ember-client" % http4s,
        "org.http4s" %% "http4s-dsl" % http4s,

        "io.circe" %% "circe-literal" % "0.14.6",

      	"com.vmunier" %% "scalajs-scripts" % "1.2.0",   //"1.1.4",

        //Temporalmente usaremos covenant y no mycelium
        //"com.github.cornerman" %% "mycelium-akka" % "0.3.0", //"0.2.3", // akka-based jvm client and server implementation
    ),
    WebKeys.packagePrefix in Assets := "public/",
    //sourceDirectories in (Compile, TwirlKeys.compileTemplates) := (unmanagedSourceDirectories in Compile).value,
    managedClasspath in Runtime += (packageBin in Assets).value,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    javaOptions in run += "-Xms4G -Xmx8G",    //-XX:MaxPermSize=1024M,
    javaOptions in Test += s"-Dconfig.file=${sourceDirectory.value}/test/resources/application.test.conf",  // When running tests, we use this configuration
    fork in Test := true,     // We need to fork a JVM process when testing so the Java options above are applied
  )
  .enablePlugins(SbtWeb, SbtTwirl, WebScalaJSBundlerPlugin/*, JavaAppPackaging*/)
  .dependsOn(sharedJvm)
  //.dependsOn(teotl)

lazy val client = (project in file("client"))
  .settings(commonSettings)
  .settings(
    //webpack / version := "4.46.0",
    requireJsDomEnv in Test := true,
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)), // configure Scala.js to emit a JavaScript module instead of a top-level script
    useYarn := true,

    //version in installJsdom := "15.2.1",

    installJsdom / version := "22.1.0",

    Test / requireJsDomEnv := true,

    scalacOptions ++= Seq(
                  "-language:postfixOps"
    ),
    //The baseDirectory is "tsoolik/client/"
    //webpackConfigFile / fastOptJS := Some(baseDirectory.value / "my.custom.webpack.config.js"), // siguiendo ejemplo de outwatch.
    webpackConfigFile in fastOptJS := Some(baseDirectory.value / "my.custom.webpack.config.js"), // siguiendo ejemplo de outwatch.
    //skip in packageJSDependencies := false,
    //resolvers += "jitpack" at "https://jitpack.io",
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-fake-insecure-java-securerandom" % "1.0.0",

      "org.scalatest" %%% "scalatest" % "3.2.2", // % "test",

      "org.slf4j" % "slf4j-api" % "2.0.9",
      "org.slf4j" % "slf4j-simple" % "2.0.9" % Test,

      "org.http4s" %%% "http4s-dom"    % "0.2.11",

      //"org.scala-js" %%% "scalajs-dom" % "2.1.0",
      //"org.scala-js" %%% "scalajs-java-time" % "1.0.0",

      //"io.github.outwatch.outwatch" %%% "outwatch" % "1.0.0-RC13",     //If you want to use utilities for Store, WebSocket or Http, add the following:
      //"io.github.outwatch.outwatch" %%% "outwatch-util" % "1.0.0-RC13",     //If you want to use utilities for Store, WebSocket or Http, add the following:
      //"com.github.cornerman.colibri" %%% "colibri" % "0.7.8",

      //"com.github.cornerman" %%% "mycelium-core" % "0.3.1+44-6573716d+20221211-1930-SNAPSHOT",
      //"com.github.cornerman" %%% "mycelium-client-js" % "0.3.1+44-6573716d+20221211-1930-SNAPSHOT",

      "io.github.outwatch.outwatch" %%% "outwatch" % "1.0.0-RC17", //If you want to use utilities for Store, WebSocket or Http, add the following:
      "com.github.cornerman.colibri" %%% "colibri" % "0.7.7",

    ),
    //emitSourceMaps := false,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
  )
  .enablePlugins( ScalaJSPlugin,/* ScalaJSWeb,*/ ScalaJSBundlerPlugin)
  .dependsOn(sharedJs)
  .dependsOn(outwatch_components)
  //.dependsOn(teotl)

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

      "io.suzaku" %%% "boopickle" % "1.4.0",

      "io.circe" %%% "circe-generic" % "0.14.6",
      "org.http4s" %%% "http4s-circe" % http4s,
      "io.circe" %%% "circe-generic" % "0.14.3",

      "org.typelevel" %%% "cats-core" % "2.9.0",

      //"com.github.marklister" % "base64" % "0.2.5",

    )
  )
  .jsConfigure(_ enablePlugins ScalaJSWeb)
  .jsConfigure(_ enablePlugins ScalaJSBundlerPlugin)
  .jsConfigure(_ enablePlugins SbtWeb)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
    scalaVersion := "2.13.11",
    organization := "taak" /** almacenes de  unidades medicas.*/      //https://es.freelang.net/enlinea/maya.php?lg=es
)

// loads the server project at sbt startup
onLoad in Global := ( onLoad in Global ).value andThen { s: State => "project server" :: s }


```



#### Error stacktrace:

```
scala.meta.internal.parsers.Reporter.syntaxError(Reporter.scala:16)
	scala.meta.internal.parsers.Reporter.syntaxError$(Reporter.scala:16)
	scala.meta.internal.parsers.Reporter$$anon$1.syntaxError(Reporter.scala:22)
	scala.meta.internal.parsers.Reporter.syntaxError(Reporter.scala:17)
	scala.meta.internal.parsers.Reporter.syntaxError$(Reporter.scala:17)
	scala.meta.internal.parsers.Reporter$$anon$1.syntaxError(Reporter.scala:22)
	scala.meta.internal.parsers.ScalametaParser.syntaxErrorExpected(ScalametaParser.scala:421)
	scala.meta.internal.parsers.ScalametaParser.expect(ScalametaParser.scala:423)
	scala.meta.internal.parsers.ScalametaParser.accept(ScalametaParser.scala:427)
	scala.meta.internal.parsers.ScalametaParser.scala$meta$internal$parsers$ScalametaParser$$inParensAfterOpen(ScalametaParser.scala:248)
	scala.meta.internal.parsers.ScalametaParser.scala$meta$internal$parsers$ScalametaParser$$inParensAfterOpenOr(ScalametaParser.scala:253)
	scala.meta.internal.parsers.ScalametaParser.scala$meta$internal$parsers$ScalametaParser$$inParensOnOpenOr(ScalametaParser.scala:244)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$scala$meta$internal$parsers$ScalametaParser$$getArgClause$1(ScalametaParser.scala:2468)
	scala.meta.internal.parsers.ScalametaParser.atPos(ScalametaParser.scala:319)
	scala.meta.internal.parsers.ScalametaParser.autoPos(ScalametaParser.scala:365)
	scala.meta.internal.parsers.ScalametaParser.scala$meta$internal$parsers$ScalametaParser$$getArgClause(ScalametaParser.scala:2457)
	scala.meta.internal.parsers.ScalametaParser.simpleExprRest(ScalametaParser.scala:2364)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$simpleExpr0$3(ScalametaParser.scala:2293)
	scala.util.Success.$anonfun$map$1(Try.scala:255)
	scala.util.Success.map(Try.scala:213)
	scala.meta.internal.parsers.ScalametaParser.simpleExpr0(ScalametaParser.scala:2293)
	scala.meta.internal.parsers.ScalametaParser.simpleExpr(ScalametaParser.scala:2243)
	scala.meta.internal.parsers.ScalametaParser.prefixExpr(ScalametaParser.scala:2226)
	scala.meta.internal.parsers.ScalametaParser.argumentExprsOrPrefixExpr(ScalametaParser.scala:2423)
	scala.meta.internal.parsers.ScalametaParser.getNextRhs$2(ScalametaParser.scala:2129)
	scala.meta.internal.parsers.ScalametaParser.getPostfixOrNextRhs$1(ScalametaParser.scala:2161)
	scala.meta.internal.parsers.ScalametaParser.loop$6(ScalametaParser.scala:2181)
	scala.meta.internal.parsers.ScalametaParser.postfixExpr(ScalametaParser.scala:2209)
	scala.meta.internal.parsers.ScalametaParser.postfixExpr(ScalametaParser.scala:2102)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$expr$2(ScalametaParser.scala:1682)
	scala.meta.internal.parsers.ScalametaParser.atPosOpt(ScalametaParser.scala:322)
	scala.meta.internal.parsers.ScalametaParser.autoPosOpt(ScalametaParser.scala:366)
	scala.meta.internal.parsers.ScalametaParser.expr(ScalametaParser.scala:1587)
	scala.meta.internal.parsers.ScalametaParser.argumentExpr(ScalametaParser.scala:2454)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$argumentExprsInParens$1(ScalametaParser.scala:2481)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$commaSeparated$1(ScalametaParser.scala:656)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$commaSeparated$1$adapted(ScalametaParser.scala:656)
	scala.meta.internal.parsers.ScalametaParser.iter$1(ScalametaParser.scala:646)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$tokenSeparated$1(ScalametaParser.scala:652)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$tokenSeparated$1$adapted(ScalametaParser.scala:639)
	scala.meta.internal.parsers.ScalametaParser.scala$meta$internal$parsers$ScalametaParser$$listBy(ScalametaParser.scala:568)
	scala.meta.internal.parsers.ScalametaParser.tokenSeparated(ScalametaParser.scala:639)
	scala.meta.internal.parsers.ScalametaParser.commaSeparatedWithIndex(ScalametaParser.scala:659)
	scala.meta.internal.parsers.ScalametaParser.commaSeparated(ScalametaParser.scala:656)
	scala.meta.internal.parsers.ScalametaParser.argumentExprsInParens(ScalametaParser.scala:2481)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$scala$meta$internal$parsers$ScalametaParser$$getArgClause$2(ScalametaParser.scala:2467)
	scala.meta.internal.parsers.ScalametaParser.scala$meta$internal$parsers$ScalametaParser$$inParensAfterOpenOr(ScalametaParser.scala:253)
	scala.meta.internal.parsers.ScalametaParser.scala$meta$internal$parsers$ScalametaParser$$inParensOnOpenOr(ScalametaParser.scala:244)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$scala$meta$internal$parsers$ScalametaParser$$getArgClause$1(ScalametaParser.scala:2468)
	scala.meta.internal.parsers.ScalametaParser.atPos(ScalametaParser.scala:319)
	scala.meta.internal.parsers.ScalametaParser.autoPos(ScalametaParser.scala:365)
	scala.meta.internal.parsers.ScalametaParser.scala$meta$internal$parsers$ScalametaParser$$getArgClause(ScalametaParser.scala:2457)
	scala.meta.internal.parsers.ScalametaParser.simpleExprRest(ScalametaParser.scala:2364)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$simpleExpr0$3(ScalametaParser.scala:2293)
	scala.util.Success.$anonfun$map$1(Try.scala:255)
	scala.util.Success.map(Try.scala:213)
	scala.meta.internal.parsers.ScalametaParser.simpleExpr0(ScalametaParser.scala:2293)
	scala.meta.internal.parsers.ScalametaParser.simpleExpr(ScalametaParser.scala:2243)
	scala.meta.internal.parsers.ScalametaParser.prefixExpr(ScalametaParser.scala:2226)
	scala.meta.internal.parsers.ScalametaParser.postfixExpr(ScalametaParser.scala:2100)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$expr$2(ScalametaParser.scala:1682)
	scala.meta.internal.parsers.ScalametaParser.atPosOpt(ScalametaParser.scala:322)
	scala.meta.internal.parsers.ScalametaParser.autoPosOpt(ScalametaParser.scala:366)
	scala.meta.internal.parsers.ScalametaParser.expr(ScalametaParser.scala:1587)
	scala.meta.internal.parsers.ScalametaParser.expr(ScalametaParser.scala:1486)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$patDefOrDcl$1(ScalametaParser.scala:3609)
	scala.meta.internal.parsers.ScalametaParser.autoEndPos(ScalametaParser.scala:368)
	scala.meta.internal.parsers.ScalametaParser.autoEndPos(ScalametaParser.scala:373)
	scala.meta.internal.parsers.ScalametaParser.patDefOrDcl(ScalametaParser.scala:3596)
	scala.meta.internal.parsers.ScalametaParser.defOrDclOrSecondaryCtor(ScalametaParser.scala:3558)
	scala.meta.internal.parsers.ScalametaParser.nonLocalDefOrDcl(ScalametaParser.scala:3543)
	scala.meta.internal.parsers.ScalametaParser$$anonfun$1.applyOrElse(ScalametaParser.scala:4404)
	scala.meta.internal.parsers.ScalametaParser$$anonfun$1.applyOrElse(ScalametaParser.scala:4399)
	scala.PartialFunction.$anonfun$runWith$1$adapted(PartialFunction.scala:145)
	scala.meta.internal.parsers.ScalametaParser.statSeqBuf(ScalametaParser.scala:4462)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$batchSource$13(ScalametaParser.scala:4696)
	scala.Option.getOrElse(Option.scala:189)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$batchSource$1(ScalametaParser.scala:4696)
	scala.meta.internal.parsers.ScalametaParser.atPos(ScalametaParser.scala:319)
	scala.meta.internal.parsers.ScalametaParser.autoPos(ScalametaParser.scala:365)
	scala.meta.internal.parsers.ScalametaParser.batchSource(ScalametaParser.scala:4652)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$source$1(ScalametaParser.scala:4645)
	scala.meta.internal.parsers.ScalametaParser.atPos(ScalametaParser.scala:319)
	scala.meta.internal.parsers.ScalametaParser.autoPos(ScalametaParser.scala:365)
	scala.meta.internal.parsers.ScalametaParser.source(ScalametaParser.scala:4645)
	scala.meta.internal.parsers.ScalametaParser.entrypointSource(ScalametaParser.scala:4650)
	scala.meta.internal.parsers.ScalametaParser.parseSourceImpl(ScalametaParser.scala:135)
	scala.meta.internal.parsers.ScalametaParser.$anonfun$parseSource$1(ScalametaParser.scala:132)
	scala.meta.internal.parsers.ScalametaParser.parseRuleAfterBOF(ScalametaParser.scala:59)
	scala.meta.internal.parsers.ScalametaParser.parseRule(ScalametaParser.scala:54)
	scala.meta.internal.parsers.ScalametaParser.parseSource(ScalametaParser.scala:132)
	scala.meta.parsers.Parse$.$anonfun$parseSource$1(Parse.scala:29)
	scala.meta.parsers.Parse$$anon$1.apply(Parse.scala:36)
	scala.meta.parsers.Api$XtensionParseDialectInput.parse(Api.scala:25)
	scala.meta.internal.semanticdb.scalac.ParseOps$XtensionCompilationUnitSource.toSource(ParseOps.scala:17)
	scala.meta.internal.semanticdb.scalac.TextDocumentOps$XtensionCompilationUnitDocument.toTextDocument(TextDocumentOps.scala:206)
	scala.meta.internal.pc.SemanticdbTextDocumentProvider.textDocument(SemanticdbTextDocumentProvider.scala:54)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticdbTextDocument$1(ScalaPresentationCompiler.scala:374)
```
#### Short summary: 

file%3A%2F%2F%2Fhome%2Felyphas%2FPrjs%2Ftaak%2Fbuild.sbt:44: error: ) expected but string constant found
        "org.http4s" %% "http4s-ember-server" % http4s,
        ^