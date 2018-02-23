(ns components.work-area
  (:require
    [components.common-state :as cst]
    [components.common-styles :as cs]
    [components.core :as c]
    [components.params :as p]
    [components.world :as w]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    [keybind.core :as key]
    [reagent.core :as r]))



(def default-params
  {
   :accent  "#607D8B"
   :padding 16
   :height  16
   :width   16})


(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))


(def work-area-s ^:css [cs/flex-box
                        {:position        "absolute"
                         :align-items     "center"
                         :justify-content "center"
                         :content         "\" \""
                         :left            0
                         :right           0
                         :top             0
                         :bottom          0
                         :overflow        "scroll"}])


(def work-area__block ^:css {})

(def work-area__row ^:css [cs/flex-box {:height (px (v :height))}])

(def work-area__row__cell ^:css {
                                 :background "grey"
                                 :height     "100%"
                                 :width      (px (v :width))})


(defn work-area [data]
  (fn [data]
    (c/log "work-area world " @cst/world)
    [:div (c/cls 'work-area-s)
     [:div (c/cls 'work-area__block)
      (doall (for [[idx row] (map-indexed vector (:data @cst/world))]
               ^{:key idx} [:div (c/cls 'work-area__row)
                            (doall (for [[idx2 cell] (map-indexed vector row)]
                                     ^{:key idx2} [:div (c/cls 'work-area__row__cell
                                                               (@cst/render cell)
                                                               :on-click #(swap! cst/world w/update-world idx idx2 @cst/current-brash))]))]))]]))

(c/add-css (ns-interns 'components.work-area))