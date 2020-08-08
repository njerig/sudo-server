(ns sudo-server.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [org.httpkit.client :as client]
            [environ.core :refer [env]]
            [clojure.data.json :as json]))

;; env variables
(def PRIVACY_API_URL 
  (env :api-url))
(def PRIVACY_API_KEY
  (env :api-key))

;; request options
(def options 
  {:headers {"Content-Type" "application/json"
             "Authorization" (str "api-key " PRIVACY_API_KEY)}})
;; request handlers
(defn get-cards [req]
  (let [resp @(client/get (str PRIVACY_API_URL "/v1/card") options)]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (json/read-str (get resp :body) :key-fn keyword)}))

;; routing
(defroutes all-routes
  (GET "/" [] "Hello world")
  (GET "/cards" [] get-cards))

;; system
;; ref to app server instance for stopping/starting
(defonce server (atom nil))

(defn stop-server []
  ;; shut down server, waiting 100ms beforehand
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)
    (println "Server shutting down...")))

(defn -main
  "App entry point"
  [& args]
  (let [port (Integer/parseInt (or (env :port) "8080"))]
    (reset! server (run-server #'all-routes {:port port}))
    (println (str "Running server at http://localhost:" port "/"))))