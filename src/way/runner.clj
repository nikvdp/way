(ns way.runner
  (:require [clojure.java.io :as io]
            [babashka.fs :as fs]
            [clojure.edn :as edn]))

(defn run [args]
 (when (fs/exists? ".wayf")
    (let [dotfile (fs/file ".wayf")
          notes (-> dotfile slurp edn/read-string)
          commands (-> notes :commands keys)
          command-str (->> commands (map symbol) (map str) 
                         (interleave (repeat " ")) (apply str))
          ]
      (when (empty? args)
        (println "Please choose which usage command to run" command-str)
        (System/exit 1))

      (doseq [arg args
            :let [karg (keyword arg)
                  message-lines (-> notes :usage-reminders karg)]]
        (doseq [line message-lines]
                (println line))
        (println))
      )) 
)
