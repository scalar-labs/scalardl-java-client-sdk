# Getting Started with Scalar DL Sandbox

This document explains how to get started with Scalar DL Sandbox.

## Purpose of Sandbox

Sandbox is for playing around with Scalar DL to roughly understand what Scalar DL does and how to write contracts.
This is not for verifying or benchmarking its reliability, scalability and/or performance.
If you want to interact with Scalar DL more deeply, please [contact us](https://scalar-labs.com/contact_us/).

## Get an auth token and a key pair (a certificate and a private key)

From here, it assumes that you have a [GitHub](https://github.com/) account.
If you don't, please create your account.

We will authorize you through GitHub OAuth to give you an access to the Sandbox environment.
Please visit [our sandbox site](https://scalar-labs.com/sandbox/), read the [terms of use](https://scalar-labs.com/terms-of-use), and press the button to do it.
We will give you a zip file containing an access token, a key pair and configuration file.
The access token is used for communicating with Sandbox API gateway to authenticate you.
The key pair is used for communicating with Scalar DL network.

Please note that we give you a generated key pair for ease of use in the Sandbox, but it is usually required to create your private key in your own environment.

## Before running your first contract 

Let's clone Scalar DL client SDK to interact with Scalar DL network.
```
$ git clone https://github.com/scalar-labs/scalardl-client-sdk.git 
```
You can put your downloaded zip in the directory and unzip it.

Scalar DL manages data as a set of assets, where each asset is composed of a history of data identified by a key called `asset_id` and historical version number called `age`.
`asset_id` is an arbitrary, but unique, string specified by users to manage their assets.

However, the Sandbox is a shared environment that anyone can access,
so please be careful about choosing an appropriate name so that it will not conflict with `asset_id`s chosen by other users.
One recommended way to do this is to append your username to your asset name as in `<username>-<your-asset-name>`.
Also, the same care is needed in choosing contract IDs. So, we recommend using `<username>-<your-contract-class-name>`.

## Register your certificate

Next, let's register your certificate in the Scalar DL network.
The registered certificate will allow you to register and execute contracts, and will also be used for tamper detection of the data stored in the network.

In the `scalardl-client-sdk` directory:
```
$ client/bin/register-cert -properties client.properties
```

## Run the StateUpdater contract

We will run the contract [`scr/main/java/com/org1/contract/StateUpdater.java`](https://github.com/scalar-labs/scalardl-client-sdk/blob/master/src/main/java/com/org1/contract/StateUpdater.java), which manages status of some asset.

In the `scalardl-client-sdk` directory:

1. Compile the contract

    ```
    $ ./gradlew assemble
    ```

    This will generate `build/classes/java/main/com/org1/contract/StateUpdater.class`.

2. Register the contract

    NOTE: Please replace `<username>` with your GitHub username.

    ```
    $ client/bin/register-contract -properties client.properties -contract-id <username>-StateUpdater -contract-binary-name com.org1.contract.StateUpdater -contract-class-file build/classes/java/main/com/org1/contract/StateUpdater.class
    ```

3. Execute the contract

    NOTE: Please replace `<username>` with your GitHub username.
    ```
    $ client/bin/execute-contract -properties client.properties -contract-id <username>-StateUpdater -contract-argument '{"asset_id": "<username>-myasset", "state": 3}'
    ```
  
## What's next

Please take a look at [Getting Started with Scalar DL](dl-getting-started.md) to know a little more about what is going on. 

## References

* [Getting Started with Scalar DL](dl-getting-started.md)
* Design Document (coming soon)
* [Javadoc for client SDK](https://scalar-labs.github.io/scalardl-client-sdk/javadoc/client/)
