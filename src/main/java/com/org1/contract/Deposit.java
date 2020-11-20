package com.org1.contract;

import com.scalar.dl.ledger.contract.Contract;
import com.scalar.dl.ledger.database.Ledger;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonObject;

public class Deposit extends Contract {

  private final static String ID_ATTRIBUTE_NAME = "id";
  private final static String AMOUNT_ATTRIBUTE_NAME = "amount";
  private final static String OP_ATTRIBUTE_NAME = "OP";
  private final static String OP_ADD = "ADD";

  @Override
  public JsonObject invoke(Ledger ledger, JsonObject argument, Optional<JsonObject> properties) {
    String id = argument.getString(ID_ATTRIBUTE_NAME);
    int amount = argument.getInt(AMOUNT_ATTRIBUTE_NAME);

    ledger.get(id);
    ledger.put(
        id, Json.createObjectBuilder().add(OP_ATTRIBUTE_NAME, OP_ADD + " " + amount).build());

    return null;
  }

}
