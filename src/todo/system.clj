(ns todo.system
  (:require [com.stuartsierra.component :as component]
            [todo.server :as server]
            [todo.store :as store]
            [clojure.tools.logging :refer [error]]))

(def ^:redef system "Hold our system" nil)

(defn build-system
  "Defines our system map"
  []
  (try
    (-> (component/system-map
         :server (server/make-server)
         :store (store/make-store))
        (component/system-using {:server [:store]}))
    (catch Exception e
      (error "Failed to build system" e))))

(defn init-system
  []
  (let [sys (build-system)]
    (alter-var-root #'system (constantly sys))))

(defn start!
  "Start system"
  []
  (alter-var-root #'system component/start-system)
  (println "System started at http://localhost:8080"))

(defn stop!
  "Stop system"
  []
  (alter-var-root #'system component/stop-system))
