(ns way.main
 (:gen-class)
 (:require [way.notecheck :as notecheck]
           [way.runner :as runner]
           [way.shell :as shell]
           [way.edit :as edit]
           [way.server :as server]
           [way.wprint :as wprint]
;           [way.search :as search]
          ; [babashka.cli :as cli]
           ))


(defn -main [& args]
  (case (first args)
    "notecheck"     (notecheck/check (rest args))
    "show"          (notecheck/show (rest args))
    "login"         (notecheck/login (rest args))
    "run"           (runner/run (rest args))
    "shell"         (shell/shell (rest args))
    ;"new"           (edit/new (rest args))
    "edit"          (edit/edit (rest args))
    "sync"          (server/sync (rest args))
    "serve"         (server/cli-serve (rest args))
    "systemd-serve" (server/systemd-serve (rest args))

    ;"expert"        (conf/expert (rest args))
    ;"keep" - keep shell history?
    ;  "search"    (search/search (rest args))
    ;  "edit"      (edit/edit (rest args))
    (do
      (println "way: no subcommand match")
      (println "please try one of: notecheck, show, login, run, shell, edit, sync, serve, systemd-serve")
      (System/exit 1)
      )

  ))

