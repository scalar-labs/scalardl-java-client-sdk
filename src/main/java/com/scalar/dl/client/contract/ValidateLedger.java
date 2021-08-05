package com.scalar.dl.client.contract;

import com.scalar.dl.ledger.contract.Contract;
import com.scalar.dl.ledger.database.AssetFilter;
import com.scalar.dl.ledger.database.Ledger;
import com.scalar.dl.ledger.exception.ContractContextException;
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

    // Simply reading the specified assets to return the corresponding AssetProofs
    if (argument.containsKey(AGE_KEY)) {
      int age = argument.getInt(AGE_KEY);
      AssetFilter filter = new AssetFilter(ASSET_ID_KEY).withStartAge(age, true)
          .withEndAge(age, true);
      ledger.scan(filter);
    } else {
      ledger.get(assetId);
    }

    return null;
  }
}
