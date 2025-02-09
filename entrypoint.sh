#!/bin/sh

# Run the tests
mvn test

# Keep the container running
exec "$@"