(ns components.command-center
  (:require
    [clojure.string :as s]
    ))


(defn echo [& params]
  [:good (s/join " " params)])

(def command-list {:echo echo})


(defn execute-command [command]
  (let [splitted-command (s/split command #" ")
        cmd (->> (first splitted-command)
                 (keyword)
                 (get command-list))
        params (rest splitted-command)]
    (if (some? cmd)
      (apply cmd params)
      [:bad (str "Unknown cmd: " (first splitted-command))])))
