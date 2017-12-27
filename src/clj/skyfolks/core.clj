(ns skyfolks.core
  (:require
            [skyfolks.cfg :refer [read-cfg]]

            [aleph.http :as http]
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
  (let [cfg (read-cfg "./cfg/config.clj")]
    (http/start-server handler {:port 10000})
    (println "Started")))



