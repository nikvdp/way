(ns way.main
 (:require [way.notecheck :as notecheck]
          ; [babashka.cli :as cli]
           )
)
(defn -main [& args]
(case (first args)
  "notecheck" (apply notecheck/check (rest args))
  "show"      (apply notecheck/show (rest args))
  ;"run"       (runner/run (rest *command-line-args*))
  ;"keep" - keep shell history?
  (println "no match")
  )
  )


