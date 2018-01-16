(ns components.inputs
  (:require [components.common-styles :as cs]
            [components.core :as c]
            [components.params :as p]
            [garden.color :as gc]
            [garden.core :refer [css]]
            [garden.units :refer [px]]
            [reagent.core :as r]))


(def default-params
  {:padding {:left 16}})

(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))

(def text-input ^:css [{:position     "absolute"
                        :top          0
                        :bottom       0
                        :left         0
                        :right        0
                        :width        "100%"
                        :padding-left (px (v :padding :left))
                        :border-style "none"
                        :box-sizing   "border-box"
                        }
                       [:&:focus

                        [:before
                         cs/iconic-font
                         {:content "\"\\e809\""}]]])


(defn fullsize-input [placeholder on-change-f & {:keys [value autofocus]}]
  (let [val (r/atom (str value))
        f (c/debounce
            #(do
               (c/log "debounce completed")
               (on-change-f @val)) (v :debounce-timeout))]
    (r/create-class
      {:component-did-mount (fn [x]
                              (when autofocus
                                (let [elem (r/dom-node x)]
                                  (.setTimeout js/window #(.focus elem) 50))))
       :component-function  (fn []
                              [:input (c/cls 'text-input
                                             :type "text"
                                             :spellCheck "false"
                                             :autoCapitalize "off"
                                             :autoCorrect "off"
                                             :autoComplete "off"
                                             :value @val
                                             :placeholder placeholder
                                             :on-change (fn [x]
                                                          (let [new-val (-> x .-target .-value)]
                                                            (reset! val new-val)
                                                            (f))))])})))

(c/add-css (ns-interns 'components.inputs))