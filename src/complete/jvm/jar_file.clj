(ns complete.jvm.jar-file
  (:require [clojure.string :as string])
  (:import [java.util.zip ZipInputStream]
           [java.io FileInputStream File]))

(defprotocol ZipEntries
  (zip-entries [_]))

(extend-protocol ZipEntries
  nil
  (zip-entries [_] '())

  ZipInputStream
  (zip-entries [zip-stream]
    (lazy-seq (if-let [e (.getNextEntry zip-stream)]
                (cons e (zip-entries zip-stream)))))

  String
  (zip-entries [jar-fname]
    (with-open [zip-stream (-> jar-fname (FileInputStream.) (ZipInputStream.))]
      (into [] (zip-entries zip-stream)))))

(defn- jars-from-path [path-name]
  (when-let [path (System/getProperty path-name)]
    (->> (string/split path #":")
         (filter #(string/ends-with? % ".jar")))))

(defn- file? [fname] (.isFile (File. fname)))
(defn- bootclass-jars [] (jars-from-path "sun.boot.class.path"))
(defn- classpath-jars [] (jars-from-path "java.class.path"))
(defn jars []
  (->> (concat (bootclass-jars) (classpath-jars))
       (filter file?)
       distinct))
