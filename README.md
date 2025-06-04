# Play Framework by example

## What is it?
I am learning **Play Framework 2.9** for Java and I am creating boilerplate templates to show how to do basic stuff.

## What is included?
* Reading URL query parameters examples
* Creating, reading and deleting Cookie
* Creating, reading and deleting Session and protecting the resource behind the login 
* Database selects, inserts using [Hibernate 6.x](https://hibernate.org/) and JPA 
* Basic filter example
* Full example of how to [use html forms](https://github.com/ertra/play-framework-by-example/blob/master/app/controllers/FormExampleController.java) 
and [validations](https://github.com/ertra/play-framework-by-example/blob/master/app/controllers/forms/UserData.java), including showing form errors in UI

### Other examples
* Example of [running background task](https://github.com/ertra/play-framework-by-example/blob/master/app/jobs/SimpleMinuteRepeater.java) every minute
* Example implementation of [reCAPTCHA](https://www.google.com/recaptcha/about/) in Java from Google
* Example implementation of OAuth 2.0 in Java for Facebook and Google using [ScribeJava SDK](https://github.com/scribejava/scribejava)
* [Basic authentication example](https://github.com/ertra/play-framework-by-example/blob/master/app/controllers/BasicAuthenticationExampleController.java) in Java
* Login API example with generating JWT token and accessing protected API resource with this token

## How to run
1. Download the repository and unzip it.
2. Run the command "sbt run" from the repository directory.
3. Open web browser http://localhost:9000

## Stack used
* Tested with Java 21
* Play Framework 2.9
* H2 database for database example
* Hibernate 6.x for JPA example

## Github repo
https://github.com/ertra/play-framework-by-example

## Contact
Tomas Zeman: tomas.zeman via email from gmail.com<br/>


