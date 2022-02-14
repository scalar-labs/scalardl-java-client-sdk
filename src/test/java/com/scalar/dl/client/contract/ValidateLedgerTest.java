package com.scalar.dl.client.contract;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scalar.dl.ledger.asset.Asset;
import com.scalar.dl.ledger.database.AssetFilter;
import com.scalar.dl.ledger.database.AssetFilter.AgeOrder;
import com.scalar.dl.ledger.database.Ledger;
import com.scalar.dl.ledger.exception.ContractContextException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.json.Json;
import javax.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ValidateLedgerTest {
  private static final String SOME_ASSET_ID = "some_asset_id";
  private static final int SOME_AGE = 2;
  private static final int SOME_START_AGE = 1;
  private static final int SOME_END_AGE = 3;
  @Mock private Ledger ledger;
  private ValidateLedger validateLedger;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    validateLedger = new ValidateLedger();
  }

  private List<Asset> createMockAssets(String assetId, int startAge, int endAge) {
    return IntStream.range(startAge, endAge + 1)
        .mapToObj(
            i -> {
              Asset asset = mock(Asset.class);
              when(asset.id()).thenReturn(assetId);
              when(asset.age()).thenReturn(i);
              when(asset.data()).thenReturn(JsonObject.EMPTY_JSON_OBJECT);
              return asset;
            })
        .collect(Collectors.toList());
  }

  @Test
  public void invoke_AgeGiven_ShouldReturnOneSpecifiedAssetOnly() {
    // Arrange
    JsonObject argument =
        Json.createObjectBuilder()
            .add(ValidateLedger.ASSET_ID_KEY, SOME_ASSET_ID)
            .add(ValidateLedger.AGE_KEY, SOME_AGE)
            .build();
    List<Asset> assets = createMockAssets(SOME_ASSET_ID, SOME_AGE, SOME_AGE);
    when(ledger.scan(any(AssetFilter.class))).thenReturn(assets);

    // Act
    JsonObject result = validateLedger.invoke(ledger, argument, Optional.empty());

    // Assert
    AssetFilter expected =
        new AssetFilter(SOME_ASSET_ID)
            .withStartAge(SOME_AGE, true)
            .withEndAge(SOME_AGE, true)
            .withAgeOrder(AgeOrder.ASC);
    verify(ledger).scan(expected);
    assertThat(result.getJsonArray(SOME_ASSET_ID).size()).isEqualTo(1);
    assertThat(
            result.getJsonArray(SOME_ASSET_ID).get(0).asJsonObject().getInt(ValidateLedger.AGE_KEY))
        .isEqualTo(assets.get(0).age());
  }

  @Test
  public void invoke_StartAgeEndAgeGiven_ShouldReturnSpecifiedAssets() {
    // Arrange
    JsonObject argument =
        Json.createObjectBuilder()
            .add(ValidateLedger.ASSET_ID_KEY, SOME_ASSET_ID)
            .add(ValidateLedger.START_AGE_KEY, SOME_START_AGE)
            .add(ValidateLedger.END_AGE_KEY, SOME_END_AGE)
            .build();
    List<Asset> assets = createMockAssets(SOME_ASSET_ID, SOME_START_AGE, SOME_END_AGE);
    when(ledger.scan(any(AssetFilter.class))).thenReturn(assets);

    // Act
    JsonObject result = validateLedger.invoke(ledger, argument, Optional.empty());

    // Assert
    AssetFilter expected =
        new AssetFilter(SOME_ASSET_ID)
            .withStartAge(SOME_START_AGE, true)
            .withEndAge(SOME_END_AGE, true)
            .withAgeOrder(AgeOrder.ASC);
    verify(ledger).scan(expected);
    assertThat(result.getJsonArray(SOME_ASSET_ID).size()).isEqualTo(assets.size());
    for (int i = 0; i < assets.size(); i++) {
      assertThat(
              result
                  .getJsonArray(SOME_ASSET_ID)
                  .get(i)
                  .asJsonObject()
                  .getInt(ValidateLedger.AGE_KEY))
          .isEqualTo(assets.get(i).age());
    }
  }

  @Test
  public void invoke_OnlyAssetIdGiven_ShouldReturnAllAssets() {
    // Arrange
    JsonObject argument =
        Json.createObjectBuilder().add(ValidateLedger.ASSET_ID_KEY, SOME_ASSET_ID).build();
    // create some asset but it's not used for verification since the range is open
    List<Asset> assets = createMockAssets(SOME_ASSET_ID, 0, 0);
    when(ledger.scan(any(AssetFilter.class))).thenReturn(assets);

    // Act
    validateLedger.invoke(ledger, argument, Optional.empty());

    // Assert
    AssetFilter expected =
        new AssetFilter(SOME_ASSET_ID)
            .withStartAge(0, true)
            .withEndAge(Integer.MAX_VALUE, true)
            .withAgeOrder(AgeOrder.ASC);
    verify(ledger).scan(expected);
  }

  @Test
  public void invoke_AssetIdNotGiven_ShouldThrowContractContextException() {
    // Arrange
    JsonObject argument = Json.createObjectBuilder().add(ValidateLedger.AGE_KEY, SOME_AGE).build();

    // Act
    Throwable thrown =
        catchThrowable(() -> validateLedger.invoke(ledger, argument, Optional.empty()));

    // Assert
    assertThat(thrown).isExactlyInstanceOf(ContractContextException.class);
  }
}
