(def +lib-version+ "5.4.0")
(def +version+ (str +lib-version+ "-0"))

(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.6.0"  :scope "test"]
                  [cljsjs/react "15.4.2-2"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(task-options!
 pom  {:project     'cljsjs/react-jss
       :version     +version+
       :description "React integration of JSS"
       :url         "https://github.com/cssinjs/react-jss"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
    (download :url (str "https://unpkg.com/react-jss@" +lib-version+ "/dist/react-jss.js")
              :checksum "f65fcb5d97abff0a9f795e03b7080b21")
    (download :url (str "https://unpkg.com/react-jss@" +lib-version+ "/dist/react-jss.min.js")
              :checksum "cf5b137cec50278ba886a16831d0bb72")

    (sift :move {#"^react-jss.js" "cljsjs/react-jss/development/react-jss.inc.js"
                 #"^react-jss.min.js" "cljsjs/react-jss/production/react-jss.min.inc.js"})

    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.react-jss"
               :requires ["cljsjs.react"])
    (pom)
    (jar)))
