(ns todo.store
  (:require [com.stuartsierra.component :as component]))

(defn add-new-todo
  "Insert a new todo in the db and return its UUID"
  [store title]
  (let [uuid (.toString (java.util.UUID/randomUUID))]
    (swap! (:todos store) assoc (keyword uuid) {:title title})))

(defn get-todos
  "Get all the todos"
  [store]
  (vals @(:todos store)))

(defrecord InMemoryStore [todos]
  component/Lifecycle
  (start [this]
    (assoc this :todos (atom {})))
  (stop [this] this))

(defn make-store [] (map->InMemoryStore {}))