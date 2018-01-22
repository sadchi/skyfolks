(ns components.json-box
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


(def json-box-s (css [:textarea cs/mono-font
                      {:position     "absolute"
                       :content      "\" \""
                       :top          0
                       :bottom       0
                       :left         0
                       :right        0
                       :width        "100%"
                       :resize       "none"
                       :outline      "none"
                       :box-sizing   "border-box"
                       :padding      (px (v :padding))
                       :border-width "0 0 0 4px"
                       :border-style "solid"
                       :border-color "transparent"
                       :overflow-y   "scroll"
                       :overflow-x   "auto"
                       :margin-top   "1px"
                       }
                      [:&:focus {:border-color (cs/rgba (v :accent) 1)}]]))

(c/add-style! json-box-s :ns "textarea")

(defn json-box [val]
  (r/create-class
    {:component-did-mount (fn [this]
                            (key/bind! "f8" ::console-cmd (fn []
                                                            (-> (r/dom-node this)
                                                                ;(.getElementsByTagName "input")
                                                                ;(aget 0)
                                                                (.focus)))))
     :component-function  (fn []
                            [:textarea (c/cls :wrap "off"
                                              :spellCheck "false"
                                              :autoCapitalize "off"
                                              :autoCorrect "off"
                                              :autoComplete "off"
                                              :on-change (fn [x]
                                                           (let [new-val (-> x .-target .-value)]
                                                             (reset! val new-val))))])}))

(c/add-css (ns-interns 'components.json-box))