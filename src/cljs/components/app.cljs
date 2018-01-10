(ns components.app
  (:require
    [components.common-styles :as cs]
    [components.core :as c]
    [components.params :as p]
    [components.toolbar :as t]
    [garden.color :as gc]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    ))


(def default-params
  {:panels-width 500
   :border-color "rgba(0,0,0,0.3)"})

(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))


(def app-container ^:css [cs/flex-box
                          {:height     "100vh"
                           :width      "100vw"
                           :font-size  (px 14)
                           :color      (:dark-grey p/colors)
                           :background "white"}])


(def app-container__work-area ^:css {:flex-grow 1})

(def app-container__panels ^:css [cs/flex-box
                                  {:flex-direction "column"
                                   :width          (px (v :panels-width))
                                   :border-width   "0 0 0 1px"
                                   :border-style   "solid"
                                   :border-color   (v :border-color)}])

(defn app [_]
  [:div (c/cls 'app-container)
   [:div (c/cls 'app-container__work-area) "Test work area"]
   [:div (c/cls 'app-container__panels)
    [t/toolbar [{:content  [:div (c/cls 'cs/icon-pencil)]
                 :on-click (fn [] (c/log "Ha ha"))}
                {:content  [:div (c/cls 'cs/icon-up)]
                 :on-click (fn [] (c/log "Mmmmm"))}
                {:content  [:div (c/cls 'cs/icon-down)]
                 :on-click (fn [] (c/log "Fuck"))}]]
    "Test panel"]])




(c/add-css (ns-interns 'components.app))