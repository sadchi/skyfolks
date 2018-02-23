(ns components.console
  (:require
    [components.common-styles :as cs]
    [components.core :as c]
    [components.inputs :as in]
    [components.params :as p]
    [garden.color :as gc]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    [keybind.core :as key]
    [reagent.core :as r]
    [utils.ring-buffer :as rb]
    ))


(def default-params
  {:cmd-buffer 50
   :cmd-height 32
   :padding    16
   :accent     "#607D8B"
   :reputation {:good    "#009688"
                :bad     "#F44336"
                :warning "#2196F3"
                :error   "#FFC107"
                :neutral "#606060"}})

(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))


(def console-s ^:css [cs/flex-box
                      {:flex-direction "column"
                       :height         "100%"}])

(def console-s__log ^:css [cs/mono-font

                           {:padding-left (px (v :padding))
                            ;:flex            1
                            :flex-grow    1
                            ;:flex-shrink     1
                            ;:flex-direction  "column"
                            ;:justify-content "flex-end"
                            :overflow-y   "scroll"
                            :overflow-x   "auto"
                            }])

(def console-s__log__item ^:css {:color (v :reputation :neutral)})


(def console-s__log__item--good ^:css {:color (v :reputation :good)})
(def console-s__log__item--bad ^:css {:color (v :reputation :bad)})
(def console-s__log__item--warning ^:css {:color (v :reputation :warning)})
(def console-s__log__item--error ^:css {:color (v :reputation :error)})


(def console-s__cmd ^:css [cs/mono-font
                           {:position    "relative"
                            :flex-shrink 0
                            :height      (px (v :cmd-height))
                            :line-height (px (v :cmd-height))
                            }
                           [:&:before {:position     "absolute"
                                       :content      "\" \""
                                       :top          "-1px"
                                       :bottom       "-1px"
                                       :left         0
                                       :right        0
                                       :border-width "1px 0 1px 0"
                                       :border-style "solid"
                                       :border-color "rgba(0,0,0,0.3)"
                                       }]])


(def console-s__cmd__input ^:css [cs/mono-font
                                  {:outline      "none"
                                   :position     "absolute"
                                   :top          0
                                   :bottom       0
                                   :left         0
                                   :right        0
                                   :width        "100%"
                                   :padding-left (px (v :padding))
                                   :border-width "0 0 0 4px"
                                   :border-style "solid"
                                   :border-color "transparent"
                                   :box-sizing   "border-box"}
                                  [:&:focus {:border-color (cs/rgba (v :accent) 1)}]])

(defn console-cmd [handler]
  (let [val (r/atom nil)
        read-pointer (r/atom 0)
        commands (r/atom (rb/ring-buffer (v :cmd-buffer)))

        next-pointer (fn [x] (min (inc x) (count @commands)))
        prev-pointer (fn [x] (max (dec x) 0))

        add-command-to-buffer (fn []
                                ;(c/log "Add to buffer")
                                (swap! commands conj @val)
                                (swap! read-pointer inc)
                                (reset! val "")
                                ;(c/log "commands: " @commands)
                                )
        next-command-from-buffer (fn []
                                   ;(c/log "Getting next command")
                                   ;(c/log "commands: " @commands)
                                   (reset! val (nth @commands (swap! read-pointer next-pointer))))
        prev-command-from-buffer (fn []
                                   ;(c/log "Getting prev command")
                                   ;(c/log "commands: " @commands)
                                   (reset! val (nth @commands (swap! read-pointer prev-pointer))))
        ]
    (r/create-class
      {:component-did-mount (fn [this]
                              (key/bind! "f7" ::console-cmd (fn []
                                                              (-> (r/dom-node this)
                                                                  (.getElementsByTagName "input")
                                                                  (aget 0)
                                                                  (.focus)))))
       :component-function  (fn []
                              [:div (c/cls 'console-s__cmd)
                               [:input (c/cls 'console-s__cmd__input
                                              :type "text"
                                              :spellCheck "false"
                                              :autoCapitalize "off"
                                              :autoCorrect "off"
                                              :autoComplete "off"
                                              :value @val
                                              :on-key-down (fn [e]
                                                             (condp = (.-which e)
                                                               13 (do
                                                                    (handler @val)
                                                                    (add-command-to-buffer))
                                                               38 (prev-command-from-buffer)
                                                               40 (next-command-from-buffer)
                                                               27 (reset! val "")
                                                               nil))
                                              :on-change (fn [x] (reset! val (-> x .-target .-value))))]])})))

(defn console-log-item [[reputation text]]
  [:div (c/cls (case reputation
                 :good 'console-s__log__item--good
                 :bad 'console-s__log__item--bad
                 :warning 'console-s__log__item--warning
                 :error 'console-s__log__item--error
                 nil)) text])

(defn console-log [log-a]
  "log is an atom , should contain list of items: [reputation text]"
  (let [scroll-down (fn [node] (aset node "scrollTop" (.-scrollHeight node)))]
    (r/create-class
      {:component-did-mount  (fn [this]
                               (js/setTimeout (partial scroll-down (r/dom-node this)) 50))
       :component-did-update (fn [this _]
                               (js/setTimeout (partial scroll-down (r/dom-node this)) 50))
       :component-function   (fn [log-a]
                               [:div (c/cls 'console-s__log)
                                (doall (for [[idx item] (map-indexed vector @log-a)]
                                         ^{:key idx} [console-log-item item]))
                                ])})))




(defn console [& opts]
  (fn [& opts]
    (let [{:keys [log-a handler]} opts]
      [:div (c/cls 'console-s)
       [console-log log-a]
       [console-cmd handler]])))




(c/add-css (ns-interns 'components.console))