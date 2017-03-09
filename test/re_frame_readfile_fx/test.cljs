(ns re-frame-readfile-fx.test
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [re-frame-readfile-fx.core]))

(defn- col->array
  [col]
  (-> js/Array
      .-prototype
      .-slice
      (.call col)))

(re-frame/reg-event-fx
 :on-readfile-fx-success
 (fn [_ [_ result]]
   (.debug js/console "success" result)))

(re-frame/reg-event-fx
 :on-readfile-fx-error
 (fn [_ [_ result]]
   (.debug js/console "error" result)))

(re-frame/reg-event-fx
 :on-readfile
 (fn [_ [_ files]]
   {:readfile {:files files
               :charsets (map #(.-name %) files)
               :on-success [:on-readfile-fx-success]
               :on-error [:on-readfile-fx-error]}}))

(defn view
  []
  [:div
   [:p "please readfile the two files \"windows-1252\" and \"utf-8\" from within the repo."]
   [:p
    [:label
     {:for "test-success"}
     "test will be successful"]
    [:br]
    [:input
     {:type "file"
      :id "test-success"
      :multiple true
      :on-change #(re-frame/dispatch [:on-readfile (-> % .-target .-files col->array js->clj)])}]]
   [:p
    [:label
     {:for "test-fail"}
     "test will fail"]
    [:br]
    [:input
     {:type "file"
      :id "test-fail"
      :multiple true
      :on-change #(re-frame/dispatch [:on-readfile (-> % .-target .-files col->array js->clj (conj "I will cause an error!"))])}]]])

(->> (.getElementById js/document "view")
     (reagent/render [view]))
