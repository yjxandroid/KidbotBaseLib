package com.kidbot.library.utils;

import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串早做工具类
 * User: YJX
 * Date: 2015-11-09
 * Time: 09:48
 */
public final class StringUtils {

    /**
     * 得到32位的 md5加密
     * @param content 字符串
     * @return 返回 加密后的内容
     */
    @CheckResult
    public static String getMd5_32(@Nullable String content)
    {
        StringBuilder sb=new StringBuilder();
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            messageDigest.update(content.getBytes());
            byte [] bytes=messageDigest.digest();
            int length=bytes.length;
            int i=0;
            for (int offset = 0; offset < length; offset++) {
                i = bytes[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    sb.append("0");
                sb.append(Integer.toHexString(i));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
