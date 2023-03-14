package org.textin.util;

import com.tencentcloudapi.asr.v20190614.AsrClient;
import com.tencentcloudapi.asr.v20190614.models.SentenceRecognitionRequest;
import com.tencentcloudapi.asr.v20190614.models.SentenceRecognitionResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import java.util.Base64;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-11 21:20
 */
public class RecognitionUtil {
    public static String speechRecognition( byte[] audioBytes) throws TencentCloudSDKException {
        String audioBase64 = Base64.getEncoder().encodeToString(audioBytes);
        SentenceRecognitionRequest req = new SentenceRecognitionRequest();
        req.setUsrAudioKey("test");
        req.setSubServiceType(2L);
        req.setProjectId(0L);
        req.setEngSerViceType("16k_zh");
        req.setVoiceFormat("wav");
        req.setData(audioBase64);
        req.setSourceType(1L);
        Credential cred = new Credential("AKIDnSwEI7oJbfU6UcV9amkU1LPGtJbfT5Td", "xe7PTIuJTN3VihRzJODoDOL3ZsypSr2y");
        AsrClient client = new AsrClient(cred, "ap-shanghai");
        SentenceRecognitionResponse resp = client.SentenceRecognition(req);
        return resp.getResult();
    }
}
