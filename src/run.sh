#!/bin/bash
set -e

find . -type f -name '*.class' -delete
javac -cp *:lib/*:. -sourcepath . test/Project4Evaluation.java -Xlint:unchecked
java -cp *:lib/*:. test.Project4Evaluation
