(ns skyfolks.world
  (:require
    [clojure.tools.logging :as log]
    [ring.util.response :as r :refer [created file-response response not-found]]
    ))

(defn cur-world [req])



(defn save-world [cfg req]
  (log/debug "save-world issued " req)
  (let [{:keys [world filename]} (:params req)]
    (spit (str (get-in cfg [:world :location]) "/" filename) world)
    (response {:ok true})))

(defn load-world [cfg req]
  (log/debug "load-world issued " req)
  (response {:ok true}))

