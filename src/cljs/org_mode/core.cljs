(ns org-mode.core
   (:require [dommy.core :refer-macros [sel sel1]]))

(enable-console-print!)

(defn init []
  (println "Hello, world"))

(set! (.-onload js/window) init)

