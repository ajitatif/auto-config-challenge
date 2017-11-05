# Configuration Server Challenge
Coded for trendyol's coding challenge (could not finish on time, actually)

## What it is
This is a simple configuration service, storing configuration typed parameters for one or more services. Each service
first registers a session to the service and then asks for its configuration parameters

The project consists of the actual RESTful Web service and a Java client for it.

The `docker` directory contains files needed for a Docker image build.

## How to use the Web Service

### As the configurerer
* GET `${host:port}/admin/` to get all configuration parameters
* GET `${host:port}/admin/{id}` to get the configuration parameter bearing specified ID
* POST `${host:port}/admin/` to create a new configuration parameter
    - Don't forget to set `Content-Type: application/json`
    - See the `ConfigurationModel` class for the model
    - See the `Location` response header for created parameter's URL
* PUT `${host:port}/admin/{id}` to update the configuration parameter bearing specified ID

### As the client
1. Send a POST to `${host:port}/access/${serviceName}/` (don't forget the trailing slash)
2. Get the `JSESSIONID` cookie
3. Send a GET request to `${host:port}/access/${serviceName}/` with the cookie on the request header

## How to run
On the project root directory run `./gradlew build`

* Java Client tests are likely to fail due to the dependency to the service running itself.
* You can see the tests pass on your IDE if you first run `ConfigurationServiceTestStarter` class on test scope

### Running on Docker
1. On the `auto-config-service` directory run `../gradlew dockerize`. This implies `build`.
2. `docker run -p8080:8080 -d -v ${configPath}:/etc/auto-config-service gokalpg/auto-config-service`

    where `${configPath}` is the directory where your `application.property` is.

## Notice
* The database used in this project is an H2 database and since it's not configured to be run in server mode, 
it will not be possible to open a second connection to the Database
* You can find a sample configuration SQL on file `database.sql`
* You can find a sample application configuration on file `application.properties` under test scope.
## License
This piece of art is distributed under MIT license. See file `LICENSE` for details.
 