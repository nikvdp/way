(ns way.runner
  (:require [clojure.java.io :as io]
            [babashka.fs :as fs]
            [babashka.process :as ps]
            [way.conf :as conf]
            [clojure.edn :as edn]))

(defn exec [cmdstr]
  (let [
        process-builder (java.lang.ProcessBuilder. (list "sh" "-c" cmdstr))
        inherit         (java.lang.ProcessBuilder$Redirect/INHERIT)
       ]
    (.redirectOutput process-builder inherit)
    (.redirectInput process-builder inherit)
    (.redirectError process-builder inherit)
    (.waitFor (.start process-builder))
  ))

(defn run [args]
 (when-let [wayf (conf/find-wayf)]
    (let [
          notes (-> wayf slurp edn/read-string)
          commands (-> notes :commands keys)
          ]

      ;print out list of commands when none is given
      (when (empty? args)
        (doseq [command commands]
          (println (format "\t%s: %s" (symbol command) (get-in notes [:commands command]))))
        (System/exit 1))

      (let [command (get-in notes [:commands (keyword (first args))])]
        (when (nil? command)
          (println (str "Could not find command to match '" (first args) "' in .wayf"))
          (System/exit 1))
        (exec command)
        )
      )) 
)

