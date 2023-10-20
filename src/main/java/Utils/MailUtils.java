package Utils;

import com.aliyun.tea.TeaException;

public class MailUtils {
    // This file is auto-generated, don't edit it. Thanks.
        /**
         * 使用AK&SK初始化账号Client
         * @param accessKeyId
         * @param accessKeySecret
         * @return Client
         * @throws Exception
         */
        public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
            com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                    // 必填，您的 AccessKey ID
                    .setAccessKeyId(accessKeyId)
                    // 必填，您的 AccessKey Secret
                    .setAccessKeySecret(accessKeySecret);
            // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
            config.endpoint = "dysmsapi.aliyuncs.com";
            return new com.aliyun.dysmsapi20170525.Client(config);
        }

        public static void MailPush(String Code) throws Exception {
            // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
            // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
            com.aliyun.dysmsapi20170525.Client client = MailUtils.createClient(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"), System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
            com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                    .setPhoneNumbers("18483686406")
                    .setSignName("FBCD笔记浏览系统")
                    .setTemplateCode("SMS_463628074")
                    .setTemplateParam("{\"code\":\""+Code+"\"}");
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
            try {
                // 复制代码运行请自行打印 API 的返回值
                client.sendSmsWithOptions(sendSmsRequest, runtime);
            } catch (TeaException error) {
                // 如有需要，请打印 error
                com.aliyun.teautil.Common.assertAsString(error.message);
            } catch (Exception _error) {
                TeaException error = new TeaException(_error.getMessage(), _error);
                // 如有需要，请打印 error
                com.aliyun.teautil.Common.assertAsString(error.message);
            }
        }
}
