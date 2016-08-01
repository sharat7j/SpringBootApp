#!/bin/bash

#admin email account
ADMIN="admin@company.com"

# set usage alert threshold
THRESHOLD=90

#hostname
HOSTNAME=$(hostname)

#mail client
MAIL=/usr/bin/mail

# store all disk info here
EMAIL=""

for line in $(df -hP | egrep '^/dev/' | awk '{ print $6 "_:_" $5 }')
do
	
	part=$(echo "$line" | awk -F"_:_" '{ print $1 }')
	part_usage=$(echo "$line" | awk -F"_:_" '{ print $2 }' | cut -d'%' -f1 )

	if [ $part_usage -ge $THRESHOLD -a -z "$EMAIL" ];
	then
		EMAIL="$(date): Running out of diskspace on $HOSTNAME\n"
		EMAIL="$EMAIL\n$part ($part_usage%) >= (Threshold = $THRESHOLD%)"

	elif [ $part_usage -gt $THRESHOLD ];
	then
		EMAIL="$EMAIL\n$part ($part_usage%) >= (Threshold = $THRESHOLD%)"
	fi
done 

if [ -n "$EMAIL" ];
then 
	echo -e "$EMAIL" | $MAIL -s "Alert: Partition(s) almost out of diskspace on $HOSTNAME" "$ADMIN"
fi