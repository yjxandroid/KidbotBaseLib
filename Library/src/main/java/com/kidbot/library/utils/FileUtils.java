package com.kidbot.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 文件操作类
 * User: YJX
 * Date: 2015-11-04
 * Time: 14:57
 */
public final class FileUtils {


    /**
     * 判断 SD 卡是否存在
     *
     * @return true 存在  false 不存在
     */
    @CheckResult
    public static boolean isExistsSDCard() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 得到更目录
     *
     * @return 目录路径
     */
    @CheckResult
    public static String getRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 得到项目名称
     *
     * @param context 上下文
     * @return 项目名称
     */
    @CheckResult
    private static String getProjectName(@NonNull Context context) {
        String packageName = context.getPackageName();
        return packageName.substring(packageName.indexOf('.') + 1, packageName.length());
    }

    /**
     * 创建项目根目录 并返回路径
     *
     * @param dirName 目录名称
     * @return 目录路径
     */
    public static String createProjectDir(String dirName) {
        String dirPath = getRootPath() + File.separator + dirName;
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dirPath;
    }

    /**
     * 创建项目根目录  并返回路径
     *
     * @param context 上下文
     * @return 目录路径
     * 注意：目录名称和项目名称一样
     */
    public static String createProjectDir(Context context) {
        return createProjectDir(getProjectName(context));
    }

    /**
     * 写文件
     *
     * @param file   目标文件
     * @param object 内容
     */
    public static void writeFile(@NonNull File file, @NonNull Object object ,@NonNull Bitmap.CompressFormat format) {
        FileOutputStream outputStream=null;
        try {
            outputStream= new FileOutputStream(file);
            if (object instanceof String) {
                String content = (String) object;
                byte[] bytes = content.getBytes(Charset.defaultCharset());
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
            }else if (object instanceof Bitmap){
                Bitmap bitmap= (Bitmap) object;
                bitmap.compress(format,100,outputStream);
                outputStream.flush();
                if (bitmap.isRecycled()){
                    bitmap.recycle();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 流转字符串
     * @param in 流
     * @return 字符串
     */
    @CheckResult
    public static String streamToString(InputStream in){
        StringBuilder sb=new StringBuilder();
        BufferedReader reader=new BufferedReader(new InputStreamReader(in));
        String content;
        try {
            while ((content=reader.readLine())!=null){
                sb.append(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return sb.toString();
    }


    /**
     * 得到文件的大小
     * @param file  文件
     * @return 文件大小
     */
    @CheckResult
    public static long getFileSize(@NonNull File file)
    {
        return file.length();
    }


    /**
     * 得到 SDCard 总容量
     * @return 总容量
     */
    @CheckResult
    public static long getSDCardTotalSize(){
        File file=Environment.getExternalStorageDirectory();
        StatFs statFs=new StatFs(file.getPath());
        long blockSzie=statFs.getBlockSizeLong();
        long totalBlock=statFs.getBlockCountLong();
        return blockSzie*totalBlock;
    }

    /**
     * 得到 SDCard 可用容量
     * @return 可用容量
     */
    @CheckResult
    public static long getSDCardAvailableSize(){
        File file=Environment.getExternalStorageDirectory();
        StatFs statFs=new StatFs(file.getPath());
        long blockSzie=statFs.getBlockSizeLong();
        long availableBlock=statFs.getAvailableBlocksLong();
        return blockSzie*availableBlock;
    }




}
