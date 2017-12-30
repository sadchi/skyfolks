(ns skyfolks.cfg
  (:require [clojure.java.io :as io])
  (:import (java.io FileNotFoundException)))

(defn read-cfg [path]
  (when-not (.exists (io/file path))
    (throw (FileNotFoundException. path)))
  (read-string (slurp path)))
