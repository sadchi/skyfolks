(ns skyfolks.core
  (:require
    [components.app :as a]
    [reagent.core :as r]
    [skyfolks.routing :as routing]
    ))

(r/render-component [a/app routing/nav-pos] (.getElementById js/document "app"))
