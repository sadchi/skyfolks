(ns components.console
  (:require
    [components.common-styles :as cs]
    [components.core :as c]
    [components.params :as p]
    [garden.color :as gc]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    ))


(def default-params
  {:cmd-height 32
   :padding    16})

(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))


(def console-s ^:css [cs/flex-box
                      {:flex-direction "column"
                       :height         "100%"}])

(def console-s__log ^:css {:flex-grow 1})

(def console-s__cmd ^:css {:height (px (v :cmd-height))})

(defn console [spec]
  (fn [spec]
    [:div (c/cls 'console-s)
     [:div (c/cls 'console-s__log) "T1"]
     [:div (c/cls 'console-s__cmd) "T2"]]))




(c/add-css (ns-interns 'components.console))