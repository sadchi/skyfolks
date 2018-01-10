(ns skyfolks.core
  (:require
    [skyfolks.cfg :refer [read-cfg]]
    [aleph.http :as http]
    [compojure.core :as c]
    [compojure.route :as cr]
    [ring.middleware.params :as params]
    [ring.middleware.reload :refer [wrap-reload]]
    [hiccup.page :refer [html5 include-js]]
    ))



(defn hello-world-handler [_]
  {:status  200
   :headers {"content-type" "text/plain"}
   :body    "hello world!"})

(def handler
  (params/wrap-params
    (c/routes
      (c/GET "/" [] {:status  200
                     :headers {"Content-type" "text/html"}
                     :body    (html5
                                [:head
                                 [:title "Skyfolks debug console"]]
                                [:body
                                 [:div#dimmer]
                                 [:div#alerter]
                                 [:div#tooltip]
                                 [:div#modal]
                                 [:div#app]
                                 (include-js "app.js")])})
      (cr/resources "/")
      (cr/not-found "No such page."))))



(defn -main [& args]
  (let [cfg (read-cfg "./cfg/config.clj")]
    (http/start-server (wrap-reload handler) {:port 10000})
    (println "Started")))



