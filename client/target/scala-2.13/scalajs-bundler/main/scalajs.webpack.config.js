module.exports = {
  "entry": {
    "client-fastopt": ["/home/elyphas/Prjs/taak/client/target/scala-2.13/scalajs-bundler/main/client-fastopt.js"]
  },
  "output": {
    "path": "/home/elyphas/Prjs/taak/client/target/scala-2.13/scalajs-bundler/main",
    "filename": "[name]-bundle.js"
  },
  "mode": "development",
  "devServer": {
    "port": 8080
  },
  "devtool": "source-map",
  "module": {
    "rules": [{
      "test": new RegExp("\\.js$"),
      "enforce": "pre",
      "use": ["source-map-loader"]
    }]
  }
}