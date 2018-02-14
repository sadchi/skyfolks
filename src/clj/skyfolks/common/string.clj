(ns skyfolks.common.string
  (:require
    [clojure.string :as str]
    [clojure.tools.logging :as log]
    ))


(defn log-exception [^Throwable e]
  (log/error (str (.getName (.getClass e)) ":") (.getMessage e))
  (->> (.getStackTrace e)
       (map #(str "  at " %))
       (map #(log/error %))
       doall))


(defn generate-exception [^Throwable e]
  {:exception (str (.getName (.getClass e)) ":" (.getMessage e))
   :trace     (str/join (map #(str "\n  at " %) (.getStackTrace e)))})

