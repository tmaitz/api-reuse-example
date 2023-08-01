# API DevUnit

## IDEA

The main idea is to segregate the REST API interface/interfaces into a separate module/dependency. This interface will be implemented by the service and also used in client applications.

## How to

It is necessary to separate the controller API into a separate interface (with all annotations and documentation). This will make the controller code easier to read, and it will also make it easier to expose the API (only methods and annotations and no implementation!)

In addition to the interface, we also need to provide the entities/DTOs that should be used for API requests and responses. It is recommended to separate incoming entities from outgoing ones (but these are already issues of convenience and agreements that are a little beyond the scope of this article)

Consider an example

We have the following modules

- book-service-api - API interface,
- book-service - service that implements book-service-api (server)
- publisher-service - a service that needs data provided by book-service via API

The implementation and configuration of book-service and book-service-api look quite simple. A more difficult task seems to be the integration of publisher-service and book-service. For these purposes, we create a module

- book-service-client - a library that provides access to the book-service (using the book-service-api)

Now the publisher-service can receive data from the book-service by simple additional settings (adding the correct URL).

But let's take a closer look at the book-service-client. To be honest, it is not always possible to separate this into a separate module/project, for example, if there are few book-service-api consumers or consumers use frameworks other than Spring. But let's focus on an example in which we use Spring to build services. The book-service-client provides an OpenFeign Client (using the interface from book-service-api) and a default configuration for it. This opens up the following ways for us to use

- We can use the client right out of the box (in the configuration it is desirable to have the necessary default values)
- We can override some values by overriding the corresponding values in application.properties or application.yml
- We can override some values by overriding some beans defined in the default configuration. To do this, it is desirable to have the necessary settings in the definition of beans in the default configuration
- We can create our own configuration. To do this, we will need to duplicate the definition of the OpenFeign Client interface.

Thus, we can provide a convenient, flexible interface to provide access to the book-service without unnecessary settings.

## Testing

It's good to highlight the testing of this approach as a separate item. It is desirable that book-service, book-service-api, and book-service-client be located in the same repository. This will facilitate readability as well as testing. To test how the modules relate, we just need to add a test dependency (scope = test) to write a test.

![Testing](/docs/testing.svg?raw=true "Testing")

## Development and deployment

- book-service - service, the deployment endpoint of which will be some environment
- book-service-api - API whose deployment endpoint will be an artifact in the repository
- book-service-client - client API, the deployment endpoint of which will be an artifact in the repository

![CI CD](/docs/ci_cd.svg?raw=true "CI CD")

### Modules summary

- book-service - Service which provides book-service-api. Server implementation of book-service-api.
- book-service-api - Library which contains API for book managing.
- book-service-client - Library which contains FeignClient implementation and Default Configuration for book-service-api. Client implementation of book-service-api.
- publisher-service - Example service that uses the book-service-client to retrieve data from the book-service.
