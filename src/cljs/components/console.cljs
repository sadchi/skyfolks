(ns components.console
  (:require
    [components.common-styles :as cs]
    [components.core :as c]
    [components.params :as p]
    [garden.color :as gc]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    [reagent.core :as r]))


(def default-params
  {:cmd-height 32
   :padding    16})

(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))


(def console-s ^:css [cs/flex-box
                      {:flex-direction "column"
                       :height         "100%"}])

(def console-s__log ^:css [cs/mono-font

                           {:padding-left (px (v :padding))
                            ;:flex            1
                            ;:flex-grow       1
                            ;:flex-shrink     1
                            ;:flex-direction  "column"
                            ;:justify-content "flex-end"
                            :overflow-y   "scroll"}])

(def console-s__cmd ^:css [cs/mono-font
                           {:flex-shrink  0
                            :height       (px (v :cmd-height))
                            :line-height  (px (v :cmd-height))
                            :border-width "1px 0 1px 0"
                            :border-style "solid"
                            :border-color "rgba(0,0,0,0.3)"
                            :padding-left (px (v :padding))}])


(defn console-log [log-a]
  "log is an atom , should contain list of items: [:key :mag]"
  (r/create-class
    {:component-did-mount  (fn [this]
                             (let [node (r/dom-node this)]
                               (c/log "console-log :component-did-mount node" node)
                               (c/log "console-log :component-did-mount scrollHeight" (.-scrollHeight node))
                               (c/log "console-log :component-did-mount scrollTop 1: " (aget node "scrollTop"))
                               (aset this "scrollTop" 30 #_(.-scrollHeight node))
                               (c/log "console-log :component-did-mount scrollTop 2: " (aget node "scrollTop"))))
     :component-did-update (fn [this _]
                             (let [node (r/dom-node this)]
                               (c/log "console-log :component-did-update scrollHeight" (aget node "scrollHeight"))
                               (c/log "console-log :component-did-update scrollTop 2: " (aget node "scrollTop"))
                               #_(aset node "scrollTop" (aget node "scrollHeight"))))
     :component-function   (fn [log-a]
                             [:div (c/cls 'console-s__log)
                              [:div "T1 test hallo test"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa"]
                              [:div "Pa Pa Pa 3"]
                              [:div "Pa Pa Pa 2"]
                              [:div "Pa Pa Pa 1"]
                              ])}))


(defn console-cmd []
  (fn []
    ))

(defn console [spec]
  (let [log (r/atom nil)]
    (fn [spec]
      [:div (c/cls 'console-s)
       [console-log log]
       [:div (c/cls 'console-s__cmd) "T2"]])))




(c/add-css (ns-interns 'components.console))