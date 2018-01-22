(ns components.work-area
  (:require
    [components.common-styles :as cs]
    [components.core :as c]
    [components.params :as p]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    [keybind.core :as key]
    [reagent.core :as r]
    ))


(def default-params
  {
   :accent  "#607D8B"
   :padding 16
   })

(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))


(def work-area-s ^:css {:position "absolute"
                        :content  "\" \""
                        :left     0
                        :right    0
                        :top      0
                        :bottom   0
                        :overflow "scroll"})

(defn work-area [data]
  (fn [data]
    [:div (c/cls 'work-area-s) @data]))

(c/add-css (ns-interns 'components.work-area))