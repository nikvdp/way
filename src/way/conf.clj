(ns way.conf
  (:require [clojure.java.io :as io]
            [babashka.fs :as fs]
            [clojure.edn :as edn]))

(defn find-wayf 
  "Search recursively upward for .wayf file"
  []
  (let [cwd (fs/cwd)]
    (loop [wd cwd]
      (println wd)
      (cond 
        (.exists (fs/file wd ".wayf")) (fs/file wd ".wayf")
        (nil? (fs/parent wd))          nil 
        :else (recur (fs/parent wd))
        ))
    ))

(def template-wayf 
  {:search-keys []
   :direntry-note "Note displayed upon entry to this directory"
   :usage-reminders {:example ["Structured usage reminders"]}
   :commands {:lsa "ls -a"}
   })

(defn create-wayf []
  (spit ".wayf" template-wayf)
  (fs/file ".wayf")
  )

(defn expert
  "Set or toggle 'expert mode' setting"
  [args]


  )
