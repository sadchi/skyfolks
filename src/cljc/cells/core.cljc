(ns cells.core)


(def empty-cell {:back      nil
                 :mid-back  nil
                 :inner     {:type     :landscape
                             :material :granite
                             :state    :solid}
                 :mid-front nil
                 :front     nil})


(defn landscape? [cell]
  (-> cell
      (:inner)
      (:type)
      (= :lanscape)))