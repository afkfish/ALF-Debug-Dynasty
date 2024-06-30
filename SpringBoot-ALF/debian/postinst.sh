#!/bin/sh
chown alf-daemon:alf-daemon /opt/springboot-alf/log

#DEBHELPER#

systemctl enable springboot-alf