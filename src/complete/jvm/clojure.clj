(ns complete.jvm.clojure
  (:require [clojure.java.io :as io]
            [complete.jvm.jar-file :as jar-file])
  (:import [clojure.lang LispReader]
           [java.util.zip ZipFile]
           [java.io PushbackReader]))

(defn- try-read [rdr] (if (.ready? rdr) (LispReader/read rdr nil) ::nil))

(defn jar-entry->clj-ns [jar-name zip-entry]
  (with-open [z (ZipFile. jar-name)
              input (.getInputStream z zip-entry)
              rdr (PushbackReader. (io/reader input))]
    (->> (repeatedly #(try-read rdr))
         (take-while (complement #{::nil}))
         (filter #(-> % first (= 'ns)))
         (map second))))

(defn- clojure-jar-entry? [e]
  (and (-> e .isDirectory not)
       (-> e .getName (.endsWith ".clj"))))

(defn- clj-namespaces-in-jar [jar-name]
  (->> (jar-file/zip-entries jar-name)
       (filter clojure-jar-entry?)
       (map #(jar-entry->clj-ns jar-name %))))

(defn clojure-namespaces []
  (mapcat clj-namespaces-in-jar (jar-file/jars)))
