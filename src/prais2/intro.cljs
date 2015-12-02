(ns ^:figwheel-always prais2.intro
    (:require [rum.core :as rum]
              [prais2.core :as core]
              [prais2.content :as content]
              [prais2.data :as data]
              [prais2.chrome :as chrome]
              [prais2.open-layers-map :as map]))

(declare section)

(declare section-1-content)
(declare section-2-content)
(declare section-3-content)
(declare section-4-content)

(rum/defc render-intro < (core/monitor-react "INTRO>")  [section-id]
  [:.container
   [:.row
    [:#intro

     #_[:h1  "UNDERSTANDING PUBLISHED CHILDREN’S HEART SURGERY OUTCOMES"]

     (section 1 "What is this site for?" section-1-content)

     (section 2 "Which hospitals perform heart surgery in children?" section-2-content)

     (section 3 "What can published survival rates tell you without extra information?" section-3-content)

     (section 4 "How do we put survival rates into context?" section-4-content)

     ]]])


(rum/defc section-1-content []
  [:section.two-col.well.intro
   [:p
    "The main outcome measure that the NHS uses to monitor children’s heart surgery in the UK is the "
    [:em
     "30-day survival rate"]
    ". This is the percentage of operations where the child survived at least 30 days after their heart surgery (e.g. 100% would mean that every child survived). Every year or two there are some articles in the press about children’s heart surgery in the UK. Often, these articles compare one hospital to another or suggest that a hospital has more deaths than it “should have”."]

   [:ul.no-break
    [:li
     "Where do journalists get these numbers from?"]
    [:li
     "What do they mean by “should have”? "]
    [:li
     "How valid are these sorts of comparisons? "]
    [:li
     "What do survival rates actually tell you?"]]

   [:p
    "Our website will help you explore what survival rates "
    [:strong
     "can"]
    " and "
    [:strong
     "can’t"]
    " tell you: for instance, if one hospital has a higher survival rate than another it "
    [:strong
     "does not mean"]
    " that one hospital must be better than the other. We hope that this website will let everyone can see and understand how the NHS monitors children’s heart surgery."]

   [:p
    [:strong
     "This site cannot help parents make decisions about their child’s treatment."]
    " If you are concerned about your child’s treatment plan "
    [:strong
     "please speak with your child’s cardiologist or surgeon"]
    ". You can also access the support available from national charities such as the "
    [:a
     {:href "http://www.chfed.org.uk/documents/2012/11/second-opinion-factsheet.pdf"}
     [:strong
      "Children’s Heart Federation"]]
    " or "
    [:a
     {:href "http://www.lhm.org.uk/"}
     [:strong
      "Little Hearts Matter"]]
    " or local charities for your specialist children’s hospital (see below). These guides on "
    [:a
     {:href "http://www.chfed.org.uk/documents/2015/02/talking-to-doctors-pdf-factsheet.pdf"}
     "speaking to your child’s surgeon"]
    " or "
    [:a
     {:href "http://www.chfed.org.uk/documents/2012/11/second-opinion-factsheet.pdf"}
     "seeking a second opinion"]
    ", written by the Children’s Heart Federation, might also be helpful."]

   [:p
    "We know that there is much more to children’s heart surgery than survival to 30 days after surgery, such as much longer term survival and quality of life after surgery. Although this information is not routinely available at the moment, we are actively researching how to collect, interpret and publish this data (more in the FAQs)."]])


(rum/defc section-2-content < rum/reactive []
  [:section
   [:p
    "There are fourteen hospitals in the UK and Ireland that perform heart surgery in children (here a child means someone under the age of 16)."]
   [:p
    "We have marked these hospitals on the map. Click on a hospital's marker or menu item and a summary report will appear alongside or underneath. Mouse over or click on the bars and dot within the summary data chart for further explanation."]
   [:p "To see all hospitals together "
    [:button.btn.btn-link {:style {:padding 0}
                           :on-click #(core/click->event-bus % :data nil)} "visit the data page"]
    "."]

   [:span
    [:.map-container (map/hospitals)]
    [:.detail-container (data/hospital-detail (:map-h-code (rum/react core/app)))]]])


(rum/defc section-3-content []
  [:section.two-col
   [:p
    "Currently, about 3500 children under the age of 16 have heart surgery each year in the United Kingdom and Republic of Ireland. Overall, the survival rate is 97%, telling us that the UK has very high survival rates for this difficult speciality."]
   [:p
    "However, heart disease in children covers a wide range of disorders, from relatively minor to more severe conditions. The different conditions mean that different surgeries can carry very different risks for children. There are also other factors that make some surgeries riskier than others. For instance, some children also have other health problems or are very small which can make surgery more risky."]
   [:p
    "Some hospitals specialise in certain conditions, meaning that some hospitals tend to operate on children with a lower chance of survival. It would be unfair to then expect all hospitals to have the same survival rates each year. Circumstances also change from year to year, so that one year a hospital might see many more very complex cases than the year before. Therefore, we expect any hospital’s survival rate to vary over time."]
   [:p
    "This means that we "
    [:strong
     "cannot"]
    " use survival rates to compare hospitals to each other, or to look at one hospital from one year to the next, "
    [:strong
     "without"]
    " putting the survival rates into the context of how complex the cases were. In other words, if a hospital’s 30-day survival rate is lower this year that last year, it does "
    [:strong
     "not"]
    " necessarily mean that things have got worse. Likewise, as we have already stressed elsewhere, if one hospital has a higher survival rate than another hospital it does "
    [:strong
     "not"]
    " necessarily mean that one hospital is better than the other."]
   ])


(rum/defc section-4-content []
  [:section.two-col
   [:p
    "Every individual is unique and may respond differently to treatment (surgery, drugs, postoperative care)."]
   [:p
    "Our research has shown that there are some consistent factors that hospitals routinely collect information about that do affect a child’s chance of survival. These things include:"]

   [:ul.no-break
    [:li
     "the age and weight of the child (other things being equal, the bigger and stronger a child is, the safer the surgery is);"]
    [:li
     "what problem in the heart the surgery is trying to fix (some hearts have more complex defects than others);"]
    [:li
     "other health problems a child might have (e.g. a genetic syndrome);"]
    [:li
     "the complexity of the surgical procedure."]]

   [:p
    "Knowing these factors for each patient, allows us to identify types of patient who are at greater or lesser risk even though we cannot predict exactly how a particular individual will respond."]

   [:p
    "We use what is called a “statistical model” to combine what we know about these aspects for the children a hospital has treated over any given time period (e.g. 3 years). This mathematical combination results in a "
    [:strong
     "predicted"]
    " overall proportion of survivors for "
    [:strong
     "that specific hospital"]
    ". We would expect the survival rate actually achieved in that hospital to be not too far away from this prediction and so we finally calculate "
    [:strong
     "a predicted range"]
    " for that specific hospital. If that hospital’s actual survival rate is within that predicted range, its results are in line with what we expect."]


   [:.bg-danger
    [:p
     "IMPORTANT! The predicted range depends on the types of patients treated at that hospital over that time period – so each hospital will have a different predicted range and its predicted range will vary from year to year!"]
    [:p
     "That is why we only compare a hospital’s survival rate to its predicted range (from the independent statistical model) and not to survival rates at other hospitals. "]]
   ])


(rum/defc section [section-id section-title section-content]
  [:section {:id section-id}
   [:h2 section-title]
   (section-content)])
