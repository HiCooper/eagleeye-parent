package com.berry.eagleeye.management.common.utils;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2020/8/28 11:00
 * fileName：FileUtils
 * Use：
 */
public class FileUtils {

    /**
     * 根据文件目录递归获取其下所有指定匹配类型的文件，包括任意层级子目录下匹配的文件
     *
     * @param file       目录
     * @param acceptType 匹配类型
     * @return 文件集合
     */
    public static List<File> getAllFileByPath(String filePath, List<String> acceptType) {
        File file = new File(filePath);
        List<File> fileList = new ArrayList<>(16);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                return Lists.newArrayList();
            }
            for (File item : listFiles) {
                if (item.isFile() && acceptType.stream().anyMatch(item.getName().toLowerCase()::endsWith)) {
                    fileList.add(item);
                } else if (item.isDirectory()) {
                    List<File> temp = getAllFileByPath(item.getPath(), acceptType);
                    if (!CollectionUtils.isEmpty(temp)) {
                        fileList.addAll(temp);
                    }
                }
            }
        }
        return fileList;
    }

}
