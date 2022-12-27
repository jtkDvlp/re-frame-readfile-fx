(defproject tallyfor/re-frame-readfile-fx "2.0.2-SNAPSHOT"
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
  [:target-path]

  :profiles
  {:provided
   {:dependencies
    [[org.clojure/clojure "1.10.1"]
     [org.clojure/clojurescript "1.10.773"]

     [org.clojure/core.async "1.3.610"]

     [re-frame "1.1.1"]]}

   :dev
   {:source-paths
    ["src" "test"]

    :target-path
    "target/dev"

    :resource-paths
    ["resources" "target/dev"]

    :dependencies
    [[com.bhauman/figwheel-main "0.2.11"]
     [cider/piggieback "0.4.0"]]

    :repl-options
    {:nrepl-middleware
     [cider.piggieback/wrap-cljs-repl]}}})
