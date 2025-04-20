(ns way.conf
  (:require [clojure.java.io :as io]
            [babashka.fs :as fs]
            [clojure.edn :as edn]))

(defn find-config []
  (let [cwd (fs/cwd)]
    (loop [wd cwd]
      (cond 
        (.exists (fs/file cwd ".wayf")) (fs/file (fs/cwd) ".wayf")
        (nil? (fs/parent cwd))          nil
        :else (recur (fs/parent cwd))
        ))
    ))


