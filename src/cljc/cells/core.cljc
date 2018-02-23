(ns cells.core)


(def empty-cell {:back      nil
                 :mid-back  nil
                 :inner     {:type     "landscape"
                             :material "granite"
                             :state    "solid"
                             :attr     {:thermal-conductivity 0
                                        :temperature          0}}
                 :mid-front nil
                 :front     nil})


(defn landscape? [cell]
  (-> cell
      (:inner)
      (:type)
      (= "landscape")))

(defn thermal-conductivity [cell]
  (get-in cell [:inner :attr :thermal-conductivity]))

(defn temperature [cell]
  (get-in cell [:inner :attr :temperature]))