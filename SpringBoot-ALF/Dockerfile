# Use an official Debian base image
FROM debian:bullseye

# Set the working directory in the container
WORKDIR /package/src

# Install necessary packages
RUN apt-get update && apt-get install -y \
    build-essential \
    devscripts \
    debhelper \
    lintian \
    fakeroot \
    openjdk-17-jre

# Set the ENTRYPOINT to build the package
ENTRYPOINT ["debuild", "-us", "-uc"]