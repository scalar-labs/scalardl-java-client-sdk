package com.org1.contract;

import com.scalar.dl.ledger.asset.Asset;
import com.scalar.dl.ledger.contract.Contract;
import com.scalar.dl.ledger.database.Ledger;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

public class Payment extends Contract {

  private final static String ACCOUNTS_ATTRIBUTE_NAME = "accounts";
  private final static String AMOUNT_ATTRIBUTE_NAME = "amount";
  private final static String BALANCE_ATTRIBUTE_NAME = "balance";
  private final static String OP_ATTRIBUTE_NAME = "OP";
  private final static String OP_ADD = "ADD";
  private final static String OP_SUB = "SUB";

  @Override
  public JsonObject invoke(Ledger ledger, JsonObject argument, Optional<JsonObject> properties) {
    JsonArray array = argument.getJsonArray(ACCOUNTS_ATTRIBUTE_NAME);
    String fromId = array.getString(0);
    String toId = array.getString(1);
    int amount = argument.getInt(AMOUNT_ATTRIBUTE_NAME);

    Asset from = ledger.get(fromId).get();
    Asset to = ledger.get(toId).get();

    ledger.put(
        fromId, Json.createObjectBuilder().add(OP_ATTRIBUTE_NAME, OP_SUB + " " + amount).build());
    ledger.put(toId,
        Json.createObjectBuilder().add(OP_ATTRIBUTE_NAME, OP_ADD + " " + amount).build());

    return null;
  }
}
