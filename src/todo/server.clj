(ns todo.server
  (:require [todo.store :as store]
            [ring.util.response :as res]
            [todo.view :as view]
            [bidi.ring :refer [make-handler]]
            [ring.middleware.params :refer [wrap-params]]
            [com.stuartsierra.component :as component]
            [aleph.http :as http]))

(defn handle-index
  "We get there when we are displaying the index page, prompting for a new todo."
  [store]
  (let [todos (store/get-todos store)]
    (res/response (view/render-home todos))))
          
(defn handle-post
  "This handles creating a new todo, based on the POST data."
  [store request]
  (let [title (get (:form-params request) "todo")]
    (store/add-new-todo store title)
    (handle-index store)))

(defn index-handler
  "Handle requests sent to our root URL.

  They can be either GET requests, or POST requests (the user just POSTed a new todo)."
  [store request]
  (if (= (:request-method request) :post)
    (handle-post store request)
    (handle-index store)))

(defn handler
  "Get the handler function for our roots"
  [store]
  (make-handler ["/" {"" (partial index-handler store)}]))

(defrecord HttpServer [server]
  component/Lifecycle
  (start [this]
    (assoc this :server 
      (http/start-server 
        (-> (handler (:store this)) wrap-params)
        {:port 8080})))
  (stop [this]
    (assoc this :server nil)))

(defn make-server [] (map->HttpServer {}))