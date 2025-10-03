package co.com.bancopopular.automation.utils;

import co.com.bancopopular.automation.data.Environment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aeonbits.owner.ConfigFactory;
import redis.clients.jedis.Jedis;

public class RedisUtil {

    private static final Environment environment;

    static {
        environment = ConfigFactory.create(Environment.class);
    }

    public String getRedis(String keyRedis) {
        Jedis jedis = new Jedis(environment.redis(), 6379);
        String value = jedis.get(keyRedis);
        jedis.close();
        return value;
    }

    public String getRedisPayrollDate(String document,String paymentIncomeDt) {
        try (Jedis jedis = new Jedis(environment.redis(), 6379)) {
            var redisKey = "CC_"+document+"_UserSessionInfo";
            var json = jedis.get(redisKey);

            if (json.contains("\\\"")) {
                json = json.replace("\\\"", "\"")
                        .replace("\"{", "{")
                        .replace("}\"", "}");
            }

            var mapper = new ObjectMapper();
            var root = mapper.readTree(json);
            var dateNode = root.get(paymentIncomeDt);

            return dateNode != null ? dateNode.asText() : "paymentIncomeDt not found";
        } catch (Exception e) {
            return "Error retrieving paymentIncomeDt: " + e.getMessage();
        }
    }

}