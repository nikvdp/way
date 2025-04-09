#!/usr/bin/env bb
(ns way.notecheck
  (:require [clojure.java.io :as io]
            [babashka.fs :as fs]
            [clojure.edn :as edn]))
(bean (fs/last-modified-time ".wayf"))

(defn file-older-than? [fname epoch]
  (let [fmodtime (fs/file-time->millis (fs/last-modified-time fname))]
    (< fmodtime epoch)))

;(file-older-than? ".wayf" (- (System/currentTimeMillis)(* 1000 60 30)))

(defn check [_]
  (when (and (fs/exists? ".wayf")
             (file-older-than? ".wayf" (- (System/currentTimeMillis) (* 1000 60 30))))
    (let [dotfile (fs/file ".wayf")
          notes (-> dotfile slurp edn/read-string)
          reminders (-> notes :usage-reminders keys)
          reminders (->> reminders (map symbol) (map str) (apply str))
          message   (format "Wayfinder notes available here: %s\nRun `wayf show` to read." reminders)
          ]
      (println message)
      (fs/set-last-modified-time dotfile (System/currentTimeMillis))
      )

    )
  )

(defn show [& args]
 (when (fs/exists? ".wayf")
    (let [dotfile (fs/file ".wayf")
          notes (-> dotfile slurp edn/read-string)
          reminders (-> notes :usage-reminders keys)
          reminders (->> reminders (map symbol) (map str) (apply str))
          ]
      (when (empty? args)
        (println "Please choose which usage reminders to show:" reminders)
        (System/exit 1))

      (doseq [arg args
            :let [karg (keyword arg)
                  message-lines (-> notes :usage-reminders karg)]]
        (doseq [line message-lines]
                (println line))
        )
      
      )) 
)
