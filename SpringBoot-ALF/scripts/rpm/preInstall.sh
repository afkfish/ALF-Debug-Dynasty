#!/bin/sh
echo "Creating group: alf-daemon"
/usr/sbin/groupadd -f -r alf-daemon 2> /dev/null || :

echo "Creating user: alf-daemon"
/usr/sbin/useradd -r -m -c "alf-daemon user" alf-daemon -g alf-daemon 2> /dev/null || :