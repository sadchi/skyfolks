(ns components.command-center
  (:require
    [cells.core :as c]
    [cljs.pprint :as pp]
    [cljs.reader :as cr]
    [clojure.string :as s]
    [components.common-state :as cst]
    [components.renders :as r]
    ))


(defn echo [& params]
  [:good (s/join " " params)])

(defn def-brash [& params]
  (reset! cst/extra-param (with-out-str (pp/pprint c/empty-cell)))
  [:good "Default brash loaded"])

(defn set-brash [& params]
  (let [new-brash (cr/read-string @cst/extra-param)]
    (if new-brash
      (do
        (reset! cst/current-brash new-brash)
        [:good (str "Set new brash " new-brash)])
      [:bad "Wrong data for the new brash"])))


(defn init-world [& [w h]]
  (if-not (and w h)
    [:bad (str "Required two params")]
    (do
      (let [width (js/parseInt w)
            height (js/parseInt h)]
        (reset! cst/world {:w    width
                           :h    height
                           :data (repeatedly height #(repeat width {}))}))
      [:good (str "World initialized with empty " w "x" h " grid")])))


(defn render [& [render-key]]
  (if-not render-key
    [:bad (str "Required parameter is missing: render keyword")]
    (do
      (let [render (get r/renders-list (keyword render-key))]
        (reset! cst/render (if render render (fn [x] nil))))
      (if render
        [:good (str "Attempt to set [" render-key "] render")]
        [:bad (str "Render [" render-key "] not found")]))))


(def command-list {
                   :def-brash  def-brash
                   :echo       echo
                   :init-world init-world
                   :render     render
                   :set-brash  set-brash
                   })


(defn execute-command [command]
  (let [splitted-command (s/split command #" ")
        cmd (->> (first splitted-command)
                 (keyword)
                 (get command-list))
        params (rest splitted-command)]
    (if (some? cmd)
      (apply cmd params)
      [:bad (str "Unknown cmd: " (first splitted-command))])))
