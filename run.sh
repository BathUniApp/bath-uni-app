#!/bin/bash

set -e

ant debug
adb uninstall ip7.bathuniapp
adb install bin/BathUniApp-debug.apk
adb shell am start ip7.bathuniapp/.MainActivity
