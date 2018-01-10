(ns skyfolks.routing
  (:require
    [goog.events :as events]
    [goog.history.EventType :as EventType]
    [reagent.core :as r]
    [clojure.string :as s]
    )
  (:import goog.History))

(def nav-pos (r/atom nil))

(defn log [& msgs]
  (.log js/console (apply str msgs)))

(defn log-o [s o]
  (.log js/console s (clj->js o)))

(defn dispatch! [token]
  (let [[uri query-params-unparsed] (s/split (if (empty? token)
                                               "Home?current=0&step=20"
                                               token) #"\?")
        _ (log-o "uri" uri)
        _ (log-o "query-params-unparsed" query-params-unparsed)
        query-params (as-> (s/split query-params-unparsed #"&") $$
                           (reduce (fn [m x]
                                     (let [[k v] (s/split x #"=")]
                                       (assoc m k (js/decodeURIComponent v)))) {} $$))
        _ (log-o "query-params" query-params)]
    (reset! nav-pos [uri query-params])))

(def history (let [h (History.)]
               (events/listen h EventType/NAVIGATE
                              (fn [x]
                                (let [token (.-token x)]
                                  (log-o "*** History token: " token)
                                  (dispatch! token))))
               (doto h (.setEnabled true))))


