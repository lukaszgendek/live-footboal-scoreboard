# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Package the application
RUN mvn clean package

# Run the tests
CMD ["mvn", "test"]