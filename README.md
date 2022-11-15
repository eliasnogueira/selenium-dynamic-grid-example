# Selenium Dynamic Grid Example

Selenium 4 has the Selenium Grid project where we can use docker images to run the test targeting a container with a
browser.
We can approach this in different ways, and one of the most beneficial for multi-browser testing is using the
Dynamic Grid.

Note that this project contains basic examples using TestNG and JUnit 5.
The main intent is to give you examples to run multi-browser parallel tests.

Don't forget to give this project a :star

## How to run this project

1. Navigate to the `grid` folder
```shell
cd grid
```

2. Start the Selenium Grid

* if you have a MacOS machine first run in your
  Terminal `socat -4 TCP-LISTEN:2375,fork UNIX-CONNECT:/var/run/docker.sock`

```shell
docker-compose up
```

3. Open the grid dashboard at http://localhost:4444/ui/index.html

4. Run the test

* `src/test/java/com.eliasnogueira.junit.MultiBrowserDetectorTest` for JUnit 5
* `src/test/resources/testng/parallel.xml` for TestNG

## How this project is structured

### Grid

In the `grid` folder we will see two files:

* `docker-compose.yml`
* `config.toml`

### General

#### Configuration

The `general.properties` file has basic common values like URL, timeout, and headless execution.
The `grid.properties` file has the url and port of the grid, to configure it.

Normally we add those values in the properties files to easily change these values running the test using a CI/CD tool.

The class `Configuration` has all the bindings to the properties file, where the `ConfigurationManager` is responsible
to load it.
To make this happen the Owner library is in use.

#### Browser Factory

The `BrowserFactory` class is an enumeration that returns the browser options.
These driver options are used in the `TargetFactory` which will create a remote execution using the selected browser.
The `DriverManager` adds the driver in a thread to avoid concurrency issues.

Both `BaseWeb` classes, placed in the `test` package use the `TargetFactory` to create the remote browser instance for
the test execution.

```java
driver=new TargetFactory().createInstance(browser);
```

### Using TestNG to run multi-browser parallel tests

The TestNG tests are located at `src/test/java/com.eliasnogueira.testng` and they are composed by:

- `BaseWeb`: base test class which has the pre and post-test conditions to create the browser instance and quit it
- `BrowserDetectorTest`: test run a parameterized test based on the implemented browsers

#### How TestNG runs the tests in parallel

TestNG has a built-in feature to run parallel tests using a test suite, which is an XML file to group different tests for
execution. The `<suite>` tag has the `parallel` attribute, which is the indication of what should be run in parallel.

The `BaseWeb` class has a pre-condition that will match the required parameter with the parameter set by each test in
the `src/test/java/resources/testng/parallel.xml` file.

### Using JUnit to run multi-browser parallels tests

The JUnit 5 tests are located at `src/test/java/com.eliasnogueira.junit` and they are composed by:

- `BaseWeb`: base test class which has the post-test condition to quit the browser and a method to create the browser
  instance which will be used by the test
- `MultiBrowserDetectorTest`: test run a parameterized test based on the implemented browsers

#### How JUnit run the tests in parallel

We know that the `MultiBrowserDetectorTest` will run the test for each browser present in the `BrowserFactory` class.

The file `junit-platform.properties` has configurations set to run the tests in parallel, in a concurrent mode, and with
a fixed number of parallel tests. You can take a look at the file to see the configurations set.
