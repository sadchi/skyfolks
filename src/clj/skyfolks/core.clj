(ns skyfolks.core
  (:require [aleph.http :as http]
            [compojure.core :as c]
            [compojure.route :as route]
            [ring.middleware.params :as params]))



(defn hello-world-handler [_]
  {:status  200
   :headers {"content-type" "text/plain"}
   :body    "hello world!"})

(def handler
  (params/wrap-params
    (c/routes
      (c/GET "/hello" [] hello-world-handler)
      (route/not-found "No such page."))))



(defn -main [& args]
  (http/start-server handler {:port 10000})
  (println "Started"))



