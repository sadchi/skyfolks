(ns skyfolks.cfg
  (:require [brain-store.common.fs :as fs])
  (:import (java.io FileNotFoundException)))

(defn read-cfg [path]
  (when-not (.exists (fs/file path))
    (throw (FileNotFoundException. path)))
  (read-string (slurp path)))
