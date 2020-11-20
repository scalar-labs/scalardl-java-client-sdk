package com.org1.function;

import com.scalar.db.api.Get;
import com.scalar.db.api.Put;
import com.scalar.db.api.Result;
import com.scalar.db.io.BigIntValue;
import com.scalar.db.io.Key;
import com.scalar.db.io.TextValue;
import com.scalar.dl.ledger.database.Database;
import com.scalar.dl.ledger.function.Function;
import java.util.Optional;
import javax.json.JsonObject;

public class Deposit extends Function {

  private final static String NAMESPACE = "account";
  private final static String TABLE = "balance";
  private final static String ACCOUNTS_ATTRIBUTE_NAME = "accounts";
  private final static String ID_ATTRIBUTE_NAME = "id";
  private final static String AMOUNT_ATTRIBUTE_NAME = "amount";
  private final static String BALANCE_ATTRIBUTE_NAME = "balance";
  private final static String OP_ATTRIBUTE_NAME = "OP";
  private final static String OP_ADD = "ADD";
  private final static String OP_SUB = "SUB";

  @Override
  public void invoke(Database database,
      Optional<JsonObject> functionArgument,
      JsonObject contractArgument,
      Optional<JsonObject> properties) {
    String id = contractArgument.getString(ID_ATTRIBUTE_NAME);
    int amount = contractArgument.getInt(AMOUNT_ATTRIBUTE_NAME);

    Optional<Result> account = database
        .get(new Get(new Key(new TextValue(ID_ATTRIBUTE_NAME, id))).forNamespace(NAMESPACE)
            .forTable(TABLE));

    long balance = 0;
    if (account.isPresent()) {
      balance = ((BigIntValue) account.get().getValue(BALANCE_ATTRIBUTE_NAME).get()).get();
    }

    // Transfer
    balance += amount;

    Put put1 =
        new Put(new Key(new TextValue(ID_ATTRIBUTE_NAME, id)))
            .withValue(new BigIntValue(BALANCE_ATTRIBUTE_NAME, balance))
            .forNamespace(NAMESPACE).forTable(TABLE);

    database.put(put1);
  }
}
