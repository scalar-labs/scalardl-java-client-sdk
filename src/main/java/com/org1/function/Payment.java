package com.org1.function;

import com.scalar.db.api.Get;
import com.scalar.db.api.Put;
import com.scalar.db.api.Result;
import com.scalar.db.io.BigIntValue;
import com.scalar.db.io.Key;
import com.scalar.db.io.TextValue;
import com.scalar.dl.ledger.database.Database;
import com.scalar.dl.ledger.exception.ContractContextException;
import com.scalar.dl.ledger.function.Function;
import java.util.Optional;
import javax.json.JsonArray;
import javax.json.JsonObject;

public class Payment extends Function {

  private final static String NAMESPACE = "account";
  private final static String TABLE = "balance";
  private final static String ACCOUNTS_ATTRIBUTE_NAME = "accounts";
  private final static String AMOUNT_ATTRIBUTE_NAME = "amount";
  private final static String ID_ATTRIBUTE_NAME = "id";
  private final static String BALANCE_ATTRIBUTE_NAME = "balance";

  @Override
  public void invoke(Database database,
      Optional<JsonObject> functionArgument,
      JsonObject contractArgument,
      Optional<JsonObject> properties) {
    JsonArray array = contractArgument.getJsonArray(ACCOUNTS_ATTRIBUTE_NAME);
    int amount = contractArgument.getInt(AMOUNT_ATTRIBUTE_NAME);
    String fromId = array.getString(0);
    String toId = array.getString(1);

    Optional<Result> account1 = database
        .get(new Get(new Key(new TextValue(ID_ATTRIBUTE_NAME, fromId))).forNamespace(NAMESPACE)
            .forTable(TABLE));
    Optional<Result> account2 = database
        .get(new Get(new Key(new TextValue(ID_ATTRIBUTE_NAME, toId))).forNamespace(NAMESPACE)
            .forTable(TABLE));

    long balance1 = ((BigIntValue) account1.get().getValue(BALANCE_ATTRIBUTE_NAME).get()).get();
    long balance2 = ((BigIntValue) account2.get().getValue(BALANCE_ATTRIBUTE_NAME).get()).get();

    // Transfer
    balance1 -= amount;
    balance2 += amount;
    if (balance1 < 0) {
      throw new ContractContextException("sender doesn't have enough balance.");
    }

    Put put1 =
        new Put(new Key(new TextValue(ID_ATTRIBUTE_NAME, fromId)))
            .withValue(new BigIntValue(BALANCE_ATTRIBUTE_NAME, balance1)).forNamespace(NAMESPACE)
            .forTable(TABLE);
    Put put2 =
        new Put(new Key(new TextValue(ID_ATTRIBUTE_NAME, toId)))
            .withValue(new BigIntValue(BALANCE_ATTRIBUTE_NAME, balance2)).forNamespace(NAMESPACE)
            .forTable(TABLE);

    database.put(put1);
    database.put(put2);
  }
}
