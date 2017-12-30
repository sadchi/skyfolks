(ns skyfolks.ca.split)


(defn split-by-3 [coll]
  (partition 3 1 (concat (list nil) coll (list nil))))

(defn split-rows [coll]
  (partition 3 1 (concat (list (repeat nil))
                         coll
                         (list (repeat nil)))))

(defn prepare [coll]
  (->> (split-rows coll)
       (map (fn [x] (map #(split-by-3 %) x)))
       (map (fn [x] (apply map concat x)))))
