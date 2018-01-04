#!/bin/sh

JAVA_HOME="/usr/java/jdk1.8.0_91"

RUNNING_USER=root

APP_HOME=/home/mobilewebservices/dispatcher

APP_MAINCLASS=$APP_HOME/Illuminate-dispatcher-service-driver-1.0.0.jar

CLASSPATH=/home/mobilewebservices/lib 
SERVER_PORT=8081
SLEEP_TIME_SAFE_KILL=5
ENVIRMENT=dev

JAVA_CMD="nohup $JAVA_HOME/bin/java -Dloader.path=$CLASSPATH -Dspring.profiles.active=$ENVIRMENT -jar $APP_MAINCLASS >/dev/null 2>&1 &"

psid=0

checkpid() {

javaps=`ps -ef|grep $APP_MAINCLASS|grep -v grep|cut -c 9-15`
if [ -n "$javaps" ]; then
	psid=`echo $javaps | awk '{print $1}'`
else
	psid=0
	echo "psid=$psid"
fi
}

start() {
checkpid

if [ $psid -ne 0 ]; then
	echo "=======warn: $APP_MAINCLASS already started! (pid=$psid)========"
else
	echo -n "Starting $APP_MAINCLASS ..."
	
	echo "========JAVA_CMD=$JAVA_CMD========"	
	su - $RUNNING_USER -c "$JAVA_CMD"
	checkpid
	if [ $psid -ne 0 ]; then
		echo "(pid=$psid) [OK]"
	else
		echo "[Failed]"
	fi
fi

}

stop() {
	checkpid
	
		if [ $psid -ne 0 ]; then
	        echo -n "shutdown server: curl -X POST http://localhost:$SERVER_PORT/shutdown"
                curl -X POST http://localhost:$SERVER_PORT/shutdown
                echo -n "Sleep $SLEEP_TIME_SAFE_KILL second to wait server down, to make sure it is down will kill the pid again."
                sleep $SLEEP_TIME_SAFE_KILL

		echo -n "Stopping $APP_MAINCLASS ...(pid=$psid) "
		su - $RUNNING_USER -c "kill -9 $psid"
		if [ $? -eq 0 ]; then
			echo "[ok]"
		else
			echo "[failed],the pid is already killed."
		fi

		checkpid
		if [ $psid -ne 0 ]; then
			stop
		fi
	else
		echo "====warn: $APP_MAINCLASS is not running======="		
	fi

}

status() {
checkpid

if [ $psid -ne 0 ];  then
	echo "$APP_MAINCLASS is running! (pid=$psid)"
else
	echo "$APP_MAINCLASS is not running"
fi

}

info(){

echo "System Information:"
echo "****************************"
echo `head -n 1 /etc/issue`
echo `uname -a`
echo
echo "JAVA_HOME=$JAVA_HOME"
echo `$JAVA_HOME/bin/java -version`
echo
echo "APP_HOME=$APP_HOME"
echo "APP_MAINCLASS=$APP_MAINCLASS"
echo "****************************"

}


case "$1" in
	'start')
		start
		;;
	'stop')
		stop
		;;
	'restart')
		stop
		start
		;;
	'status')
		status
		;;
	'info')
		info
		;;
	*)
	
	echo "Usage: $0 {start|stop|restart|status|info}"
	exit 1
	esac
	exit 0