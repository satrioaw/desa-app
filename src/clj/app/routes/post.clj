(ns app.routes.post
  (:refer-clojure :exclude [update])
  (:require [compojure.core :refer [routes GET POST DELETE]]
            [noir.response :as resp]
            [ring.util.response :refer [response content-type]]
            [taoensso.timbre :as timb]
            [app.db.post :as db]
            [app.layout :as layout]))

(defn convert-boolean [b] (if (= "on" b) true false))

(defn index-page []
  (layout/render "post/index.html" {:posts (db/get-all-posts) :cols ["title" "content" "created_at" "draft" ]}))

(defn create-page []
  (layout/render "post/create.html" {:create_update "Create"}))

(defn update-page [id]
  (let [post (db/get-post-by-id id)]
    (layout/render "post/create.html" {:post post :create_update "Update"})))

(defn delete-page [id]
  (layout/render "post/delete.html" {:id id}))

(defn create [title content created_at draft ]
  (try
    (db/create-post title content created_at (convert-boolean draft) )
    (catch Exception e (timb/error e "Something went wrong creating post.")
                       (layout/flash-result (str "An error occured.") "alert-danger")))
  (resp/redirect "/post"))

(defn update [id title content created_at draft ]
  (try
    (db/update-post id {:title title :content content :created_at created_at :draft (convert-boolean draft)  })
    (catch Exception e (timb/error e (str "Something went wrong updating: " id))
                       (layout/flash-result (str "An error occured.") "alert-danger")))
  (resp/redirect "/post"))

(defn delete [id delete_cancel]
  (when (= "Delete" delete_cancel)
    (try
     (db/delete-post id)
     (catch Exception e (timb/error e (str "Something went wrong deleting: " id))
                        (layout/flash-result (str "An error occured.") "alert-danger"))))
  (resp/redirect "/post"))

(defn post-routes []
  (routes
    (GET "/post" [] (index-page))
    (GET "/post/create" [] (create-page))
    (GET "/post/:id" [id] (update-page id))
    (POST "/post/create" [title content created_at draft ] (create title content created_at draft ))
    (GET "/post/delete/:id" [id] (delete-page id))
    (POST "/post/delete" [id delete_cancel] (delete id delete_cancel))
    (POST "/post/update" [id title content created_at draft ] (update id title content created_at draft ))))
