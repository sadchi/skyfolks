(ns components.command-center
  (:require
    [ajax.core :refer [GET POST PUT]]
    [cells.core :as ce]
    [cljs.pprint :as pp]
    [cljs.reader :as cr]
    [clojure.string :as s]
    [components.common-state :as cst]
    [components.renders :as r]
    [components.core :as c]
    ))


(def save-world-url "/world")
(def load-world-url "/world")


(defn echo [& params]
  [[:good (s/join " " params)]])

(defn def-brash [& params]
  (reset! cst/extra-param (with-out-str (pp/pprint ce/empty-cell)))
  [[:good "Default brash loaded"]])

(defn set-brash [& params]
  (let [new-brash (cr/read-string @cst/extra-param)]
    (if new-brash
      (do
        (reset! cst/current-brash new-brash)
        [[:good (str "Set new brash " new-brash)]])
      [[:bad "Wrong data for the new brash"]])))


(defn init-world [& [w h]]
  (if-not (and w h)
    [[:bad (str "Required two params")]]
    (do
      (let [width (js/parseInt w)
            height (js/parseInt h)]
        (reset! cst/world {:w    width
                           :h    height
                           :data (repeatedly height #(repeat width {}))}))
      [[:good (str "World initialized with empty " w "x" h " grid")]])))


(defn render [& [render-key]]
  (if-not render-key
    [[:bad (str "Required parameter is missing: render keyword")]]
    (do
      (let [render (get r/renders-list (keyword render-key) nil)]
        (reset! cst/render (if render render (fn [x] nil)))
        (if render
          [[:good (str "Attempt to set [" render-key "] render")]]
          [[:bad (str "Render [" render-key "] not found")]])))))


(defn save-world [& [filename]]
  (if-not filename
    [[:bad (str "Required parameter is missing: filename")]]
    (let []
      (PUT save-world-url {:params        {:filename filename
                                           :world    @cst/world}
                           :format        :json
                           :handler       (fn [x]
                                            (swap! cst/log conj [:good "World saved successfully"]))
                           :error-handler (fn [x]
                                            (swap! cst/log conj [:bad (str "Error during saving the world: " x)]))
                           })
      [[:neutral "Command save-world issued"]])))


(defn load-world [& [filename]]
  (GET load-world-url {:params          {:filename filename}
                       :response-format :json
                       :keywords?       true
                       :handler         (fn [x]
                                          ;(c/log "load-world x " x)
                                          (swap! cst/log conj [:good "World loaded successfully"])
                                          (reset! cst/world x)
                                          )
                       :error-handler   (fn [x]
                                          (swap! cst/log conj [:bad (str "Error during loading the world: " x)]))})
  [[:neutral "Command load-world issued"]])

(def command-list {
                   :def-brash  def-brash
                   :echo       echo
                   :init-world init-world
                   :load-world load-world
                   :render     render
                   :save-world save-world
                   :set-brash  set-brash
                   })


(defn execute-command [context]
  (c/log "execute-command context: " context)
  (let [command (:command context)
        splitted-command (s/split command #" ")
        cmd (->> (first splitted-command)
                 (keyword)
                 (get command-list))
        params (rest splitted-command)]
    (if (some? cmd)
      (apply cmd params)
      [[:bad (str "Unknown cmd: " (first splitted-command))]])))
