import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取MD5值工具类
 * 
 * 包括:
 * 1.获取某字符串的MD5
 * 2.获取某文件的MD5
 */
public class MD5Utils {

    /**
     * 获取某个字符串的MD5
     *
     * @param password 需要转换的字符串
     * @return 返回字符串结果
     */
    public static String getStringMd5(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int i = b & 0xff;//字节转整数
                String hexString = Integer.toHexString(i);//整数转16进制
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;//如果长度等于1, 加0补位
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            //如果算法不存在的话,就会进入该方法中
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取某个文件的MD5
     *
     * @param path 文件的路径
     * @return 返回字符串结果
     */
    public static String getFileMd5(String path) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(path);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            byte[] result = digest.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int i = b & 0xff;//字节转整数
                String hexString = Integer.toHexString(i);//整数转16进制
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;//如果长度等于1, 加0补位
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
