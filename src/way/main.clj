(ns way.main
 (:require [way.notecheck :as notecheck]
           [way.runner :as runner]
;           [way.edit :as edit]
          ; [babashka.cli :as cli]
           )
)
(defn -main [& args]
(case (first args)
  "notecheck" (notecheck/check (rest args))
  "show"      (notecheck/show (rest args))
  "run"       (runner/run (rest args))
;  "edit"      (edit/edit (rest args))
  ;"keep" - keep shell history?
  (println "no match")
  )
  )


