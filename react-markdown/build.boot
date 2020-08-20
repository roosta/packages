(def +lib-version+ "4.3.1")
(def +version+ (str +lib-version+ "-0"))

(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.10.5" :scope "test"]
                  [cljsjs/react "16.13.1-0"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(task-options!
 pom  {:project     'cljsjs/react-markdown
       :version     +version+
       :description "Render Markdown as React components"
       :url         "https://github.com/rexxars/react-markdown"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
    (download :url (str "https://unpkg.com/react-markdown@" +lib-version+ "/umd/react-markdown.js"))
    (sift :move {#"react-markdown.js" "cljsjs/react-markdown/production/react-markdown.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.react-markdown"
               :requires ["cljsjs.react"])
    (pom)
    (jar)
    (validate)))
