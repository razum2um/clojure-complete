(defproject clojure-complete "0.2.6-SNAPSHOT"
  :description "Standalone completion library adapted from swank-clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :url "https://github.com/ninjudd/clojure-complete"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [robertluo/fun-map "0.1.6"]
                 [org.apache.bcel/bcel "6.2"]]
  :profiles {
    :1.2 {:dependencies [[org.clojure/clojure "1.2.0"]]}
    :1.3 {:dependencies [[org.clojure/clojure "1.3.0"]]}
    :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}})
