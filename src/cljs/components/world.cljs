(ns components.world
  (:require
    [components.common-styles :as cs]
    [components.core :as c]
    [components.inputs :as in]
    [components.params :as p]
    [garden.color :as gc]
    [garden.core :refer [css]]
    [garden.units :refer [px]]
    [keybind.core :as key]
    [reagent.core :as r]

    ))

(def default-params
  {})

(def params (merge default-params (get p/params (name (namespace ::x)))))

(defn v [& k] (get-in params k))


(defn update-world
  ([world row col cell]
   (let [data (:data world)
         [before after] (split-at row data)
         target-row (first after)
         after (rest after)]
     (assoc world :data
                  (concat
                    before
                    (list (concat
                            (take col target-row)
                            (list cell)
                            (drop (inc col) target-row)
                            ))
                    after))))
  ([world start-row start-col end-row end-col cell]
   (let [data (:data world)
         rows-before (take start-row data)
         rows-to-change (take (- end-row start-row) (drop start-row data))

         rows-after (drop end-row data)


         rows-changed (map (fn [x]
                             (let [before (take start-col x)
                                   after (drop end-col x)]
                               (doall (concat before (take (- end-col start-col) (repeat cell)) after)))) rows-to-change)
         ]
     (assoc world :data (doall (concat rows-before rows-changed rows-after)))))
  )





(c/add-css (ns-interns 'components.world))