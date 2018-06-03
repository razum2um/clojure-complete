(ns complete.jvm.java
  (:require [complete.jvm.jar-file :as jar-file]
            [clojure.string :as string])
  (:import [java.io File]))

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

(defn class-names []
  (mapcat jar-file/jar-class-names (jars)))
