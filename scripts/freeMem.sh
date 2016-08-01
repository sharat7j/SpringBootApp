#!/bin/bash



MONITOR=$(free | grep Mem)
TOTAL_MEM=$(echo $MONITOR | awk '{ print $2 }')
MEM_FREE=$(echo $MONITOR | awk '{ print $4 }')
x=$((MEM_FREE*100))
MEM_FREE_PERCENTAGE=$(($x/$TOTAL_MEM))
#hostname
HOSTNAME=$(hostname)

	SUBJECT="Free Memory low!"

	TO=admin@company.com

	THRESHOLD=5
	MESSAGE=/tmp/messages
echo "Free memory less than 5% in $HOSTNAME" >> $MESSAGE
if [ $MEM_FREE_PERCENTAGE -lt $THRESHOLD ];
then
   mail -s "$SUBJECT" "$TO"  < $MESSAGE
fi
