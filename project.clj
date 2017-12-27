(defproject skyfolks "0.1.0-SNAPSHOT"

  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.0"]
                 [aleph "0.4.4"]
                 [ring "1.6.3"]
                 [manifold "0.1.6"]]

  :source-paths ["src" "src/clj" "src/sql"]

  :resource-paths ["cfg" "resources"]

  :aot [skyfolks.core]
  :main skyfolks.core

  )
