SUMMARY = "ALF Java image"
DESCRIPTION = "ALF Java image"
LICENSE = "MIT"

inherit core-image

IMAGE_FEATURES += " package-management"
IMAGE_INSTALL += " openjdk-17-jre"
