(ns jtk-dvlp.re-frame.readfile-fx
  x(:require-macros
    [cljs.core.async.macros :refer [go]])

  (:require
   [cljs.core.async :as async]
   [re-frame.core :as re-frame]))


(defn- do-readfile!
  [file charset]
  (let [result (async/promise-chan)]
    (try
      (let [meta
            {:name (.-name file)
             :size (.-size file)
             :type (.-type file)
             :last-modified (.-lastModified file)}

            reader
            (js/FileReader.)

            on-loaded
            (fn [_]
              (->> (.-result reader)
                   (assoc meta :content)
                   (async/put! result))
              (async/close! result))]

        (.addEventListener reader "load" on-loaded)

        (if charset
          (.readAsText reader file charset)
          (.readAsText reader file)))

      (catch js/Object error
        (async/put! result {:error error :file file})
        (async/close! result)))

    result))

(re-frame/reg-fx
 :readfile
 (fn readfile-fx
   [{:keys [files charsets on-success on-error]}]
   (go
     (let [charsets
           (if (or (string? charsets) (nil? charsets))
             (repeat charsets)
             charsets)

           contents
           (->> (mapv do-readfile! files charsets)
                (async/map vector)
                (async/<!))

           errors
           (filter :error contents)]

       (if (seq errors)
         (re-frame/dispatch (conj on-error contents))
         (re-frame/dispatch (conj on-success contents)))))))
