(ns components.common-state
  (:require [reagent.core :as r]))


(def extra-param (r/atom "Test area"))

(def current-brash (r/atom nil))

(def empty-world {:w    0
                  :h    0
                  :data nil})

(def world (r/atom empty-world))

(def render (r/atom (fn [x] nil)))

(def log (r/atom []))