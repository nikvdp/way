(ns way.main
 (:require [way.notecheck :as notecheck]
           [way.runner :as runner]
           [way.shell :as shell]
;           [way.edit :as edit]
;           [way.search :as search]
          ; [babashka.cli :as cli]
           )
)
(defn -main [& args]
(case (first args)
  "notecheck" (notecheck/check (rest args))
  "show"      (notecheck/show (rest args))
  "run"       (runner/run (rest args))
  "shell"     (shell/shell (rest args))
;  "search"    (search/search (rest args))
;  "edit"      (edit/edit (rest args))
  ;"keep" - keep shell history?
  (println "no match")
  )
  )


