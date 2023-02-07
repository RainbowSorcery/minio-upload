package com.tracetech.minioserver;

import com.tracetech.minioserver.utils.MinioHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MinioTest {
    @Autowired
    private MinioHelper minioHelper;

    @Test
    public void minioTest() {
        try {
            boolean b = minioHelper.makeBucket("wabawaba-bucket");
            System.out.println(b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
