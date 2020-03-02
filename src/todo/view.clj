(ns todo.view
  (:require [hiccup.page :refer [html5]]
            [hiccup.form :refer [form-to text-field submit-button]]))

(defn render-home
  "Render todo's home page"
  [todos]
  (html5 [:head
          [:meta {:charset "UTF-8"}]]
         [:body
          [:header "Todo"]
          (form-to [:post "/"]
                   (text-field "todo")
                   (submit-button "Add!!!"))
          [:ul (for [todo todos] [:li (:title todo)])]]))