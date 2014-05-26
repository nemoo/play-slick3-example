# play-example
#
# VERSION               0.0.1

FROM      ubuntu:trusty

# make sure the package repository is up to date
RUN echo "deb http://archive.ubuntu.com/ubuntu trusty main universe" > /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y git default-jdk htop unzip wget

#===sbt
RUN wget http://dl.bintray.com/sbt/debian/sbt-0.13.1.deb
RUN dpkg -i sbt-0.13.1.deb
RUN mkdir -p /root/.sbt/.lib/0.13.1/ 
RUN wget -O /root/.sbt/.lib/0.13.1/sbt-launch.jar http://typesafe.artifactoryonline.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/0.13.1/sbt-launch.jar

RUN cd /home
RUN git clone https://github.com/nemoo/play-slick-example.git

EXPOSE 9000
