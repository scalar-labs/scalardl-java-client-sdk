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
We will give you an access token and a key pair.
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
One recommended way to do this is to append your username to your asset name as in `<username>_<your-asset-name>`.
Also, the same care is needed in choosing contract IDs. So, we recommend using `<username>-<your-contract-class-name>`.

## Run your first contract

First, you need to configure some properties to interact with Sandbox.
Please use the properties file which can be downloaded from the site.
Then you are ready to follow [the doc](dl-getting-started.md) to run your first contract.
(Again, please note that you need to choose non-conflicting asset ids and contract ids to properly use the Sandbox.)

## References

* [Getting Started with Scalar DL](dl-getting-started.md)
* Design Document (coming soon)
* [Javadoc for client SDK](https://scalar-labs.github.io/scalardl-client-sdk/javadoc/client/)
