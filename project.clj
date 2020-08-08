(defproject sudo-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "1.0.0"]
                 [http-kit "2.3.0"]
                 [compojure "1.6.1"]
                 [environ "1.2.0"]]
  :plugins [[lein-environ "1.2.0"]
            [lein-pprint "1.3.2"]]
  :main ^:skip-aot sudo-server.core
  :min-lein-version "2.0.0"
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev [:project/dev :profiles/dev]
             :test [:project/test :profiles/test]
             :prod [:project/prod :profiles/prod]
             ;; only edit :profiles/* in profiles.clj
             :profiles/dev  {}
             :profiles/test {}
             :profiles/prod {}

             :project/dev {:dev true
                           :api-key ~(System/getenv "PRIVACY_API_KEY_SAND")
                           :api-url ~(System/getenv "PRIVACY_API_URL_SAND")}
             :project/test {:test true
                            :api-key ~(System/getenv "PRIVACY_API_KEY_SAND")
                            :api-url ~(System/getenv "PRIVACY_API_URL_SAND")}
             :project/prod {:prod true
                            :api-key ~(System/getenv "PRIVACY_API_KEY_PROD")
                            :api-url ~(System/getenv "PRIVACY_API_URL_PROD")}})
