(ns way.wprint)

(defn wprint [lines]
  (comment let [marker (concat "way※" (repeat "x"))]
   (doall (map #(println %1 "┃" %2) marker (seq lines))))

  (if (seq? lines)
    (doseq [line lines] (println "※┃" line))
    (doseq [line (clojure.string/split lines #"\n")] (println "※┃" line)))
)

(defn whead []
  (println "※┃Wayfinder System Notes")
  (println "※┣━━━━━━━━━━━━━━━━━━━━━━")
)

