### scala.meta.internal.mtags.IndexingExceptions$InvalidSymbolException: #

Symbol: #

#### Error stacktrace:

```
scala.meta.internal.mtags.OnDemandSymbolIndex.definitions(OnDemandSymbolIndex.scala:61)
	scala.meta.internal.metals.DestinationProvider.definition(DefinitionProvider.scala:475)
	scala.meta.internal.metals.DestinationProvider.fromSymbol(DefinitionProvider.scala:513)
	scala.meta.internal.metals.DestinationProvider.fromSymbol(DefinitionProvider.scala:554)
	scala.meta.internal.metals.DefinitionProvider.fromSymbol(DefinitionProvider.scala:207)
	scala.meta.internal.metals.StacktraceAnalyzer.findLocationForSymbol$1(StacktraceAnalyzer.scala:67)
	scala.meta.internal.metals.StacktraceAnalyzer.$anonfun$fileLocationFromLine$2(StacktraceAnalyzer.scala:72)
	scala.PartialFunction$Unlifted.applyOrElse(PartialFunction.scala:347)
	scala.collection.IterableOnceOps.collectFirst(IterableOnce.scala:1142)
	scala.collection.IterableOnceOps.collectFirst$(IterableOnce.scala:1134)
	scala.collection.AbstractIterable.collectFirst(Iterable.scala:933)
	scala.meta.internal.metals.StacktraceAnalyzer.$anonfun$fileLocationFromLine$1(StacktraceAnalyzer.scala:72)
	scala.Option.flatMap(Option.scala:283)
	scala.meta.internal.metals.StacktraceAnalyzer.fileLocationFromLine(StacktraceAnalyzer.scala:70)
	scala.meta.internal.metals.StacktraceAnalyzer.$anonfun$makeHtmlCommandParams$1(StacktraceAnalyzer.scala:151)
	scala.collection.ArrayOps$.foreach$extension(ArrayOps.scala:1323)
	scala.meta.internal.metals.StacktraceAnalyzer.htmlStack$1(StacktraceAnalyzer.scala:150)
	scala.meta.internal.metals.StacktraceAnalyzer.$anonfun$makeHtmlCommandParams$3(StacktraceAnalyzer.scala:172)
	scala.meta.internal.metals.StacktraceAnalyzer.$anonfun$makeHtmlCommandParams$3$adapted(StacktraceAnalyzer.scala:172)
	scala.meta.internal.metals.HtmlBuilder.call(HtmlBuilder.scala:75)
	scala.meta.internal.metals.StacktraceAnalyzer.makeHtmlCommandParams(StacktraceAnalyzer.scala:172)
	scala.meta.internal.metals.StacktraceAnalyzer.analyzeStackTrace(StacktraceAnalyzer.scala:94)
	scala.meta.internal.metals.StacktraceAnalyzer.analyzeCommand(StacktraceAnalyzer.scala:26)
	scala.meta.internal.metals.MetalsLspService.analyzeStackTrace(MetalsLspService.scala:1902)
	scala.meta.internal.metals.WorkspaceLspService.$anonfun$executeCommand$43(WorkspaceLspService.scala:908)
	scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:687)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:467)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	java.base/java.lang.Thread.run(Thread.java:829)
```
#### Short summary: 

scala.meta.internal.mtags.IndexingExceptions$InvalidSymbolException: #