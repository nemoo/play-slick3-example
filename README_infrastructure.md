How to build the infrastructure for this project
================================================

First, build the docker image. This will take the Dockerfile directly from github and use it to produce the docker image. 
```
docker build -t nemoo/play-slick-example github.com/nemoo/play-slick-example
```



To install mysql in its own container, while storing the database data on the original host, do this

First, create a directory on your host where you want the actual database to be persisted
```
mkdir /database
```

Now we build the mysql container from tutum directly from github (https://github.com/tutumcloud/tutum-docker-mysql). Then we initiate the database in the container whith the directory that we created on the host and provide an admin password.

```
docker build -t tutum/mysql github.com/tutumcloud/tutum-docker-mysql
docker run -d -v /database:/var/lib/mysql -p 3306:3306 -e MYSQL_PASS="mypass" tutum/mysql /bin/bash -c "/usr/bin/mysql_install_db"
```
Now we actually run the database
```
docker run -d --name mysql -p 3306:3306 -v /database:/var/lib/mysql tutum/mysql
```
To connect from the host to the database in the container, first we install mysql on the host
```
apt-get install -y mysql-server-5.6
```
Then we run the newly created application image, exposing the application on port 9000 and linking it to the mysql container that handles the database:
```
docker run -d -p 9000:9000 --link mysql:db nemoo/play-slick-example
```
##Preparation
###Install docker on ubuntu
```
#install docker on ubuntu 14.04
apt-get update
apt-get install -y docker.io
ln -sf /usr/bin/docker.io /usr/local/bin/docker
sed -i '$acomplete -F _docker docker' /etc/bash_completion.d/docker.io
```
###Creating the app server 
We want to inject our binary into a container that will run it. The injection of the binary into the container only works with a local dockerfile. So we have to clone that first. 
```
git clone https://github.com/nemoo/play-slick-example.git
```
Now, copy your binary application into the infrastructure/appserver directory so that it can be injected to the appserver container from there. You must name you app myapp.zip so it can be injected.
Create the appserver docker image that is just a java installation that will run your app. In the infrastructure/appserver directory, run
```
docker build -t nemoo/play-slick-example-appserver .
```
Then we run the newly created application image, exposing the application on port 9000 and linking it to the mysql container that handles the database:
```
docker run -d -p 9000:9000 --link mysql:db nemoo/play-slick-example-appserver
```

###Creating the build server 
In the buildserver directory, run
```
docker build -t nemoo/play-slick-example-buildserver .
```
