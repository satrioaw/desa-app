(ns app.components.components
  (:require
    [com.stuartsierra.component :as component]
    (system.components
      [repl-server :refer [new-repl-server]])
    [app.components.server :refer [new-web-server]]
    [app.components.handler :refer [new-handler]]
    [app.components.config :as c]
    [app.components.db :refer [new-db]]
    [app.components.locale :as l]))


(defn dev-system []
  (component/system-map
    :locale (l/new-locale)
    :config (c/new-config (c/prod-conf-or-dev))
    :db (component/using (new-db) [:config])
    :handler (component/using (new-handler) [:config :locale])
    :web (component/using (new-web-server) [:handler :config])))


(defn prod-system []
  (component/system-map
    :locale (l/new-locale)
    :config (c/new-config (c/prod-conf-or-dev))
    :db (component/using (new-db) [:config])
    :handler (component/using (new-handler) [:config :locale])
    :web (component/using (new-web-server) [:handler :config])))
