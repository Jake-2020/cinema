package cinema.demo.util;

import java.util.UUID;

public class CinemaUtil {
    /**
     * 生成随机字符串
     * @return  随机字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("_", "");
    }
}
