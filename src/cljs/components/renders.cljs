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

(def greyscale-0 ^:css {:background "#000"})
(def greyscale-1 ^:css {:background "#111"})
(def greyscale-2 ^:css {:background "#222"})
(def greyscale-3 ^:css {:background "#333"})
(def greyscale-4 ^:css {:background "#444"})
(def greyscale-5 ^:css {:background "#555"})
(def greyscale-6 ^:css {:background "#666"})
(def greyscale-7 ^:css {:background "#777"})
(def greyscale-8 ^:css {:background "#888"})
(def greyscale-9 ^:css {:background "#999"})
(def greyscale-10 ^:css {:background "#aaa"})
(def greyscale-11 ^:css {:background "#bbb"})
(def greyscale-12 ^:css {:background "#ccc"})
(def greyscale-13 ^:css {:background "#ddd"})
(def greyscale-14 ^:css {:background "#eee"})
(def greyscale-15 ^:css {:background "#fff"})


(defn landscape-vs-free [cell]
  (if (sc/landscape? cell)
    'lanscape-cell
    'free-cell))


(def temperature-pallete [
                          [10, 50, 120]
                          [15, 75, 165]
                          [30, 110, 200]
                          [60, 160, 240]
                          [80, 180, 250]
                          [130, 210, 255]
                          [160, 240, 255]
                          [200, 250, 255]
                          [230, 255, 255]
                          [255, 250, 220]
                          [250, 232, 120]
                          [255, 192, 60]
                          [255, 160, 0]
                          [255, 96, 0]
                          [255, 50, 0]
                          [225, 20, 0]
                          [192, 0, 0]
                          [165, 0, 0]
                          ])

(defn mk-temperature-classes []
  (into [] (for [[idx [r g b]] (map-indexed vector temperature-pallete)]
             [(str ".temperature-" idx) {:background (str "rgb(" r "," g "," b ")")}])))


(defn temperature [cell]
  (let [temp (sc/temperature cell)
        idx (cond
              (< temp -256) 0
              (> temp 256) 17
              :else (-> temp
                        (quot 16)
                        (+ 9)
                        ))]
    (str "temperature-" idx)))


(defn thermal-conductivity [cell]
  (let [t-conductivity (sc/thermal-conductivity cell)

        idx (if (>= t-conductivity 256)
              15
              (quot t-conductivity 16))]

    (c/log "thermal-conductivity  " idx " " t-conductivity)
    (str "greyscale-" idx)))

(def renders-list {:land      landscape-vs-free
                   :temp      temperature
                   :temp-cond thermal-conductivity})



(c/add-css (ns-interns 'components.renders) (mk-temperature-classes))