package com.org1.contract;

import com.google.common.base.Splitter;
import com.scalar.dl.ledger.contract.StringBasedContract;
import com.scalar.dl.ledger.exception.ContractContextException;
import com.scalar.dl.ledger.statemachine.Asset;
import com.scalar.dl.ledger.statemachine.Ledger;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class StringBasedStateUpdaterReader extends StringBasedContract {

  @Nullable
  @Override
  public String invoke(Ledger<String> ledger, String argument, @Nullable String properties) {
    // <asset_id>,<status>
    List<String> elements = Splitter.on(',').splitToList(argument);
    if (elements.size() != 2) {
      throw new ContractContextException("invalid argument format");
    }
    String assetId = elements.get(0);
    int status = Integer.parseInt(elements.get(1));

    Optional<Asset<String>> asset = ledger.get(assetId);

    if (!asset.isPresent()
        || Integer.parseInt(Splitter.on(',').splitToList(asset.get().data()).get(1)) != status) {
      ledger.put(assetId, assetId + "," + status);
    }

    return invoke("string-state-reader", ledger, argument);
  }
}
