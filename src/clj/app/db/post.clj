(ns app.db.post
  (:refer-clojure :exclude [update])
  (:require [korma.core :refer [defentity many-to-many select where limit insert values update delete set-fields]]
            [app.db.entities :refer [post]]))

(defn get-all-posts [] (select post))

(defn get-post-by-id [id] (first (select post (where {:id id}) (limit 1))))

(defn create-post [title content created_at draft ]
  (insert post (values {:title title :content content :created_at created_at :draft draft })))

(defn update-post [id fields]
  (update post (set-fields fields) (where {:id id})))

(defn delete-post [id] (delete post (where {:id id})))