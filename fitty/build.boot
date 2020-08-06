(def +lib-version+ "2.3.2")
(def +version+ (str +lib-version+ "-0"))

(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.10.5" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(task-options!
 pom  {:project     'cljsjs/fitty
       :version     +version+
       :description "Snugly resizes text to fit its parent container"
       :url         "https://github.com/rikschennink/fitty"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
    (download :url (str "https://github.com/rikschennink/fitty/archive/" +lib-version+ ".zip")
              :unzip true)

    (sift :move {#"^fitty(.*)/dist/fitty.min.js$" "cljsjs/fitty/production/fitty.min.inc.js"
                 #"^fitty(.*)/dist/fitty.module.js$" "cljsjs/fitty/development/fitty.module.inc.js"})

    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.fitty")
    (pom)
    (jar)
    (validate)))
