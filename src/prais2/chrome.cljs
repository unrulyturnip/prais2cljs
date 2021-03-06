(ns ^:figwheel-always prais2.chrome
    (:require [goog.string :refer [unescapeEntities]]
              [rum.core :as rum]
              [cljsjs.jquery]
              [cljsjs.bootstrap]
              [prais2.utils :refer [key-with]]
              [prais2.core :as core :refer [event-bus]]
              [prais2.logger :as logger]
              [prais2.data :as data]
              ))



(defn rgba-string
  "return CSS rgba string"
  [[r g b a]]
  (str "rgba(" r "," g "," b "," a)
  )


(defrecord  Nav-item [long-title short-title class icon])

(def nbsp (unescapeEntities "&nbsp;"))
(def what-why (str "What," nbsp "why," nbsp "how?"))
(def everything-else (str "Everything" (unescapeEntities "&nbsp;") "else"))

(def nav-items {
                :home (Nav-item. "Home" "Home" "nav-item home" "home")
                :intro (Nav-item. what-why what-why "nav-item intro" "question")
                :data  (Nav-item. "Data" "Explore the data" "nav-item data" "table")
                :faqs  (Nav-item. everything-else everything-else "nav-item faqs" "info")})


(rum/defc bs-nav-link [active? nav-item click-handler]
  [:li
   [:button.navbar-btn {:on-click click-handler
                        :class (str (if active? " active " " ") (:class nav-item))}
    [:i.fa {:class (str "fa-" (:icon nav-item))
            :style {:font-size "22px"}}]
    (str " " (:short-title nav-item))]])

(rum/defc bs-fixed-navbar  [active-key]
  (let [nav-item (active-key nav-items)]
    [:nav.navbar.navbar-simple.navbar-fixed-top {:style {:max-height 0}}
     [:.navbar-inner
      [:.container {
                    :style {:background-color "#475E63"
                            ;:opacity 0.95
                            :width "100%"
                            :height "55px"
                            }
                    }
       [:.navbar-header {:key 1}
        [:button.navbar-toggle.collapsed {:key 1
                                          :type "button"
                                          :data-toggle "collapse"
                                          :data-target "#navbar"
                                          :aria-expanded "false"
                                          :aria-controls "navbar"}
         [:span.sr-only {:key 1} "Toggle navigation"]
         [:span.icon-bar {:key 2}]
         [:span.icon-bar {:key 3}]
         [:span.icon-bar {:key 4}]]]
       [:#navbar.navbar-collapse.collapse {:key 2}
        [:ul.nav.navbar-nav.navbar-right {:key 1}
         (map-indexed #(key-with %1 (bs-nav-link
                                     (= active-key %2)
                                     (%2 nav-items)
                                     (fn [e] (core/click->event-bus e %2 (if (= %2 :data) :map :top)))))
                      (keys nav-items))]]]]]))

(rum/defc header < rum/reactive [& deep]
  [:div
   (bs-fixed-navbar (:page (rum/react core/app)))
   [:div.blurred
    {:style
     {:width "100%"
      :height "80px"
      :background-color "#4F4763"
      :color "rgba(255,255,255,0.5)"
      :position "relative"
      }}
    (when true ;deep
      [:div
       [:div {:key 1
              :style
              {:position "relative"
               :z-index 1;
               :left "0px"
               :top "3px"
               :background-image "url(assets/logo3.png)"
               :background-repeat "no-repeat"
               :background-size "100%"
               :width "70px"
               :height "80px"
               :border "none"
               :color "white"
               :text-align "right"
               :float "left"
               :margin-left "30px"
}}

        ]
       [:h3 {:key 2
             :class "main-title"} "UNDERSTANDING CHILDREN’S HEART SURGERY OUTCOMES"]])
    ]])



(rum/defc footer []
  [:.container-fluid.partners
   [:.row {:style {:padding-top "50px"}}
    [:img.img-responsive.col-sm-offset-2.col-sm-2
     {:src "assets/ucl-logo.png"}]
    [:img.img-responsive.col-sm-2
     {:src "assets/camlogo.png"}]
    [:img.img-responsive.col-sm-1
     {:src "assets/KCLlogo.gif"}]
    [:img.img-responsive.col-sm-2
     {:src "assets/sas-logo.png"}]
    [:img.img-responsive.col-sm-1
     {:src "assets/chf-logo.png"}]]
   [:.row
    [:.col-md-12
     (data/option-menu event-bus)]]
   [:.row.footer
    ;[:.pull-right (logger/playback-controls)]


    [:h3
     "Funding acknowledgement"]
    [:p
     "This project was funded by the National Institute for Health Research Health Services and Delivery Research Programme\n(project number 14/19/13)"]
    [:h3
     "Department of Health disclaimer"]
    [:p
     "The views and opinions expressed therein are those of the authors and do not necessarily reflect those of the Health Services and Delivery Research Programme, NIHR, NHS or the Department of Health."]
    ]]
  )
