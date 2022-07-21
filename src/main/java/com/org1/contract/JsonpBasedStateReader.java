package com.org1.contract;

import com.scalar.dl.ledger.contract.JsonpBasedContract;
import com.scalar.dl.ledger.exception.ContractContextException;
import com.scalar.dl.ledger.statemachine.Asset;
import com.scalar.dl.ledger.statemachine.Ledger;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonObject;

public class JsonpBasedStateReader extends JsonpBasedContract {

  @Override
  @Nullable
  public JsonObject invoke(
      Ledger<JsonObject> ledger, JsonObject argument, @Nullable JsonObject properties) {
    if (!argument.containsKey("asset_id")) {
      // ContractContextException is the only throwable exception in a contract and
      // it should be thrown when a contract faces some non-recoverable error
      throw new ContractContextException("please set asset_id in the argument");
    }

    String assetId = argument.getString("asset_id");
    Optional<Asset<JsonObject>> asset = ledger.get(assetId);

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
