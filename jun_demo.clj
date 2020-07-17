#!/usr/bin/env inlein

'{:dependencies [[org.clojure/clojure "1.8.0"][origami/youtube "1.0.0"][origami-dnn "0.1.10"]]}

(ns jundemo
  (:require
    [opencv4.core :refer :all]
    [opencv4.utils :as u]
    [origami-dnn.net.yolo :as yolo]
    [origami-dnn.draw :as d]
    [opencv4.dnn.core :as dnn]))

(Class/forName "origami.video.YouTubeHandler")

(let [[net _ labels] (dnn/read-net-from-repo "networks.yolo:yolov2-tiny:1.0.0")]
  (u/simple-cam-window
    {:video {:device (str "youtube://" (or (first *command-line-args*)  "bRr50VZ5Dqw"))}}
   (fn [buffer]
     (-> buffer
         ;(u/resize-by 0.5)
         (yolo/find-objects net)
         (d/blue-boxes! labels)))))