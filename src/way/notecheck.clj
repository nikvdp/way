(ns way.notecheck
  (:require [clojure.java.io :as io]
            [babashka.fs :as fs]
            [way.conf :as conf]
            [way.terminal :as term]
            [way.wprint :as wprint]
            [clojure.edn :as edn]))

;(bean (fs/last-modified-time ".wayf"))

(defn file-older-than? [fname epoch]
  (let [fmodtime (fs/file-time->millis (fs/last-modified-time fname))]
    (< fmodtime epoch)))

;(file-older-than? ".wayf" (- (System/currentTimeMillis)(* 1000 60 30)))

;way show||all bbtest bbrepl touch
;way run ||lsr ls vimt

(defn check [_]
  (when (and (fs/exists? ".wayf")
             (file-older-than? ".wayf" (- (System/currentTimeMillis) (* 1000 60 30))))
    (let [dotfile (fs/file ".wayf")
          notes (-> dotfile slurp edn/read-string)
          reminders (-> notes :usage-reminders keys)
          reminders (->> reminders (map symbol) (map str) (interleave (repeat " ")) (apply str))
          commands (-> notes :commands keys)
          commands (->> commands (map symbol) (map str) 
                         (interleave (repeat " ")) (apply str))
          message   (str
                      (format "Wayfinder notes available here: %s\nRun `way show` to read.\n" reminders)
                      (format "Wayfinder commands available here: %s\nRun `way run` to execute" commands)
                      )
          ]
      (wprint/wprint message)
      (when-let [dirnote (:direntry-note notes)]
        (println (str term/bold dirnote term/reset)))
      (fs/set-last-modified-time dotfile (System/currentTimeMillis))
      )

    )
  )

(defn show [args]
 (when (fs/exists? ".wayf")
    (let [dotfile (fs/file ".wayf")
          notes (-> dotfile slurp edn/read-string)
          reminders (-> notes :usage-reminders keys)
          args (if (= "all" (first args)) reminders args)]

      (when (empty? args)
        (let [reminder-str (apply str 
                                  (->> reminders 
                                       (map symbol) 
                                       (map str) 
                                       (interleave (repeat " "))))]
          (println "Please choose which usage reminders to show:" reminder-str))
        (System/exit 1))

      (doseq [arg args
            :let [karg (keyword arg)
                  message-lines (-> notes :usage-reminders karg)
                  cmd?          (-> notes :commands karg)
                  ]]
        ;(println (str term/bold (first message-lines) term/reset ))
        (wprint/whead (-> arg name str))
        ;(doseq [line message-lines] (println line))
        (wprint/wprint message-lines)
        (when cmd?
          (wprint/wblank )
          (wprint/wsub "associated command:")
          (wprint/wprint cmd?))
        (println))
      )))

(defn login 
  "Show system-specific notes from central config file 
  as well as notes from central network server
  at login time."
  [args]
  (let [confdata (conf/load-conf)]
    (when confdata
      (wprint/whead "System Wayfinder Login Notes")
      (-> confdata :login-message wprint/wprint))))


