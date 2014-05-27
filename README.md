play-slick-example
==================

A simple skeleton for play scala slick applications running in a docker container.

To get it to run, just grab ubuntu 14.04 and run this:

```
#install docker on ubuntu 14.04
apt-get update
apt-get install docker.io
ln -sf /usr/bin/docker.io /usr/local/bin/docker
#get dockerfile
cd /home
wget https://raw.githubusercontent.com/nemoo/play-slick-example/master/Dockerfile
#create docker image
docker build -t nemoo/play-slick-example:1.0 .
#run docker container
docker run -d -p 9000:9000 nemoo/play-slick-example
```