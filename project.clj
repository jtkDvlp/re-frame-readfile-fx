(defproject tallyfor/re-frame-readfile-fx "2.0.1-SNAPSHOT"
  :description
  "A re-frame effects handler for reading files via FileReader-Object"

  :url
  "https://github.com/jtkDvlp/re-frame-readfile-fx"

  :license
  {:name "MIT"}

  :min-lein-version
  "2.5.3"

  :source-paths
  ["src"]

  :clean-targets
  ^{:protect false}
  ["resources/public/js"
   "target"]

  :plugins
  [[lein-cljsbuild "1.1.4"
    :exclusions [[org.clojure/clojure]]]
   [lein-figwheel "0.5.0-1"]]

  :profiles
  {:provided
   {:dependencies
    [[org.clojure/clojure "1.8.0"]
     [org.clojure/clojurescript "1.9.229"]

     [re-frame "0.9.1"]]}

   :dev
   {:dependencies
    [[figwheel-sidecar "0.5.8"]
     [com.cemerick/piggieback "0.2.1"]]}}

  :repl-options
  {:nrepl-middleware
   [cemerick.piggieback/wrap-cljs-repl]}

  :cljsbuild
  {:builds
   [{:id "test"
     :source-paths ["src" "test"]
     :figwheel true
     :compiler {:main jtk-dvlp.re-frame.readfile-fx-test
                :asset-path "js/test/out"
                :output-to "resources/public/js/test/test.js"
                :output-dir "resources/public/js/test/out"}}]})
