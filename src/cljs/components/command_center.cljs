(ns components.command-center
  (:require
    [clojure.string :as s]
    ))


(defn echo [& params]
  (s/join " " params))

(def command-list {:echo echo})


(defn execute-command [command]
  (let [splitted-command (s/split command #" ")
        cmd (->> (first splitted-command)
                 (keyword)
                 (get command-list))
        params (rest splitted-command)]
    (when (some? cmd)
      (apply cmd params))))


;(def command-list-grammar
;  (s/join " | " (map #(str "'" (name %) "'") (keys command-list))))
;
;(def g
;  (str
;    "command : keyword params\n"
;    "keyword : " command-list-grammar "\n"
;    "
;    command          : keyword <space>+ params*
;    params           : Epsilon | param | param (<space>+ param)*
;    param            : param-syms | <quote> non-quote-syms <quote>
;    <param-syms>     : #'[^ \"]+'
;    <non-quote-syms> : #'[^\"]+'
;    <quote>          : '\"'
;    <space>          : ' '
;  "))
;
;
;(defonce parser (i/parser g))