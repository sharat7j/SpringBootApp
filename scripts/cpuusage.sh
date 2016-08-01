#!/bin/bash

#hostname
HOSTNAME=$(hostname)

	SUBJECT="WARNING CPU USAGE HIGH"

	TO=admin@company.com

	MESSAGE=/tmp/messages
	# set cpu usage threshold
	THRESHOLD=95
	CPU_USAGE=$(top -b -n1 | awk '/^Cpu/ {print $2}' | cut -d. -f1)
	
	echo $HOSTNAME > $MESSAGE
		
	echo "#######################" > $MESSAGE


    echo "CPU statistics as follows.." >> $MESSAGE


	mpstat >> $MESSAGE

	echo "#######################" >> $MESSAGE

	

	[ $CPU_USAGE -gt THRESHOLD ] && mail -s "$SUBJECT" "$TO" < $MESSAGE
