(ns complete.jvm.jar-file
  (:import [java.util.zip ZipInputStream]
           [java.io FileInputStream]
           [org.apache.bcel.classfile ClassParser]))

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

(defn- class-jar-entry? [e]
  (and (-> e .isDirectory not)
       (-> e .getName (.endsWith ".class"))))

(defn jar-class-names [jar-fname]
  (->> (zip-entries jar-fname)
       (filter class-jar-entry?)
       (map #(-> (ClassParser. jar-fname (.getName %)) .parse .getClassName))))
