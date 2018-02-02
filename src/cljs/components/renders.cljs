(ns components.renders
  (:require
    [cells.core :as sc]
    [components.common-styles :as cs]
    [components.core :as c]
    [components.params :as p]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    [keybind.core :as key]
    [reagent.core :as r]
    ))

(def default-params
  {})

(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))


(def lanscape-cell ^:css {:background "grey"})
(def free-cell ^:css {:background "rgba(0,0,255,0.5)"})


(defn landscape-vs-free [cell]
  (if (sc/landscape? cell)
    'lanscape-cell
    'free-cell))


(def renders-list {:lanscape-vs-free landscape-vs-free})

(c/add-css (ns-interns 'components.renders))