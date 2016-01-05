play-slick3-example
==================

A starter application with Play Framework 2.4 and Slick 3.1. Most templates stop at hello world. This template shows you how you can structure your app into controllers, services and repositories. It shows how to model relationships between entities and perform basic operations on entities. It shows how to handle situations where various database operations have to be performed within a single transaction. Inspired by domain driven design (ddd) this template should give you a good starting point for your play framework project. It intentionally leaves out authorization and ui libraries to focus on how to model your domain. Have fun!

* Controllers link results from the service layer to GUI templates. They know nothing about data access technology. 
* Services handle the execution of slick database queries and retrieve data for views.
* Repositories handle interactions with domain aggregates.

Additional feature: Handling of Enums in Slick