(ns complete.jvm.java
  (:require [complete.jvm.jar-file :as jar-file]
            [clojure.string :as string])
  (:import [java.io File]
           [org.apache.bcel.classfile ClassParser]))

(defn- class-jar-entry? [e]
  (and (-> e .isDirectory not)
       (-> e .getName (.endsWith ".class"))
       (-> e .getName (.contains "__") not)))

(defn- jar-class-names [jar-fname]
  (->> (jar-file/zip-entries jar-fname)
       (filter class-jar-entry?)
       (map #(-> (ClassParser. jar-fname (.getName %)) .parse .getClassName))
       (remove #(re-find #"\$" %))))

(defn class-names []
  (mapcat jar-class-names (jar-file/jars)))
