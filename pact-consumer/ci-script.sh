#!/bin/bash
set -e
BLUE='\033[45m\033[1;97m'
OFF='\033[m'

VERSION=1.0.0-$(date +"%m-%d-%Y-%H-%M-%S")
echo -e "\n$BLUE====> Setting the following version for the consumer: $VERSION $OFF\n"
mvn clean versions:set -DnewVersion=$VERSION
echo -e "\n$BLUE====> Running the tests and publishing the pact file $OFF\n"
mvn test pact:publish -Dpact.consumer.version=$VERSION
echo -e "\n$BLUE====> Tagging the version we just published as main code $OFF\n"
mvn pact:create-version-tag -Dpacticipant=zoo_client -DpacticipantVersion=$VERSION -Dtag=main
echo -e "\n$BLUE====> Checking whether we can deploy to test environment $OFF\n"
mvn pact:can-i-deploy -Dpact.pacticipantVersion=$VERSION -Dpact.latest=false -DtoTag=test
echo -e "\n$BLUE====> Deploying to the test environment $OFF\n"
echo -e "Deploying to test...\nDeployment done!"
echo -e "\n$BLUE====> Tagging the version we published before as deployed to the test environment $OFF\n"
mvn pact:create-version-tag -Dpacticipant=zoo_client -DpacticipantVersion=$VERSION -Dtag=test
