(set-env!
 :source-paths  #{"src/cljs"}
 :dependencies '[[binaryage/devtools "0.9.0" :scope "test"]
                 [binaryage/dirac "1.1.3" :scope "test"]
                 [powerlaces/boot-cljs-devtools "0.2.0" :scope "test"]
                 [adzerk/boot-cljs "1.7.228-2" :scope "test"]
                 [adzerk/boot-reload "0.5.1" :scope "test"]
                 [pandeiro/boot-http "0.7.6"]
                 [org.clojure/clojurescript "1.9.456"]])

(swap! boot.repl/*default-middleware* conj 'dirac.nrepl/middleware)

(require '[adzerk.boot-cljs :refer [cljs]]
         '[pandeiro.boot-http :refer :all]
         '[powerlaces.boot-cljs-devtools :refer [cljs-devtools dirac]]
         '[adzerk.boot-reload :refer [reload]])

(deftask build []
  (comp
   (cljs :optimizations :advanced)
   (target :dir #{"js"})))

(deftask dev
  "Run a restartable system in the Repl"
  []
  (comp
   (watch)
   (serve :dir ".")
   (reload)
   (cljs-devtools)
   (dirac)
   (cljs :optimizations :none)
   (notify :visual true)
   (repl :server true)))

