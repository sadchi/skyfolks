(ns skyfolks.core
  (:require
    [aleph.http :as http]
    [cheshire.core :refer [generate-string]]
    [clojure.tools.logging :as log]
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
    [skyfolks.common.string :as s]
    ))


(defn wrap-exception-handling [handler]
  (fn [request]
    (try
      (handler request)
      (catch Throwable e
        (log/trace (generate-string (assoc (:json-params request) :exception (s/generate-exception e))))
        (s/log-exception e)
        (throw e)))))

(defn common-handler [handler]
  (-> handler
      wrap-exception-handling
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
      (c/PATCH "/world" [] (common-handler w/calculate-new-world))
      (cr/resources "/")
      (cr/not-found "No such page."))))



(defn -main [& args]
  (let [cfg (read-cfg "./cfg/config.clj")]
    (log/info (apply str (take 200 (repeat "*"))))
    (log/debug "Starting with cfg: " cfg)
    (http/start-server (wrap-reload (handlers cfg)) {:port 10000})
    (println "Started")))



