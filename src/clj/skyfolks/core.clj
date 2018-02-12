(ns skyfolks.core
  (:require
    [aleph.http :as http]
    [compojure.core :as c]
    [compojure.route :as cr]
    [hiccup.page :refer [html5 include-js]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body wrap-json-params]]
    [ring.middleware.keyword-params :refer [wrap-keyword-params]]
    [ring.middleware.params :as params]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.reload :refer [wrap-reload]]
    [skyfolks.cfg :refer [read-cfg]]
    [skyfolks.world :as w]
    ))


(defn common-handler [handler]
  (-> handler
      wrap-keyword-params
      wrap-params
      wrap-json-params
      wrap-json-response
      ))

(defn handlers [cfg]
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
      (c/GET "/world" [] (common-handler (partial w/load-world cfg)))
      (c/PUT "/world" [] (common-handler (partial w/save-world cfg)))
      (cr/resources "/")
      (cr/not-found "No such page."))))



(defn -main [& args]
  (let [cfg (read-cfg "./cfg/config.clj")]
    (http/start-server (wrap-reload (handlers cfg)) {:port 10000})
    (println "Started")))



