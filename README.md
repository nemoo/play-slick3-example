Play 2.5, Slick 3.1 and Mysql
==================

An example app using Play Framework 2.5, Slick 3.1 and Mysql.

* Most templates stop at hello world. This template shows you how you can structure your app into controllers and repositories.
* It shows how to model relationships between entities and perform basic operations on entities.
* It shows how to handle situations where various database operations have to be performed within a single transaction.
* The focus is on how to model your domain, leaving out authorization and ui libraries. Have fun!

Repositories handle interactions with domain aggregates. All public methods are exposed as Futures. Internally, in some cases we need to compose various queries into one block that is carried out within a single transaction. In this case, the individual queries return DBIO query objects. A single public method runs those queries and exposes a Future to the client.


1. Install [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
2. Install [Lightbend Activator](https://www.lightbend.com/activator/download)
3. Run `activator ~run` for continuous recompilation of the server app.

Done: [http://localhost:9000/](http://localhost:9000/)

PS.:
Slick should reintegrate the minimalistic blocking slick 2 api. 
Why? Because it it easier to read and write.  
I want to be able to use the asynchronous api when I need it. Still, following 
[Haoyi's Principle of Least Power](http://www.lihaoyi.com/post/StrategicScalaStylePrincipleofLeastPower.html) 
I want to use the blocking api when I want maximum simplicity. 
If you think so too, please support this [slick issue 1564](https://github.com/slick/slick/issues/1564).
 