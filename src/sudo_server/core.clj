(ns sudo-server.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [org.httpkit.client :as client]
            [environ.core :refer [env]]))

;; env variables
(def PRIVACY_API_URL 
  (env :privacy-api-url))
(def PRIVACY_API_KEY
  (env :privacy-api-key))

(def options 
  {:headers {"Content-Type" "application/json"
             "Authorization" (str "api-key " PRIVACY_API_URL)}})

(defn get-cards [req]
  (let [resp @(client/get (str PRIVACY_API_URL "/v1/card") options)]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    resp}))

(defroutes all-routes
  (GET "/" [] "Hello world")
  (GET "/cards" [] get-cards))

(defn -main
  "App entry point"
  [& args]
  (let [port (Integer/parseInt (or (env :port) "8080"))]
    (run-server all-routes {:port port})
    (println (str "Running server at http://127.0.0.1:" port "/"))))