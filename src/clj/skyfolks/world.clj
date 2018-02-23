(ns skyfolks.world
  (:require
    [clojure.tools.logging :as log]
    [ring.util.response :as r :refer [created file-response response not-found]]
    ))

(def our-world (agent nil))

(defn cur-world [req])

(defn save-world [cfg req]
  (log/debug "save-world issued " req)
  (let [{:keys [world filename]} (:params req)]
    (send our-world (fn [_] world))
    (spit (str (get-in cfg [:world :location]) "/" filename) world)
    (response {:ok true})))

(defn load-world [cfg req]
  (log/debug "load-world issued " req)
  (let [filename (get-in req [:params :filename])]
    (log/debug "load-world filename " filename)
    (if (empty? filename)
      (response @our-world)
      (let [new-world (read-string (slurp (str (get-in cfg [:world :location]) "/" filename)))]
        (send our-world (fn [_] new-world))
        (response new-world)))))

