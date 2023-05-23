# Netflix Conductor Task Worker

This project is a Tasks Worker built with the Netflix Conductor client library that fetches data from a protected endpoint. It also includes an Auth0 client that renews the JWT when it's expired.

## Requirements
* JDK 10 or higher
* Gradle 6.0 or higher
* An Auth0 account and API credentials
* A running instance of Conductor server. To set up Conductor locally, refer to Conductor Setup Guide.

## Installation
1. Edit the application.properties file in the src/main/resources directory with your workflow configuration and Auth0 credentials. Make sure the conductor-url property is set up correctly and points to a running Conductor server instance.
2. Build the project with Gradle: gradle build
3. Run the project: java -jar build/libs/your-repo-1.0-SNAPSHOT.jar

## Usage
Once the project is running, it will automatically start listening and executing the tasks defined in the workflow configuration file. If the JWT expires, the Auth0 client will automatically renew it and update the headers for the requests made to the protected endpoint.

## Contributing
Contributions are welcome! Please feel free to fork the repository and submit a pull request with your changes.

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.