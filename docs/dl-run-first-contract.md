# Run your first contract

## Before you begin

You should have already downloaded the `scalardl-client-sdk` directory and registered your certificate. If you haven't ... **add links to other docs here**

## Run the StateUpdater contract

We will run the contract [`scr/main/java/com/org1/contract/StateUpdater.java`](https://github.com/scalar-labs/scalardl-client-sdk/blob/master/src/main/java/com/org1/contract/StateUpdater.java).

In the `scalardl-client-sdk` directory:

1. Compile the contract

    ```
    $ ./gradlew assemble
    ```

    This will generate `build/classes/java/main/com/org1/contract/StateUpdater.class`.

2. Register the contract

    ```
    $ client/bin/register-contract -properties conf/client.properties -contract-id StateUpdater -contract-binary-name com.org1.contract.StateUpdater -contract-class-file build/classes/java/main/com/org1/contract/StateUpdater.class
    ```

    The `contract-id` is set by the user. Above we have chosen `StateUpdater`, but you can choose anything you like. You can set different contract IDs on the same contract to clarify "who did what" in a tamper-evident way. For example, consider a voting application. In the application, anyone can vote with the same voting logic, and hence can use the same Contract, but A's vote and B's vote need to be properly and securely distinguished; A cannot vote for B, and vice versa. This can be achieved by having different contract IDs on the same contract.

3. Execute the contract

    ```
    $ client/bin/execute-contract -properties conf/client.properties -contract-id StateUpdater -contract-argument '{"asset_id": "abcdef", "state": 3}'
    ```

    **Note:** if you are executing the contract in the Sandbox you may want to change the `asset_id`.
    

## What's next

Anything to add to this section?




## (This should probably be moved or deleted) Interact with ClientService 

The tools we have used above are useful for testing purposes, but should not be used for production applications. The Client SDK also provides a service layer called `ClientService` which should be used for production applications.
The following is a code snippet showing how to use `ClientService` to execute a contract.

```java
Injector injector =
  Guice.createInjector(new ClientModule(new ClientConfig(new File(properties))));

try (ClientService service = injector.getInstance(ClientService.class)) {
  JsonObject jsonArgument = Json.createReader(new StringReader(contractArgument)).readObject();
  ContractExecutionResponse response = service.executeContract(contractId, jsonArgument);
  System.out.println("status: " + response.getStatus());
  System.out.println("result: " + response.getResult());
}
```

It's pretty self-explanatory. It creates a `ClientService` instance using the `Guice` dependency injection framework, and creates a json argument with `javax.json.JsonObject`. Then, it executes the contract and prints out the response.

For more information, please take a look at [Javadoc](https://scalar-labs.github.io/scalardl-client-sdk/javadoc/client/).

## References

* Design Document (coming soon)
* [Javadoc for client SDK](https://scalar-labs.github.io/scalardl-client-sdk/javadoc/client/)
