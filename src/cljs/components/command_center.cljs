(ns components.command-center
  (:require
    [cljs.reader :as reader]
    [clojure.string :as s]
    [instaparse.core :as i]
    [instaparse.failure :as f]
    ))


(defn echo [& params]
  )

(def command-list {:echo echo})

(def command-list-grammar
  (s/join " | " (map #(str "'" (name %) "'") (keys command-list))))

(def g
  (str
    "command : keyword params"
    "keyword : " command-list-grammar "\n"
    "
    command          : keyword <space>+ params*
    params           : Epsilon | param | param (<space>+ param)*
    param            : param-syms | <quote> non-quote-syms <quote>
    <param-syms>     : #'[^ \"]+'
    <non-quote-syms> : #'[^\"]+'
    <quote>          : '\"'
    <space>          : ' '
  "))


(defonce parser (i/parser g))