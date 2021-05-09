package com.berry.eagleeye.management.common.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP
     *
     * @param srcDir 压缩文件夹路径
     * @param out    压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, OutputStream out) throws Exception {
        ZipOutputStream zos = new ZipOutputStream(out);
        File sourceFile = new File(srcDir);
        try {
            compress(sourceFile, zos, sourceFile.getName());
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩
     *
     * @param inputStream
     * @param entryName
     * @param out
     * @throws IOException
     */
    public static void doZip(InputStream inputStream, String entryName, ZipOutputStream out) throws IOException {
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);
        IOUtils.copy(inputStream, out);
        inputStream.close();
    }


    /**
     * 递归压缩方法
     *
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            int len;
            try (FileInputStream in = new FileInputStream(sourceFile)) {
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
            }
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                zos.putNextEntry(new ZipEntry(name + "/"));
                zos.closeEntry();
            } else {
                for (File file : listFiles) {
                    compress(file, zos, name + "/" + file.getName());
                }
            }
        }
    }

}