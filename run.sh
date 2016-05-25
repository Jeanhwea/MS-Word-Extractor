#!/usr/bin/env bash

MAINCLASS='cn.edu.buaa.sei.AppMain'
ARGUMENTS=''

mvn exec:java -Dexec.mainClass="$MAINCLASS"  -Dexec.args="$ARGUMENTS"
