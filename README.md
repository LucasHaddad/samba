# SambaTech

SambaTech's implementation test for backend developer position.

# Project

The project has a swagger editor implementation that can be used to get the information about it's routes/paths, through `scheme://host:port/swagger-ui.html`. If not modified the full url shoud be: `http://localhost:8080/swagger-ui.html`.
It also has linting enabled on compile time.

## Installing

First you need Java 11 (the best option, used in development is the Oracle OpenJDK 11).
Second of all, you need gradle (the used for project building is the 5.6.2).

## Building

To build the project just go to it's root and run `gradle build`.

## Running

To run it, just do as follows: `gradle run`.

## Testing

If you want to test it run `gradle test`, and all the code coverage reports can be found at `PROJECT_ROOT/build/generated/reports/samba/index.html`.