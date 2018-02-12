(defproject skyfolks "0.1.0-SNAPSHOT"

  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [
                 [aleph "0.4.4"]
                 [com.andrewmcveigh/cljs-time "0.5.2"]
                 [compojure "1.6.0"]
                 [garden "1.3.3"]
                 [hiccup "1.0.5"]
                 [keybind "2.2.0"]
                 [manifold "0.1.6"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [prismatic/schema "1.1.7"]
                 [reagent "0.8.0-alpha2"]
                 [ring "1.6.3"]
                 [ring/ring-json "0.4.0"]
                 ]


  :plugins [
            [lein-cljsbuild "1.1.7"]
            [lein-gorilla "0.4.0"]
            ]

  :source-paths ["src" "src/clj" "src/cljs" "src/cljc"]

  :resource-paths ["cfg" "resources"]

  :aot [skyfolks.core]
  :main skyfolks.core

  :cljsbuild {:builds
              {:report
               {:jar          true
                :source-paths ["src/cljs" "src/cljc"]
                :compiler     {:output-to     "resources/public/app.js"
                               :output-dir    "resources/public/out"
                               :asset-path    "out"
                               :optimizations :none
                               :pretty-print  false}}}}

  :profiles {:dev
             {:source-paths ["src/cljs" "src/cljc"]
              :cljsbuild    {:builds {:report
                                      {:jar      true
                                       :compiler {:main          "skyfolks.core"
                                                  :asset-path    "out"
                                                  :optimizations :none
                                                  :pretty-print  true}}}}}
             :prod
             {:cljsbuild {:builds
                          {:report
                           {:jar      true
                            :compiler {:source-map       "resources/public/out.js.map"
                                       :optimizations    :advanced
                                       :closure-warnings {:externs-validation :off
                                                          :non-standard-jsdoc :off}
                                       :pretty-print     false}}}}}}

  :aliases {"init"   ["modules" "install"]
            "bj"     ["cljsbuild" "auto"]
            "bjprod" ["with-profile" "prod" "cljsbuild" "once"]
            "uber"   ["do" "bjprod" ["uberjar"]]}

  )
