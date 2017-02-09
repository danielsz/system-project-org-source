(ns org-mode.core
   (:require [dommy.core :as dommy :refer-macros [sel sel1]]))

(enable-console-print!)

(defn set-visibility [el value]
  (set! (.. el -style -display) value))

(defn toggle-visibility [el]
  (let [value (.. el -style -display)]
    (if (= value "none")
      (set-visibility el "initial")
      (set-visibility el "none"))))

(defn hide-all-but-first-article []
  (let [articles (drop 1 (sel :article))]
    (doseq [article articles]
      (set! (.. article -style -display) "none"))))

(defn hide-all-articles []
  (doseq [article (sel :article)]
    (set! (.. article -style -display) "none"))
  )
(defn hide-all-but-article [el]
  (hide-all-articles)
  (toggle-visibility el))

(defn handler [e]
  (let [hash (.. e -target -hash)]
    (hide-all-but-article (sel1 (keyword (str "#outline-container-" (subs hash 1)))))) )

(defn attach-hover-listeners []
  (let [els (sel [:#table-of-contents :li :a])]
    (doseq [el els]
      (dommy/listen! el :mouseover handler :click handler))))

(defn init []
  (hide-all-but-first-article)
  (attach-hover-listeners))

(set! (.-onload js/window) init)

