(set-env!
 :source-paths  #{"src/cljs"}
 :dependencies '[[binaryage/devtools "0.9.1" :scope "test"]
                 [binaryage/dirac "1.1.5" :scope "test"]
                 [powerlaces/boot-cljs-devtools "0.2.0" :scope "test"]
                 [adzerk/boot-cljs "1.7.228-2" :scope "test"]
                 [adzerk/boot-reload "0.5.1" :scope "test"]
                 [pandeiro/boot-http "0.7.6"]
                 [org.clojure/clojurescript "1.9.473"]
                 [org.danielsz/cljs-utils "0.1.0-SNAPSHOT"]
                 [prismatic/dommy "1.1.0"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[pandeiro.boot-http :refer :all]
         '[powerlaces.boot-cljs-devtools :refer [cljs-devtools dirac]]
         '[adzerk.boot-reload :refer [reload]])

(deftask build []
  (comp
   (cljs :optimizations :advanced)
   (target :no-clean true :dir #{"."})))

(deftask dev
  "Run a restartable system in the Repl"
  []
  (reset! boot.repl/*default-middleware* '[dirac.nrepl/middleware])
  (comp
   (watch)
   (serve :dir ".")
   (reload :ids #{"js/main"})
   (cljs-devtools)
   (dirac)
   (cljs :optimizations :none :ids #{"js/main"})
   (notify :visual true)
   (repl :server true)))
