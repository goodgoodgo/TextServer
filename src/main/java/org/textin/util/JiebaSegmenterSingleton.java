package org.textin.util;

import com.huaban.analysis.jieba.JiebaSegmenter;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2022-12-23 20:21
 */
public class JiebaSegmenterSingleton {
    private static JiebaSegmenter instance;

    private JiebaSegmenterSingleton() {}

    public static JiebaSegmenter getInstance() {
        if (instance == null) {
            instance = new JiebaSegmenter();
        }
        return instance;
    }
}

