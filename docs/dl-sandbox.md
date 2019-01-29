# Getting Started with Scalar DL Sandbox

This document explains how to get started with Scalar DL Sandbox.

## Purpose of Sandbox

Sandbox is for playing around with Scalar DL to roughly understand what Scalar DL does and how to write contracts.
This is not for verifying or benchmarking its reliability, scalability and/or performance.
If you want to interact with Scalar DL more deeply, please [contact us](https://scalar-labs.com/contact_us/).

## Before you begin

You will need to have your `client.properties` file, certificate, and private key that you downloaded from [Sandbox Registration](https://scalar-labs.com/sandbox/). If you have not yet registered, please do so now.

## Download the example code

Download the example code from our Github repository (the following command will clone the Scalar DL client SDK).

```
$ git clone https://github.com/scalar-labs/scalardl-client-sdk.git 
```

Then copy your `client.properties` file, certificate, and private key to the `conf` directory in the repository.

## Register your certificate

Next, let's register your certificate in the Scalar DL network. The registered certificate will allow you to register and execute contracts, and will also be used for tamper detection of the data stored in the network.

You may register your certificate with the following command.

```
$ client/bin/register-cert -properties conf/client.properties
```


## Run your first contract

Now you are ready to follow the "[Run your first contract](dl-run-first-contract.md)" document to run your first contract.


##### Note on choosing asset ids in the Sandbox

The Sandbox is a shared environment that anyone can access, so please be careful about choosing an appropriate name for your `asset_id`s so that it will not conflict with `asset_id`s chosen by other users. One recommended way to do this is to prepend your username to your asset name as in `<username>_your-asset-name`.

## References

* [Getting Started with Scalar DL](dl-getting-started.md)
* Design Document (coming soon)
* [Javadoc for client SDK](https://scalar-labs.github.io/scalardl-client-sdk/javadoc/client/)
