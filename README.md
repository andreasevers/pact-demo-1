Pact example project
====================

This is an example project demonstrating how to test-drive a new endpoint on a
Spring Boot provider, starting from the consumer side.


How to use this repository
--------------------------

Like a real project, commits in this repository incrementally add tests and
implementation.  Use a tool of your choice to view the diffs between each
commit.

Some of the commits have failing tests to highlight intermediate steps and to
show how failing tests inform us about the next steps.


About the provider test
-----------------------

The provider test is implemented as an integration test, running the whole
application in the `pact` Spring profile. In a real app, this profile should
stub out external dependencies to prevent side effects and to make the test
predictable and robust.

Running contract tests as integration tests is definitely controversial. In my
experience, I have seen more up- than downsides to this approach: it's usually
not more complicated to set up preconditions in the database vs. setting up
mocks. The tests are also not brittle if the application is designed to be
testable and if its output is reproducible (e.g. list item order needs to be
stable). On the plus side, this approach allows having an integration-like test
for every response case of each endpoint. And who would say no to free
integration tests?

How to demo this repository
---------------------------

1. Run the Pact Broker locally through `docker compose up -d` in the [Broker repo](https://github.com/pact-foundation/pact-broker-docker) and delete any existing pacts.
1. Remove any pacts inside the `pacts` folder in the consumer.
1. Run mvn spring-boot:run for both projects and watch the console log of the consumer.
1. In the provider, change the `name` property in `AnimalResponse` to `type`, and watch the consumer log.
1. Explore the tests in the consumer.
1. Run the `listAnimals_returnsAnimals` test and verify the creation of the new pact file.
1. Explore the tests in the provider.
1. Run the `PactIntegrationTest` and inspect the error returned from it.
1. Change `type` back to `name` and rerun the test.
1. Look at the `@PactFolder` annotation, and notice the dependency on the consumer codebase.
1. Go to [the Pact Broker UI](http://localhost) and observe there are no contracts.
1. Publish to the Pact Broker by running the `pact:publish` consumer maven goal.
1. In the Broker, take a look at the newly published contract.
1. Run the `pact:can-i-deploy` consumer maven goal to verify if we can deploy the consumer.
1. In the Broker, note that the new contract has not been verified yet.
1. Change the `@PactFolder("../pact-consumer/pacts")` annotation to `@PactBroker(host = "localhost", port = "80")`
1. Run the `Test and publish results` provider maven goal to report the verification results to the Pact Broker.
    _This goal is actually just the `mvn test -Dpact.verifier.publishResults=true -f pom.xml` command._
1. **[Not working]** Run the `pact:can-i-deploy` maven goal to verify if we can deploy the provider.
1. Run the `pact:can-i-deploy` maven goal to verify if we can deploy the consumer.
1. Let's start using tags.


1. Feature branches (https://github.com/pact-foundation/pact-jvm/tree/master/provider/maven#publishing-pact-files-to-a-pact-broker)


1. Pending / WIP pacts (https://docs.pact.io/pact_broker/advanced_topics/pending_pacts/ https://docs.pact.io/implementation_guides/jvm/provider/junit/#pending-pact-support-version-413-and-later)
