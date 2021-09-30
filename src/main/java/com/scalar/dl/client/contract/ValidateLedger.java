package com.scalar.dl.client.contract;

import com.scalar.dl.ledger.asset.Asset;
import com.scalar.dl.ledger.contract.Contract;
import com.scalar.dl.ledger.database.AssetFilter;
import com.scalar.dl.ledger.database.Ledger;
import com.scalar.dl.ledger.exception.ContractContextException;
import java.util.List;
import java.util.Optional;
import javax.json.JsonObject;

public class ValidateLedger extends Contract {

  private static final String ASSET_ID_KEY = "asset_id";
  private static final String AGE_KEY = "age";

  @Override
  public JsonObject invoke(Ledger ledger, JsonObject argument, Optional<JsonObject> properties) {
    if (!argument.containsKey(ASSET_ID_KEY)) {
      throw new ContractContextException("please set asset_id in the argument");
    }
    String assetId = argument.getString(ASSET_ID_KEY);

    Optional<JsonObject> data;
    if (argument.containsKey(AGE_KEY)) {
      int age = argument.getInt(AGE_KEY);
      AssetFilter filter = new AssetFilter(ASSET_ID_KEY).withStartAge(age, true)
          .withEndAge(age, true);
      List<Asset> assets = ledger.scan(filter);
      if (assets.isEmpty()) {
        data = Optional.empty();
      } else {
        data = Optional.of(assets.get(0).data());
      }
    } else {
      data = ledger.get(assetId).map(Asset::data);
    }

    return data.orElse(null);
  }
}
