(ns app.core
  (:require [taoensso.timbre :as timbre]
            [reloaded.repl :refer [go]]
            [app.cljccore :as cljc]
            [app.components.components :refer [prod-system]])
  (:gen-class))

(defn -main [& args]
  (reloaded.repl/set-init! prod-system)
  (go)
  (cljc/foo-cljc "hello from cljx")
  (timbre/info "server started."))
