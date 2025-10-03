package co.com.bancopopular.automation.utils;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Base64;
import java.util.logging.Level;
import javax.crypto.Cipher;
import java.util.logging.Logger;


public class CipherUtil {

  private static final String ENCRYPTION_ALGORITHM = "RSA/ECB/OAEPWithSHA512AndMGF1Padding";
  private static final Logger LOGGER = Logger.getLogger(CipherUtil.class.getName());

  private CipherUtil(){
    throw new IllegalStateException("Utility class");
  }
  public static String cipherRSAOAEP512(String text) {
    try {
      byte[] textInBytes = text.getBytes();
      Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
      var cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM, "BC");
      var publicKey = loadPublicKey();
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] plainText = cipher.doFinal(textInBytes);
      return Base64.getEncoder().encodeToString(plainText);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "error encrypting info: ", e);
    }
    return "error";
  }

  private static Key loadPublicKey()
      throws NoSuchAlgorithmException, InvalidKeySpecException, ParseException {
    var jsonObject = new net.minidev.json.JSONObject();
    jsonObject.put("kty", "RSA");
    jsonObject.put("e", "AQAB");
    jsonObject.put("n",
        "zCi8mi3DgzVwwbVEhb3oTvWr73RG3CKfkVirxjpA5DLjBRslXICgBtVlMW3zkLqcz75f_cz0B9GqL2y6weRtGkPyUTaOuea1ZkeuGmAXDfXEdYpmRFxQ6D5B-49S8x4iUe-Jd4nL4qWuQGtWXNPKSu8_mOPKj64Nw5eA5IKLOdPZd3D0hrfWxNXWWEzcvbArVrhA00xgmZb23V2z_HufHVsnlyFlWKNcXmepB_76wlW5RKoh2Ns0ZbOrASvbTYB4NqLHZy3YQ0mkimNH1JOUmtZ-XeGIPOUpKPM_bah0BTaFOzFq8UaNLhs2_kgOfzgZI5bFx__JmwOYbVBhR_MAJw");
    jsonObject.put("alg", "RSA-OAEP-512");
    jsonObject.put("ext", true);
    jsonObject.put("kid", "my-public");
    var jsonResult = "{ \"keys\":[" + jsonObject + "]}";
    var jwkSet = JWKSet.parse(jsonResult);
    var key = (RSAKey) jwkSet.getKeys().get(0);
    var keyPair = new KeyPair(key.toRSAPublicKey(), key.toRSAPrivateKey());
    return keyPair.getPublic();
  }

}
