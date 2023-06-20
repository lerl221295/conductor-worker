# Netflix Conductor Task Worker

This project is a Tasks Worker built with the Netflix Conductor client library that fetches data from a protected endpoint. It also includes an Auth0 client that renews the JWT when it's expired.

## Requirements
* Docker
* An Auth0 account and API credentials
* Basic auth credentials for reqrepo access

## Installation (only once)
1. Create a new application-local.properties file and provide auth0 credentials: `auth0.client-id` and `auth0.client-secret` 
2. Run `task setup-conductor` that will pull a local version of Netflix Conductor to be used for local development
3. Run `docker compose up` so the instance of Conductor and the Worker get started
4. Run `task setup-workflow` that will hit Conductor server to create the Workflow Definition. Make sure env `BASIC_AUTH` is exported

## Test
`task start-wf` will trigger a new workflow

## Run with Docker
`docker compose up`

## Run app locally
* `docker compose up conductor-server`
* Run the app using Gradle or your IDE
* (Optional) Run the Conductor UI `docker compose up conductor-ui`

## Usage
Once the project is running, it will automatically start listening and executing the tasks defined in the workflow configuration file. If the JWT expires, the Auth0 client will automatically renew it and update the headers for the requests made to the protected endpoint.

## Contributing
Contributions are welcome! Please feel free to fork the repository and submit a pull request with your changes.

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.