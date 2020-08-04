(ns sudo-server.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]))

(defroutes all-routes
  (GET "/" [] "Hello world"))

(defn -main
  "App entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (run-server all-routes {:port port})
    (println (str "Running server at http://127.0.0.1:" port "/"))))
