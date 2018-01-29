(ns components.command-center
  (:require
    [cells.core :as c]
    [clojure.string :as s]
    [cljs.pprint :as pp]
    [components.common-state :as cst]
    ))


(defn echo [& params]
  [:good (s/join " " params)])

(defn def-brash [& params]
  (reset! cst/extra-param (with-out-str (pp/pprint c/empty-cell)))
  [:good "Default brash loaded"])

(defn init-world [& [w h]]
  (if-not (and w h)
    [:bad (str "Required two params")]
    (do
      (reset! cst/world {:w    w
                         :h    h
                         :data (repeatedly (js/parseInt h) #(repeat (js/parseInt w) {}))})
      [:good (str "World initialized with empty " w "x" h " grid")])))


(def command-list {:echo       echo
                   :def-brash  def-brash
                   :init-world init-world})


(defn execute-command [command]
  (let [splitted-command (s/split command #" ")
        cmd (->> (first splitted-command)
                 (keyword)
                 (get command-list))
        params (rest splitted-command)]
    (if (some? cmd)
      (apply cmd params)
      [:bad (str "Unknown cmd: " (first splitted-command))])))
