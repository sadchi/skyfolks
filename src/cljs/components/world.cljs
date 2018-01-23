(ns components.world
  (:require
    [components.common-styles :as cs]
    [components.core :as c]
    [components.inputs :as in]
    [components.params :as p]
    [garden.color :as gc]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    [keybind.core :as key]
    [reagent.core :as r]

    ))

(def default-params
  {})

(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))





(c/add-css (ns-interns 'components.world))