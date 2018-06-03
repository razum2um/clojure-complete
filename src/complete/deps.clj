(ns complete.deps
  (:require [robertluo.fun-map :refer [fun-map]]
            [complete.jvm.java :as java]
            [complete.jvm.clojure :as clojure]))

(def deps (fun-map {:java-class-names (future (into [] (java/class-names)))
                    :clojure-namespaces (future (into [] (clojure/clojure-namespaces)))}))

