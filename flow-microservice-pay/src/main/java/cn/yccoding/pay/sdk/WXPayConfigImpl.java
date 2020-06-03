package cn.yccoding.pay.sdk;

import java.io.*;

import cn.yccoding.pay.config.ConstantProperties;

/**
 * @author : Chet
 * @description : WXPayConfig继承类
 * @date : 2019/11/1
 */
public class WXPayConfigImpl implements WXPayConfig {

    private byte[] certData;

    public WXPayConfigImpl() throws IOException {
        String certPath = ConstantProperties.APICLIENT_CERT;
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int)file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    // public WXPayConfigImpl() {}

    @Override
    public String getAppID() {
        return ConstantProperties.APP_ID;
    }

    @Override
    public String getMchID() {
        return ConstantProperties.MUCH_ID;
    }

    @Override
    public String getKey() {
        return ConstantProperties.API_KEY;
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
