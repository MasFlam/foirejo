
# Foirejo
Foirejo (Esperanto for *marketplace*) is an online marketplace where all payments are made in
Monero. You can buy and sell **anything** – physical goods, virtual products, and even services,
as long as it's legal. Think craigslist, but global and only using Monero.

# Contributing
The project is written in Java and uses Quarkus for the backend with Qute
for templating HTML. Read [CONTRIBUTING.md](CONTRIBUTING.md) to get started.

# License and Donations
Foirejo is shared under the terms of the GNU Affero General Public License version 3. This means
a CCS proposal is out of reach, since all source code for one must be under a permissive license.
Therefore, if you want to support the development of Foirejo, feel free to tip some XMR directly:

- MasFlam: ([QR Code](src/main/resources/META-INF/resources/masflam-xmr-qr.png))  
  `89D4wqCMjwvhxGWyux9PhkdMBuYBuRQSH2pc7LthSQ2ZJcuEzHfXd37KzsMamppcV3WPFbMsBLVziHWHHnzMubiWHEGJD8u`

A couple of contributions will land you on this list too, so feel incentivized ;).

<!--
# foirejo Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/foirejo-0.1.0-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Related Guides

- RESTEasy JAX-RS ([guide](https://quarkus.io/guides/rest-json)): REST endpoint framework implementing JAX-RS and more
-->
