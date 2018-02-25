(ns skyfolks.world
  (:require
    [cells.core :as sc]
    [clj-time.core :as t]
    [clojure.tools.logging :as log]
    [ring.util.response :as r :refer [created file-response response not-found]]
    [skyfolks.ca.calc :as c]
    [skyfolks.ca.split :as s]
    ))

(def our-world (agent nil))

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



(defn world-next-step [coll-of-f x]
  (let [_ (log/debug "world-next-step x: " x)
        start (System/currentTimeMillis)
        old-data (:data x)
        prepared (s/prepare old-data)
        _ (log/debug "world-next-step prepared: " prepared)


        ;apply any F

        end (System/currentTimeMillis)

        ]
    (assoc-in x [:elapsed :total] (- end start))))

(defn calculate-new-world [req]
  (log/debug "calculate-new-world " @our-world)
  (send our-world (partial world-next-step nil))
  (await our-world)
  (response @our-world))

