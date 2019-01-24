package com.org1.contract;

import com.scalar.ledger.contract.Contract;
import com.scalar.ledger.ledger.Ledger;
import com.scalar.ledger.asset.Asset;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Optional;

public class StateUpdater extends Contract {

  @Override
  public JsonObject invoke(Ledger ledger, JsonObject argument, Optional<JsonObject> properties) {
    String assetId = argument.getString("asset_id");
    int state = argument.getInt("state");

    Optional<Asset> asset = ledger.get(assetId);

    if (!asset.isPresent() || asset.get().data().getInt("state") != state) {
      ledger.put(assetId, Json.createObjectBuilder().add("state", state).build());
    }

    return null;
  }
}
