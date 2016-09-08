(ns desa.dev
  (:require [schema.core :as s]
            [app.core :as core]))

(s/set-fn-validation! true)

(enable-console-print!)

(defn main [] (core/main))
