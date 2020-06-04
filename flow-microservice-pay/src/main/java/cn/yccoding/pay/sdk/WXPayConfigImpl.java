package cn.yccoding.pay.sdk;

import cn.yccoding.pay.util.ConstantPropertyUtils;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author : Chet
 * @description : WXPayConfig继承类
 * @date : 2019/11/1
 */
public class WXPayConfigImpl implements WXPayConfig {

    private byte[] certData;

    /**
     * 加载证书
     */
    /*public WXPayConfigImpl() throws IOException {
        String certPath = ConstantPropertyUtils.APICLIENT_CERT;
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int)file.length()];
        certStream.read(this.certData);
        certStream.close();
    }*/

    public WXPayConfigImpl() {}

    @Override
    public String getAppID() {
        return ConstantPropertyUtils.APP_ID;
    }

    @Override
    public String getMchID() {
        return ConstantPropertyUtils.MUCH_ID;
    }

    @Override
    public String getKey() {
        return ConstantPropertyUtils.API_KEY;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }
}
