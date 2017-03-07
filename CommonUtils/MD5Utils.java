import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取MD5值工具类
 */
public class MD5Utils {

    /**
     * 获取某个字符串的md5
     *
     * @param password 需要转换的字符串密码
     * @return 返回字符串值结果
     */
    public static String getStringMd5(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(password.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int i = b & 0xff;// 将字节转为整数
                String hexString = Integer.toHexString(i);// 将整数转为16进制

                if (hexString.length() == 1) {
                    hexString = "0" + hexString;// 如果长度等于1, 加0补位
                }

                sb.append(hexString);
            }

            // System.out.println(sb.toString());//打印得到的md5
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            // 如果算法不存在的话,就会进入该方法中
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取某个文件的md5
     *
     * @param path 文件的路径
     * @return 返回字符串值结果
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
                int i = b & 0xff;// 将字节转为整数
                String hexString = Integer.toHexString(i);// 将整数转为16进制

                if (hexString.length() == 1) {
                    hexString = "0" + hexString;// 如果长度等于1, 加0补位
                }

                sb.append(hexString);
            }

            //System.out.println(sb.toString());// 打印得到的md5
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}