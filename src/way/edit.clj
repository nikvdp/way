(ns way.edit
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn])
  )

(defn launch-editor [filename]
  (let [editor          (or (System/getenv "VISUAL") "vim")
        process-builder (java.lang.ProcessBuilder. (list editor filename))
        inherit         (java.lang.ProcessBuilder$Redirect/INHERIT)
       ]
    (.redirectOutput process-builder inherit)
    (.redirectInput process-builder inherit)
    (.redirectError process-builder inherit)
    (.waitFor (.start process-builder))
  ))

(defn new [args]
  (launch-editor)
    )


(defn edit-direntry []
  (let [tmpfile (java.io.File/createTempFile "wayfinder-direntry" "txt")
        tmpname (str tmpfile)]
    (spit tmpfile "#Enter text to be displayed upon entry to this directory\n#All comment lines such as this one will be stripped")
    (launch-editor tmpname)
    ;TODO merge content of tmpfile to .wayf file
    (let [wayf (-> ".wayf" slurp edn/read-string)])

    ))

(defn edit [args]
  (case (first args)
    "direntry" (edit-direntry)
    ))

