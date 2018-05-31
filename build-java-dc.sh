#! /bin/bash
#
#
APPLICATION_NAME=wordcount2
APP_FILE=wordcount-1.0-SNAPSHOT-jar-with-dependencies.jar
GIT_URI=https://github.com/zmhassan/spark-word-count-example.git
GIT_REF=master
        echo "$APPLICATION_NAME"
        echo "$APP_FILE"
        echo "$GIT_URI"
        echo "$GIT_REF"
        oc new-app --template=oshinko-java-spark-build-dc --param=APPLICATION_NAME="$APPLICATION_NAME" --param=APP_FILE="$APP_FILE"   --param=GIT_URI="$GIT_URI" --param=GIT_REF="$GIT_REF"
