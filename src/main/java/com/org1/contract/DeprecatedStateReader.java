package com.org1.contract;

import com.scalar.dl.ledger.asset.Asset;
import com.scalar.dl.ledger.contract.Contract;
import com.scalar.dl.ledger.database.Ledger;
import com.scalar.dl.ledger.exception.ContractContextException;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonObject;

public class DeprecatedStateReader extends Contract {

  @Override
  public JsonObject invoke(Ledger ledger, JsonObject argument, Optional<JsonObject> properties) {
    if (!argument.containsKey("asset_id")) {
      // ContractContextException is the only throwable exception in a contract and
      // it should be thrown when a contract faces some non-recoverable error
      throw new ContractContextException("please set asset_id in the argument");
    }

    String assetId = argument.getString("asset_id");
    Optional<Asset> asset = ledger.get(assetId);

    return asset
        .map(
            value ->
                Json.createObjectBuilder()
                    .add("id", value.id())
                    .add("age", value.age())
                    .add("output", value.data())
                    .build())
        .orElse(null);
  }
}
