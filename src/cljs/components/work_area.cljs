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
   :height  16
   :width   16
   })

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

(def work-area__row ^:css {:height        (px (v :height))
                           :margin-bottom (px -1)
                           })

(def work-area__row__cell ^:css {:margin-right (px -1)
                                 :display      "inline-block"
                                 :width        (px (v :width))
                                 :height       "100%"
                                 :background   "grey"

                                 :border       "1px solid blue"})

(defn work-area [data]
  (fn [data]
    [:div (c/cls 'work-area-s)
     [:div (c/cls 'work-area__block)
      [:div (c/cls 'work-area__row)
       [:div (c/cls 'work-area__row__cell)]
       [:div (c/cls 'work-area__row__cell)]

       ]
      [:div (c/cls 'work-area__row)
       [:div (c/cls 'work-area__row__cell)]
       [:div (c/cls 'work-area__row__cell)]
       [:div (c/cls 'work-area__row__cell)]
       ]]]))

(c/add-css (ns-interns 'components.work-area))