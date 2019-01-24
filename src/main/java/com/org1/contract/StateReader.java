package com.org1.contract;

import com.scalar.ledger.asset.Asset;
import com.scalar.ledger.asset.InternalAsset;
import com.scalar.ledger.contract.Contract;
import com.scalar.ledger.ledger.Ledger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Base64;
import java.util.Optional;

public class StateReader extends Contract {

  @Override
  public JsonObject invoke(Ledger ledger, JsonObject argument, Optional<JsonObject> properties) {
    String assetId = argument.getString("asset_id");

    Optional<Asset> asset = ledger.get(assetId);
    InternalAsset internal = (InternalAsset) asset.get();

    Base64.Encoder encoder = Base64.getEncoder();
    JsonObjectBuilder builder = Json.createObjectBuilder()
        .add("age", internal.age())
        .add("input", internal.input())
        .add("output", internal.data())
        .add("contract_id", internal.contractId())
        .add("argument", internal.argument())
        .add("signature", encoder.encodeToString(internal.signature()))
        .add("hash", encoder.encodeToString(internal.hash()));
    if (internal.prevHash() != null) {
      builder.add("prev_hash", encoder.encodeToString(internal.prevHash()));
    } else {
      builder.add("prev_hash", "");
    }
    return builder.build();
  }
}
