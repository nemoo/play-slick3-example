# play-example
#
# VERSION               0.0.1


FROM      ubuntu:14.04

# make sure the package repository is up to date
RUN echo "deb http://archive.ubuntu.com/ubuntu trusty main universe" > /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y --no-install-recommends git  htop unzip wget
# java installation needs some special treatment
RUN sed 's/main$/main universe/' -i /etc/apt/sources.list
RUN apt-get update && apt-get install -y software-properties-common python-software-properties
RUN add-apt-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN apt-get install -y oracle-java7-installer


#===install sbt
RUN wget http://apt.typesafe.com/repo-deb-build-0002.deb
RUN dpkg -i repo-deb-build-0002.deb
RUN wget http://dl.bintray.com/sbt/debian/sbt-0.13.2.deb
RUN dpkg -i sbt-0.13.2.deb
RUN mkdir -p /root/.sbt/.lib/0.13.1/ 
RUN wget -O /root/.sbt/.lib/0.13.1/sbt-launch.jar http://typesafe.artifactoryonline.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/0.13.1/sbt-launch.jar

# create a distribution from the source
RUN git clone https://github.com/nemoo/play-slick-example.git
RUN cd /play-slick-example && sbt dist
RUN unzip /play-slick-example/target/universal/play-slick-example-1.0-SNAPSHOT.zip

CMD play-slick-example-1.0-SNAPSHOT/bin/play-slick-example -DapplyEvolutions.default=true

EXPOSE 9000
