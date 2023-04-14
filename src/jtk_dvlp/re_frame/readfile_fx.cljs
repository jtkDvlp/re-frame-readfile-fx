(ns jtk-dvlp.re-frame.readfile-fx
  (:require-macros
    [cljs.core.async.macros :refer [go]])


  (:require
    [cljs.core.async :as async]
    [re-frame.core :as re-frame]))


(defn- do-readfile!
  "FileReader will read file per optional named parameter :read-as which
  can have the following values
  :array-buffer
  :binary-string
  :data-url
  :text (default)"
  [file charset & {:keys [read-as] :or {read-as :text}}]
  (let [result (async/promise-chan)]
    (try
      (let [meta
            {:name          (.-name file)
             :size          (.-size file)
             :type          (.-type file)
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

        (case read-as
          :array-buffer
          (.readAsArrayBuffer reader file)

          :binary-string
          (.readAsBinaryString reader file)

          :data-url
          (.readAsDataURL reader file)

          :text
          (if charset
            (.readAsText reader file charset)
            (.readAsText reader file))))

      (catch js/Object error
        (async/put! result {:error error :file file})
        (async/close! result)))

    result))

(re-frame/reg-fx
  :readfile
  (fn readfile-fx
    [{:keys [files charsets channel on-success on-error read-as] :or {read-as :text}}]
    (go
      (let [charsets
            (if (or (string? charsets) (nil? charsets))
              (repeat charsets)
              charsets)

            contents
            (->> (mapv do-readfile! files charsets (repeat :read-as) (repeat read-as))
                 (async/map vector)
                 (async/<!))

            errors
            (filter :error contents)]

        (if (seq errors)
            (re-frame/dispatch (conj on-error contents channel))
            (re-frame/dispatch (conj on-success contents channel)))))))
