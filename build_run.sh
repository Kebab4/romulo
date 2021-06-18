#!/bin/bash
cd src
pwd
javac -cp ".:../lib/json-simple-1.1.1.jar" --module-path ../lib/javafx-sdk-11.0.2/lib  --add-modules=javafx.controls romulo/Romulo.java
jar cfm moje.jar META-INF/MANIFEST.MF romulo/*.class romulo/format/*.class romulo/graph/*.class
cd ..
pwd
java -cp ".:lib/json-simple-1.1.1.jar" --module-path lib/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -jar src/moje.jar