#!/bin/sh
for i in $(find ./build -name '*.java')
do
  if $(file -bi "$i" | grep -q iso-8859-1); then
    #echo "iso-8859-1 $i"
    mv $i $i.orig
    iconv -f iso8859-1 -t utf-8 "$i.orig" > "$i"
  fi
  if $(file -bi "$i" | grep -q unknown-8bit); then
    #echo "unknown-8bit"
    mv $i $i.orig
    iconv -f iso8859-1 -t utf-8 "$i.orig" > "$i"
  fi
  if $(file -bi "$i" | grep -q us-ascii); then
    #echo "us-ascii $i"
    mv $i $i.orig
    iconv -f us-ascii -t utf-8 "$i.orig" > "$i"
  fi

done

find ./build -name "*.orig" -exec rm -rf {} \;
