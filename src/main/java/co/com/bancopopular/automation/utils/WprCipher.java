package co.com.bancopopular.automation.utils;

import co.com.adl.pb.commons.sdk.cipher.dto.SymmetricKeyResponseDto;
import co.com.adl.pb.commons.sdk.cipher.logic.*;
import co.com.adl.pb.commons.sdk.cipher.util.RsaSecurityHelper;
import co.com.bancopopular.automation.rest.apis.ServicePaths;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import co.com.bancopopular.automation.utils.SessionHelper.SessionData;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.http.HttpStatus;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import static co.com.bancopopular.automation.constants.Constants.MAX_ATTEMPTS;
import static co.com.bancopopular.automation.utils.SessionHelper.getFromSession;
import static co.com.bancopopular.automation.utils.SessionHelper.setInSession;


public class WprCipher {
  private static SymmetricKeyResponseDto symmetricKeyResponseDto;
  private static String xSymmetricKey;
  private static String xSymmetricIv;
  private static final String PUBLIC_KEY_JSON_PATH = "publicKey";

  private WprCipher() {}

  public static Callable<Boolean> getKeysCipher() {
    return () -> {
      SymmetricKeyResponseDto myResponse = WprCipher.getKeys();
      if(!myResponse.getKey().isEmpty()){
        xSymmetricKey = myResponse.getKey();
        setInSession(SessionData.XSYMMETRICKEY, xSymmetricKey);
        xSymmetricIv = myResponse.getIv();
        setInSession(SessionData.XSYMMETRICIV, xSymmetricIv);
        String xSecuritySession = myResponse.getId();
        setInSession(SessionData.XSECURITYSESSION, xSecuritySession);
        var requestId = UUID.randomUUID().toString();
        requestId = SecureRequestEncryptor.encryptRequest(requestId, xSymmetricKey, xSymmetricIv);
        setInSession(SessionData.XSECURITYREQUESTID, requestId);
        return true;
      }
      return false;
    };
  }

  private static SymmetricKeyResponseDto getKeys() throws MalformedURLException {
    if (Objects.isNull(symmetricKeyResponseDto)) {
      var clientKeyPair = RsaSecurityHelper.generateKeyPair(2048);

      var body =
          SymmetricKeyRequestGenerator.buildGetSymmetricKeyRequestAsString(
              getPublicKey(), clientKeyPair);

      var clientPrivateKey = RsaSecurityHelper.exportPrivateKey(clientKeyPair.getPrivate());

      var myBodyStr =
          SerenityRest.given()
              .and()
              .contentType(ContentType.JSON)
              .and()
              .body(body)
              .when()
              .post(ServicePaths.getSimmetricKey())
              .then()
              .extract()
              .body()
              .asString();
      symmetricKeyResponseDto = SymmetricKeyResponseDecryptor.decrypt(myBodyStr, clientPrivateKey);
    }
    return symmetricKeyResponseDto;
  }

  private static String getPublicKey() throws MalformedURLException {
    String cipherServerPublicKey = null;
    String responsePublicKeyBody = null;
    for(var x = 0 ; x < MAX_ATTEMPTS ;x++) {
        responsePublicKeyBody = SerenityRest.given()
              .and()
              .contentType(ContentType.JSON)
              .when()
              .get(ServicePaths.getPublicKey())
              .then()
              .statusCode(200)
              .and()
              .extract()
              .body()
              .asString();
      if (SerenityRest.then().extract().statusCode() == HttpStatus.SC_OK) {
        break;
      }
      if (SerenityRest.then().extract().statusCode() == HttpStatus.SC_BAD_GATEWAY) {
        RequestsUtil.pause(30);
      }
    }

    var parserPublicKey = new JSONParser();
    JSONObject jsonPublicKey = null;
    try {
      jsonPublicKey = (JSONObject) parserPublicKey.parse(responsePublicKeyBody);
      cipherServerPublicKey = jsonPublicKey.get(PUBLIC_KEY_JSON_PATH).toString();
    } catch (Exception e) {
      e.toString();
    }

    return cipherServerPublicKey;
  }

  public static String generateEncryptedBody(String body) {
    return SecureRequestEncryptor.encryptRequest(body, xSymmetricKey, xSymmetricIv);
  }

  public static String generateSecurityHmac(String body, String httpRequest) {
  return SecureRequestValidator.calculateHmac(httpRequest, body, symmetricKeyResponseDto.getHmacKey());
  }

  public static JsonPath decryptRequest(String encryptedMsj){
    var decryptedRspBdy =
        SecureRequestDecryptor.decryptRequest(
            encryptedMsj,
            getFromSession(SessionData.XSYMMETRICKEY),
            getFromSession(SessionData.XSYMMETRICIV));

    return new JsonPath(decryptedRspBdy);
  }

  public static JsonPath extractDecryptResponse(){
    var encryptedBody = SerenityRest.then().extract().body().asString();
    return decryptRequest(encryptedBody);
  }

  public static byte[] encode(String text)  {
    return Base64.getEncoder().encode(text.getBytes(StandardCharsets.UTF_8));
  }

  public static String decode(byte[] textEncode){
    return Arrays.toString(Base64.getDecoder().decode(textEncode));
  }
}
