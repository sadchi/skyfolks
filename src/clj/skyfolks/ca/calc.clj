(ns skyfolks.ca.calc)

(defn if-any-1 [coll]
  (if-not (empty? (filter #(some-> % pos? ) (concat (take 4 coll) (drop 5 coll)))) 1 0))

(defn calc [f prepared-coll]
  (map #(map (fn [x] (f x)) %) prepared-coll))