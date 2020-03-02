(ns todo.main
  (:require [todo.system :refer [init-system start!]]))

(defn -main [& args]
  (init-system)
  (start!))