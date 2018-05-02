#!/usr/bin/env sh

### tilde files
find . |grep '~$' |xargs rm

rm gg.jar
rm gg

rm myGroovyAll.jar

rm trace.txt
rm -r traces

