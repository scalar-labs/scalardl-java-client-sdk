package com.scalar.dl.client.contract;

import com.scalar.dl.ledger.asset.Asset;
import com.scalar.dl.ledger.contract.Contract;
import com.scalar.dl.ledger.database.AssetFilter;
import com.scalar.dl.ledger.database.AssetFilter.AgeOrder;
import com.scalar.dl.ledger.database.Ledger;
import com.scalar.dl.ledger.exception.ContractContextException;
import java.util.List;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class ValidateLedger extends Contract {
  static final String ASSET_ID_KEY = "asset_id";
  static final String AGE_KEY = "age";
  static final String START_AGE_KEY = "start_age";
  static final String END_AGE_KEY = "end_age";
  static final String DATA_KEY = "data";

  @Override
  public JsonObject invoke(Ledger ledger, JsonObject argument, Optional<JsonObject> properties) {
    if (!argument.containsKey(ASSET_ID_KEY)) {
      throw new ContractContextException("please set asset_id in the argument");
    }
    String assetId = argument.getString(ASSET_ID_KEY);

    int startAge = 0;
    int endAge = Integer.MAX_VALUE;
    if (argument.containsKey(AGE_KEY)) {
      int age = argument.getInt(AGE_KEY);
      startAge = age;
      endAge = age;
    } else {
      if (argument.containsKey(START_AGE_KEY)) {
        startAge = argument.getInt(START_AGE_KEY);
      }
      if (argument.containsKey(END_AGE_KEY)) {
        endAge = argument.getInt(END_AGE_KEY);
      }
    }

    AssetFilter filter =
        new AssetFilter(assetId)
            .withStartAge(startAge, true)
            .withEndAge(endAge, true)
            .withAgeOrder(AgeOrder.ASC);
    List<Asset> assets = ledger.scan(filter);

    JsonArrayBuilder builder = Json.createArrayBuilder();
    assets.forEach(
        a ->
            builder.add(
                Json.createObjectBuilder().add(AGE_KEY, a.age()).add(DATA_KEY, a.data()).build()));

    return Json.createObjectBuilder().add(assetId, builder.build()).build();
  }
}
