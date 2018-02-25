(ns components.toolbar
  (:require
    [components.common-styles :as cs]
    [components.core :as c]
    [components.params :as p]
    [garden.color :as gc]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    [keybind.core :as key]
    ))


(def default-params
  {:back       (:blue-grey p/colors)
   :color      (:white p/colors)
   :height     32
   :item-width 32
   :padding    16})

(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))

(def toolbar-s ^:css [cs/flex-box
                      {:box-sizing    "border-box"
                       :position      "relative"
                       :height        (px (v :height))
                       :background    (v :back)
                       :color         (v :color)
                       :border-width  "0 0 1px 0"
                       :border-style  "solid"
                       :border-color  "rgba(0,0,0,0.05)"
                       :padding-left  (px (v :padding))
                       :padding-right (px (v :padding))
                       }])

(def toolbar-s__shadow-wrapper ^:css {:position "absolute"
                                      :content  "\" \""
                                      :top      0
                                      :bottom   "-10px"
                                      :left     0
                                      :right    0
                                      :overflow "hidden"
                                      })

(def toolbar-s__shadow ^:css {:position   "absolute"
                              :content    "\" \""
                              :top        0
                              :bottom     "9px"
                              :left       0
                              :right      0
                              :box-shadow "0 0 10px 3px rgba(0,0,0,0.2)"})


(def toolbar-s__item ^:css [cs/flex-box
                            {:position        "relative"
                             :flex-direction  "column"
                             :align-items     "center"
                             :justify-content "center"
                             :width           (px (v :item-width))}
                            [:&:hover:after {:position   "absolute"
                                             :content    "\" \""
                                             :top        0
                                             :bottom     0
                                             :left       0
                                             :right      0
                                             :background "rgba(255,255,255,0.1)"}]])

(def toolbar-s__item--clickable ^:css {:cursor "pointer"})


(defn toolbar [items]
  "[{:name '1' :content [:span 'FF'] :on-click fn } {:name '1' :content [:span 'FF'] :on-click fn }]"
  (fn [items]
    [:div (c/cls 'toolbar-s)
     [:div (c/cls 'toolbar-s__shadow-wrapper) [:div (c/cls 'toolbar-s__shadow)]]
     (for [[idx {:keys [content on-click key-bind]}] (map-indexed vector items)
           :let [_ (when (some? key-bind)
                     (key/bind! key-bind (str "toolbar-" key-bind) on-click))]]

       ^{:key idx} [:div (c/cls 'toolbar-s__item
                                'toolbar-s__item--clickable
                                :on-click on-click) content])
     ]))


(c/add-css (ns-interns 'components.toolbar))
