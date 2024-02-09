# README #

Event Sourcing REST API Demo application using Java / Spring / Axon / Mongo. This application demonstrates how to implement a RESTful API
with Event Sourcing, CQRS and DDD principles. This will allow for capturing events and appending these to the event history. In doing this, we
are able to load pst events and replay history to arrive at the latest snapshot of data. 

In addition, by implementing the API using CQRS principles, we can split the write (Command) from the read (Query) phases. This means
that data being written in the system utilizes a separate path from the querying side. To name a few advantages for this, 
we would be able to performance tune the path that is more frequently used by for instance using a faster Database for the
busier side.

Ideally we would also have different repositories for the write and write sides, and have these synced using projections. Due
to the interest iof time, I did not implement this feature. That said, we are able to create a separate model for the Query from that of the Command.

This allows us to enrich the Query model when necessary without affecting the Command models for instance.

Mongo DB was used as the database to store Exchanges data, primarily for its speed when reading data f which is ideal for the read side.
Another advantage of using this database was that this allows for writing documents and requires little transformation logic from Java objects.

Finally, Axon was used in order to facilitate the CQRS design of this application. The primary benefit of using such a framework is that this will
handle the event store required for event sourcing. In addition, this will encourage using DDD principles.

In order to develop this app, a docker-compose file was added to host Mongo and Axon.

### Running the app ###

Step 1) Download docker desktop from https://www.docker.com/products/docker-desktop/

Step 2) Run the app

The app uses the Maven for dependency management. Using the Maven wrapper (MVNW), you do not need to install Maven to run the app.

Command to run from the top level of the project:

`./mvnw spring-boot:run`

Step 3) enable axon server node

Go to http://localhost:8024/ ensure that "Start as standalone node or first node" is selected and select Finish in the form to initialize the node for a fresh installation.

After a short while the app should connect to the Axon server and become available for REST requests,

## Running Tests ## 

Run the following command:

`./mvnw test`