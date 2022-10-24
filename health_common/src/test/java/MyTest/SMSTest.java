package MyTest;

import com.aliyuncs.exceptions.ClientException;
import utils.SMSUtils;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName SMSTest
 * @date 2022/8/15 09:52
 */

public class SMSTest {
    public static void main(String[] args) throws ClientException {
        SMSUtils.sendShortMessage("SMS_217915240","18345926109","lihongxinghaha");
    }
}
