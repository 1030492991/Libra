package cn.coolhao.app.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密类
 * @desc 加密类
 */
public class Thick {
    /**
     * 加密算法
     */
    private static final String ENCRY_ALGORITHM = "AES";

    /**
     * 加密算法/加密模式/填充类型
     * 本例采用AES加密，ECB加密模式，PKCS5Padding填充
     */
    private static final String CIPHER_MODE = "AES/ECB/PKCS5Padding";

    /**
     * @Description: 32位字符 HEX加/解密密钥 每个项目需不同配置
     */
    private static final String ENCODE_KEY="pqVulLeDs3^cRNXpmqmoJfNjx*wUerqy";

    /**
     * 设置iv偏移量
     * 本例采用ECB加密模式，不需要设置iv偏移量
     */
    private static final String IV_ = null;

    /**
     * 设置加密字符集
     * 本例采用 UTF-8 字符集
     */
    private static final String CHARACTER = "UTF-8";

    /**
     * 设置加密密码处理长度。
     * 不足此长度补0；
     */
    private static final int PWD_SIZE = 16;
    //MD5加密字符
    public static final String KEY_MD5 = "MD5";
    /*
    * MD5加密
    * */
    private static String md5(String str){
        BigInteger bigInteger=null;
        try {
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            byte[] inputData = str.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {e.printStackTrace();}
        return bigInteger.toString(16);
    }
    /**
     * 原始加密
     * @param clearTextBytes 明文字节数组，待加密的字节数组
     * @param pwdBytes 加密密码字节数组
     * @return 返回加密后的密文字节数组，加密错误返回null
     */
    private static byte[] encrypt(byte[] clearTextBytes, byte[] pwdBytes) {
        try {
            // 1 获取加密密钥
            SecretKeySpec keySpec = new SecretKeySpec(pwdBytes, ENCRY_ALGORITHM);

            // 2 获取Cipher实例
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);

            // 查看数据块位数 默认为16（byte） * 8 =128 bit
//            System.out.println("数据块位数(byte)：" + cipher.getBlockSize());

            // 3 初始化Cipher实例。设置执行模式以及加密密钥
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // 4 执行
            byte[] cipherTextBytes = cipher.doFinal(clearTextBytes);

            // 5 返回密文字符集
            return cipherTextBytes;

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //======================>原始加密<======================
    /**
     * 原始解密
     * @param cipherTextBytes 密文字节数组，待解密的字节数组
     * @param pwdBytes 解密密码字节数组
     * @return 返回解密后的明文字节数组，解密错误返回null
     */
    private static byte[] decrypt(byte[] cipherTextBytes, byte[] pwdBytes) {

        try {
            // 1 获取解密密钥
            SecretKeySpec keySpec = new SecretKeySpec(pwdBytes, ENCRY_ALGORITHM);

            // 2 获取Cipher实例
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);

            // 查看数据块位数 默认为16（byte） * 8 =128 bit
//            System.out.println("数据块位数(byte)：" + cipher.getBlockSize());

            // 3 初始化Cipher实例。设置执行模式以及加密密钥
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            // 4 执行
            byte[] clearTextBytes = cipher.doFinal(cipherTextBytes);

            // 5 返回明文字符集
            return clearTextBytes;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解密错误 返回null
        return null;
    }
    /**
     * 密码处理方法
     * 如果加解密出问题，
     * 请先查看本方法，排除密码长度不足补"0",导致密码不一致
     * @param password 待处理的密码
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] pwdHandler(String password) throws UnsupportedEncodingException {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(PWD_SIZE);
        sb.append(password);
        while (sb.length() < PWD_SIZE) {
            sb.append("0");
        }
        if (sb.length() > PWD_SIZE) {
            sb.setLength(PWD_SIZE);
        }

        data = sb.toString().getBytes("UTF-8");

        return data;
    }
    //======================>BASE64<======================
    /**
     * BASE64加密
     * @param clearText 明文，待加密的内容
     * @param password 密码，加密的密码
     * @return 返回密文，加密后得到的内容。加密错误返回null
     */
//    public static String encryptBase64(String clearText, String password) {
//        try {
//            // 1 获取加密密文字节数组
//            byte[] cipherTextBytes = encrypt(clearText.getBytes(CHARACTER), pwdHandler(password));
//
//            // 2 对密文字节数组进行BASE64 encoder 得到 BASE6输出的密文
//            BASE64Encoder base64Encoder = new BASE64Encoder();
//            String cipherText = base64Encoder.encode(cipherTextBytes);
//
//            // 3 返回BASE64输出的密文
//            return cipherText;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 加密错误 返回null
//        return null;
//    }

    /**
     * BASE64解密
     * @param cipherText 密文，带解密的内容
     * @param password 密码，解密的密码
     * @return 返回明文，解密后得到的内容。解密错误返回null
     */
//    public static String decryptBase64(String cipherText, String password) {
//        try {
//            // 1 对 BASE64输出的密文进行BASE64 decodebuffer 得到密文字节数组
//            BASE64Decoder base64Decoder = new BASE64Decoder();
//            byte[] cipherTextBytes = base64Decoder.decodeBuffer(cipherText);
//
//            // 2 对密文字节数组进行解密 得到明文字节数组
//            byte[] clearTextBytes = decrypt(cipherTextBytes, pwdHandler(password));
//
//            // 3 根据 CHARACTER 转码，返回明文字符串
//            return new String(clearTextBytes, CHARACTER);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 解密错误返回null
//        return null;
//    }

    //======================>HEX<======================

    /**
     * HEX加密
     * @param clearText 明文，待加密的内容
     * @param password 密码，加密的密码
     * @return 返回密文，加密后得到的内容。加密错误返回null
     */
    private static String encryptHex(String clearText, String password) {
        try {
            // 1 获取加密密文字节数组
            byte[] cipherTextBytes = encrypt(clearText.getBytes(CHARACTER), pwdHandler(password));

            // 2 对密文字节数组进行 转换为 HEX输出密文
            String cipherText = byte2hex(cipherTextBytes);

            // 3 返回 HEX输出密文
            return cipherText;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 加密错误返回null
        return null;
    }

    /**
     * HEX解密
     * @param cipherText 密文，带解密的内容
     * @param password 密码，解密的密码
     * @return 返回明文，解密后得到的内容。解密错误返回null
     */
    private static String decryptHex(String cipherText, String password) {
        try {
            // 1 将HEX输出密文 转为密文字节数组
            byte[] cipherTextBytes = hex2byte(cipherText);

            // 2 将密文字节数组进行解密 得到明文字节数组
            byte[] clearTextBytes = decrypt(cipherTextBytes, pwdHandler(password));

            // 3 根据 CHARACTER 转码，返回明文字符串
            return new String(clearTextBytes, CHARACTER);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解密错误返回null
        return null;
    }

    /**
     * MD5加密封装
     * @Description: MD5加密封装
     * @Author: 谭浩
     * @Date: 2019/5/24 16:25
     * @Param: [content, key]
     * @Return: java.lang.String
     */
    public static String MD5(String content, String key) {
        try {
            // 1 将HEX输出密文 转为密文字节数组
            byte[] textBytes = hex2byte(content);

            // 2 将密文字节数组进行解密 得到明文字节数组
            byte[] clearTextBytes = encrypt(textBytes, pwdHandler(key));

            // 3 根据 CHARACTER 转码，返回明文字符串
            String str= new String(clearTextBytes, CHARACTER);
            return md5(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解密错误返回null
        return null;
    }

    /*字节数组转成16进制字符串  */
    private static String byte2hex(byte[] bytes) { // 一个字节的数，
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        String tmp = "";
        for (int n = 0; n < bytes.length; n++) {
            // 整数转成十六进制表示
            tmp = (Integer.toHexString(bytes[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    /*将hex字符串转换成字节数组 */
    private static byte[] hex2byte(String str) {
        if (str == null || str.length() < 2) {
            return new byte[0];
        }
        str = str.toLowerCase();
        int l = str.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = str.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }
    /**
     * @Description: 封装加密
     * @Author: 谭浩
     * @Date: 2019/5/24 16:21
     * @Param: [pwd]
     * @Return: java.lang.String
     */
    public static String encode(String pwd){
        return encryptHex(pwd,ENCODE_KEY);
    }
    /**
     * 封装解密
     * @Description: 封装解密
     * @Author: 谭浩
     * @Date: 2019/5/24 16:23
     * @Param: [pwd]
     * @Return: java.lang.String
     */
    public static String decode(String pwd){
        return decryptHex(pwd,ENCODE_KEY);
    }
}
