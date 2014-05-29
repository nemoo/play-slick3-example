play-slick-example
==================

A simple skeleton for play scala slick applications running in a docker container.

To get it to run, just grab ubuntu 14.04 and run this:



df
#create docker image
docker build -t nemoo/play-slick-example github.com/nemoo/play-slick-example
#run docker container
docker run -d -p 9000:9000 nemoo/play-slick-example
```


If you need to install docker on ubuntu, do this:
```
#install docker on ubuntu 14.04
apt-get update
apt-get install -y docker.io
ln -sf /usr/bin/docker.io /usr/local/bin/docker
```
