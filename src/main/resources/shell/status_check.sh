#!/bin/sh

RUNNING_USER=root
APP_MAINCLASS=org.apache.catalina.startup.Bootstrap
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

status() {
checkpid

if [ $psid -ne 0 ];  then
	echo tomcat8 is running! (pid=$psid)"
else
	echo tomcat8 is not running"
fi

}

info(){
echo "System Information:"
echo "****************************"
echo `head -n 1 /etc/issue`
echo `uname -a`
echo "****************************"

}


case "$1" in
	'status')
		status
		;;
	'info')
		info
		;;
	*)
	
	echo "Usage: $0 {status|info}"
	exit 1
	esac
	exit 0