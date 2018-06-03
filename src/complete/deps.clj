(ns complete.deps
  (:require [robertluo.fun-map :refer [fun-map]]
            [complete.jvm.java :as java]))

(def deps (fun-map {:java-class-names (future (into [] (java/class-names)))}))

