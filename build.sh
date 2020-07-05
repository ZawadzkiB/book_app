#!/usr/bin/env bash

docker-compose -f docker-compose-build.yml up && docker build . -t book-app