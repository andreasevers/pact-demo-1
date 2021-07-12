#!/bin/bash

mvn clean test pact:publish pact:can-i-deploy
