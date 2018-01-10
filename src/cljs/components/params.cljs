(ns components.params
  (:require [garden.color :as gc]))


(defn darken [hex-color perc]
  (gc/as-hex (gc/darken hex-color perc)))

(defn lighten [hex-color perc]
  (gc/as-hex (gc/lighten hex-color perc)))

(def unit 4)

(def colors {:red          "#F44336"
             :pink         "#E91E63"
             :indigo       "#3F51B5"
             :blue         "#2196F3"
             :light-blue   "#03A9F4"
             :cyan         "#00BCD4"
             :teal         "#009688"
             :green        "#4CAF50"
             :lime         "#CDDC39"
             :yellow       "#FFEB3B"
             :amber        "#FFC107"
             :orange       "#FF9800"
             :deep-orange  "#FF5722"
             :grey         "#9E9E9E"
             :darkest-grey "#212121"
             :dark-grey    "#606060"
             :light-grey   "#C7C7C7"
             :blue-grey    "#607D8B"
             :white        "#FFFFFF"})

(def params {})
