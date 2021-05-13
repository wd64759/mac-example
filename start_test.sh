#!/bin/bash
current_dir=$(dirname $0)
agent_jar=/mnt/d/code/e4/mac-core/build/libs/mac-core-1.0.jar
app_jar=mac-example-0.0.1.jar
cd $current_dir

export JAVA_HOME=/home/wei/jdk1.8.0_212
# BOOT_JAR="$JAVA_HOME/lib/tools.jar:$JAVA_HOME/jre/lib/rt.jar"
# JAVA_OPTIONS="-Djdk.attach.allowAttachSelf=true"
OPTIONS="-Dmac.agent=/mnt/d/code/e4/mac-agent/scripts/start_agent.sh"

# ${JAVA_HOME}/bin/java -Xbootclasspath:a:${BOOT_JAR} ${JAVA_OPTIONS} -javaagent:${agent_jar} -jar ./build/libs/${app_jar}
# java ${JAVA_OPTIONS} -javaagent:${agent_jar} -jar ./build/libs/${app_jar}
# ${JAVA_HOME}/bin/java -jar ./build/libs/${app_jar}
# ${JAVA_HOME}/bin/java ${OPTIONS} -javaagent:${agent_jar} -jar ./build/libs/${app_jar}
java ${OPTIONS} -javaagent:${agent_jar} -jar ./build/libs/${app_jar}