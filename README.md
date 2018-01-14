# Rest-Assured Automation Example
This is an example application of Rest-Assured as a basis for API test fremwork. I don't hold any strict convention, I'm just showing different posible options one can have for handling requests, responses and assertions.
You can see both generic Rest-Assured assertions (Hamcrest Matchers) being used here along with JUnit ones and even AssertJ assertions.

## API
API used in this example is based on very easy to use  [`json-server` npm package](https://github.com/typicode/json-server). Database file used as source can be found in `db.json` file.

### Endpoints
- `/users` - all users
- `/pets` - users' pets

## Framework

### Structure
This project is your standard Maven Java project with `src` folders and `POM.xml`.

### Models
`src/main/java/models` represent API entities with class properties equal to JSON response fields. This lets us serialize and deserialize  requests and responses easily.
In order for the serialization to work properly, names of the fields must match JSON convention thus they violate Java camel case convention (it can be overriden using `@JsonProperty` annotations in real-life projects).

### Properties
`src/test/resources/config.properties` is a simple properties file to store various configurations

### Tests
`src/main/java/TestBase.class` is the tests superclass for configuration and common code
`src/test/java/` holds test classes (JUnit4) 