package com.org1.contract;

import com.google.common.base.Splitter;
import com.scalar.dl.ledger.contract.StringBasedContract;
import com.scalar.dl.ledger.exception.ContractContextException;
import com.scalar.dl.ledger.statemachine.Asset;
import com.scalar.dl.ledger.statemachine.Ledger;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class StringBasedStateReader extends StringBasedContract {

  @Nullable
  @Override
  public String invoke(Ledger<String> ledger, String argument, @Nullable String properties) {
    // <asset_id>
    List<String> elements = Splitter.on(',').splitToList(argument);
    if (elements.size() < 1) {
      throw new ContractContextException("invalid argument format");
    }

    String assetId = elements.get(0);
    Optional<Asset<String>> asset = ledger.get(assetId);

    return asset
        .map(value -> String.join(",", value.id(), String.valueOf(value.age()), value.data()))
        .orElse(null);
  }
}
