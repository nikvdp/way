(ns way.shell
  (:require [clojure.java.io :as io]
            [babashka.fs :as fs]
            [way.terminal :as term]
            [clojure.edn :as edn]))

(defn help [args]
  (case (first args)
    "bash" "run: eval $( way shell bash )"
    " the 'shell' command outputs commands to integrate the wayfinder into your shell\n try 'way shell bash' or 'way help bash'\n supported shells: bash"
    ))

(defn shell [args]
  (println 
    (case (first args)
      "help" (help (rest args))
      "bash" "function cd_wayfinder() { cd \"$@\" && [ -f .wayf ] && way notecheck; }; alias cd=cd_wayfinder; alias wayr='way run'"
      "other shells unsupported as of yet; try 'way shell help'"
    )
  )
)


