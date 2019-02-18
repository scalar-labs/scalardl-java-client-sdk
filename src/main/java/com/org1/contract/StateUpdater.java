package com.org1.contract;

import com.scalar.ledger.asset.Asset;
import com.scalar.ledger.contract.Contract;
import com.scalar.ledger.exception.ContractContextException;
import com.scalar.ledger.ledger.Ledger;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonObject;

public class StateUpdater extends Contract {

  @Override
  public JsonObject invoke(Ledger ledger, JsonObject argument, Optional<JsonObject> properties) {
    if (!argument.containsKey("asset_id") || !argument.containsKey("state")) {
      // ContractContextException is the only throwable exception in a contract and
      // it should be thrown when a contract faces some non-recoverable error
      throw new ContractContextException("please set asset_id and state in the argument");
    }

    String assetId = argument.getString("asset_id");
    int state = argument.getInt("state");

    Optional<Asset> asset = ledger.get(assetId);

    if (!asset.isPresent() || asset.get().data().getInt("state") != state) {
      ledger.put(assetId, Json.createObjectBuilder().add("state", state).build());
    }

    return null;
  }
}
