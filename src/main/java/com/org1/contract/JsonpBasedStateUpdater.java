package com.org1.contract;

import com.scalar.dl.ledger.contract.JsonpBasedContract;
import com.scalar.dl.ledger.exception.ContractContextException;
import com.scalar.dl.ledger.statemachine.Asset;
import com.scalar.dl.ledger.statemachine.Ledger;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonObject;

public class JsonpBasedStateUpdater extends JsonpBasedContract {

  @Override
  @Nullable
  public JsonObject invoke(
      Ledger<JsonObject> ledger, JsonObject argument, @Nullable JsonObject properties) {
    if (!argument.containsKey("asset_id") || !argument.containsKey("state")) {
      // ContractContextException is the only throwable exception in a contract and
      // it should be thrown when a contract faces some non-recoverable error
      throw new ContractContextException("please set asset_id and state in the argument");
    }

    String assetId = argument.getString("asset_id");
    int state = argument.getInt("state");

    Optional<Asset<JsonObject>> asset = ledger.get(assetId);

    if (!asset.isPresent() || asset.get().data().getInt("state") != state) {
      ledger.put(assetId, Json.createObjectBuilder().add("state", state).build());
    }

    return null;
  }
}
