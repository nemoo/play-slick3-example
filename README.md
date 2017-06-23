Play 2.6 and Slick 3.2
==================

An example app using Play Framework 2.6 and Slick 3.2.

* Most templates stop at hello world. This template shows you how you can structure your app into controllers and repositories.
* It shows how to model relationships between entities and perform basic operations on entities.
* It shows how to handle situations where various database operations have to be performed within a single transaction.
* The focus is on how to model your domain, leaving out authorization and ui libraries. Have fun!
* Simple templates for ScalaTest and Specs2 are also included.

Repositories handle interactions with domain aggregates. All public methods are exposed as Futures. Internally, in some cases we need to compose various queries into one block that is carried out within a single transaction. In this case, the individual queries return DBIO query objects. A single public method runs those queries and exposes a Future to the client.


1. Install [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
2. Install [SBT](http://www.scala-sbt.org/download.html)
3. Run `sbt ~run` for continuous recompilation of the server app.

Done: [http://localhost:9000/](http://localhost:9000/)
