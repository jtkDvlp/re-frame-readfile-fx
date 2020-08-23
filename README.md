[![Clojars Project](https://img.shields.io/clojars/v/jtk-dvlp/re-frame-readfile-fx.svg)](https://clojars.org/jtk-dvlp/re-frame-readfile-fx)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/jtkDvlp/re-frame-worker-fx/blob/master/LICENSE)

# Readfile effect handler for re-frame

This [re-frame](https://github.com/Day8/re-frame) library contains an [FileReader-Object](https://developer.mozilla.org/docs/Web/API/FileReader) [effect handler](https://github.com/Day8/re-frame/tree/develop/docs). The handler can be addressed by `:readfile`.

## Getting started

### Get it / add dependency

Add the following dependency to your `project.cljs`:<br>
[![Clojars Project](https://img.shields.io/clojars/v/jtk-dvlp/re-frame-readfile-fx.svg)](https://clojars.org/jtk-dvlp/re-frame-readfile-fx)

### Usage

See the following minimal code example or the [test.cljs](https://github.com/jtkDvlp/re-frame-readfile-fx/blob/master/test/re_frame_readfile_fx/test.cljs).

```clojure
(ns your.project
  (:require [re-frame.core :as re-frame]
            [re-frame-readfile-fx.core]))

(re-frame/reg-event-fx
 :some-event
 (fn [_ [_ files]]
   {:readfile {;; vector of file- and / or blob-objects
               :files files
               ;; charset via string for all files,
               ;; via vector of strings for every single file
               ;; or nil for default (utf-8). nil also works
               ;; from within vector (for every single file)
               :charsets ["windows-1252" nil]
               ;; dispatched on success conjoined with read files
               :on-success [:your-success-event]
               ;; dispatched on error conjoined with read files
               :on-error [:your-error-event]}}))
```

How to get files from file-input see the [test.cljs](https://github.com/jtkDvlp/re-frame-readfile-fx/blob/master/test/re_frame_readfile_fx/test.cljs).

## Appendix

I´d be thankful to receive patches, comments and constructive criticism.

Hope the package is useful :-)
