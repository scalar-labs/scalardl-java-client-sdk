package com.org1.contract;

import com.fasterxml.jackson.databind.JsonNode;
import com.scalar.dl.ledger.contract.JacksonBasedContract;
import com.scalar.dl.ledger.exception.ContractContextException;
import com.scalar.dl.ledger.statemachine.Asset;
import com.scalar.dl.ledger.statemachine.Ledger;
import java.util.Optional;
import javax.annotation.Nullable;

/**
 * The contents of the contract are the same as {@link StateUpdater}. It is recommended to use
 * JacksonBasedContract for taking a good balance between development productivity and performance.
 */
public class JacksonBasedStateUpdater extends JacksonBasedContract {

  @Nullable
  @Override
  public JsonNode invoke(
      Ledger<JsonNode> ledger, JsonNode argument, @Nullable JsonNode properties) {
    if (!argument.has("asset_id") || !argument.has("state")) {
      // ContractContextException is the only throwable exception in a contract and
      // it should be thrown when a contract faces some non-recoverable error
      throw new ContractContextException("please set asset_id and state in the argument");
    }

    String assetId = argument.get("asset_id").asText();
    int state = argument.get("state").asInt();

    Optional<Asset<JsonNode>> asset = ledger.get(assetId);

    if (!asset.isPresent() || asset.get().data().get("state").asInt() != state) {
      ledger.put(assetId, getObjectMapper().createObjectNode().put("state", state));
    }

    return null;
  }
}
