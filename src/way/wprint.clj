(ns way.wprint)

(defn wprint [lines]
  (comment let [marker (concat "way※" (repeat "x"))]
   (doall (map #(println %1 "┃" %2) marker (seq lines))))

  (if (string? lines) 
    (doseq [line (clojure.string/split lines #"\n")] (println " ┃" line))
    (doseq [line lines] (println " ┃" line))
    )
)

(defn wdecor [line]
  (println (apply str "※┃" line)))

(defn whead [message]
  (println (apply str "※┃" message))
  (println (apply str "▶┣" (repeat (count message) "━")))
)

(defn wsub [message]
  (println (apply str "※┃" message))
  (println (apply str "▶┠" (repeat (count message) "─")))
)

(defn wblank []
  (println " ┃" ))
