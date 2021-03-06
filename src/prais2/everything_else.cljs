(ns ^:figwheel-always prais2.everything-else
    (:require [rum.core :as rum]
              [clojure.string :as str]
              [prais2.core :as core]
              [prais2.faqs :as faqs]
              [prais2.utils :refer [key-with]]
              [prais2.chrome :as chrome]
              [prais2.content :as content :refer [faq-sections]]))

;; See http://jsfiddle.net/mtjhax/q4p5r/1/ for simple scrollspy

(defn faq-id
  ([six] (str "faqss" six))
  ([six fix] (str "faqss" six "-" fix)))

(defn faq-hash [& args]
  (str "#" (apply faq-id args)))

(def scrollspy
  {:did-mount (fn [state]
                (.scrollspy (js/$ "body") (clj->js {:target "#faq-sidebar"}))
                state)
   })

(defn make-glossary-map []
  (into {} (mapcat (fn [g] {(:glossary-entry g) {:title (:title g) :body (:body g)}}) (:faqs (first (filter #(:is-glossary %) faq-sections))))))

(rum/defc render-everything-else < scrollspy [faq-ref]
  [:.container
   [:#sidebar.row {:style {:position "relative"}}

    [:#faq-sidebar.col-sm-3.affix {:style {:top "120px"}}
     [:h1 content/title]
     [:ul.nav.nav-list
      (for [six (range (count faq-sections))
           :let [section (faq-sections six)]]
        [:li
         [:a {:href (faq-hash six)
              :data-target (faq-hash six)} (:section  section)]
         [:ul.nav
          (let [faqs (:faqs section)]
            (for [fix (range (count faqs))
                  :let [faq (faqs fix)]]
              [:li
               [:a {:href (faq-hash six fix)} (:title faq)]]))]])
      ]]

    [:#faqs.col-sm-offset-4.col-sm-8
     {:style {:padding-bottom "500px"}}

     (for [six (range (count faq-sections))
           :let [section (faq-sections six)]]
       [:div {:id (faq-id six)
              :style {:padding-top "40px"}}
        [:h2 (:section section)]
        (let [faqs (:faqs section)]
          (for [fix (range (count faqs))
                :let [faq (faqs fix)]]
            [:div.clearfix {:id (faq-id six fix)
                            :style {:padding-top "40px"} }
             [:h3  (:title faq)]
             [:div (:body faq)]
             (when (not (empty? (:glossary faq)))
               [:p [:i "See " (interpose ", " (map name (:glossary faq))) " in the "
                    [:a {:href (faq-hash 6)} "glossary"] "."]])]))]
       )]

    ]]
  )
