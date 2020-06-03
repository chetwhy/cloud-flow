package cn.yccoding.pay.sdk;

import java.io.*;

import cn.yccoding.wpp.config.bean.WCPConfigParamsYML;

/**
 * @author : Chet
 * @description : WXPayConfig继承类
 * @date : 2019/11/1
 */
public class WXPayConfigYMLImpl implements WXPayConfig {

    private WCPConfigParamsYML wcpConfigParamsYML;

    private byte[] certData;

    // 需要证书，yml注入
    public WXPayConfigYMLImpl(WCPConfigParamsYML wcpConfigParamsYML) throws IOException {
        this.wcpConfigParamsYML = wcpConfigParamsYML;
        String certPath = wcpConfigParamsYML.getApiclientCert();
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int)file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public WXPayConfigYMLImpl() {}

    public void setCertData(byte[] certData) {
        this.certData = certData;
    }

    public void setWcpConfigParamsYML(WCPConfigParamsYML wcpConfigParamsYML) {
        this.wcpConfigParamsYML = wcpConfigParamsYML;
    }

    @Override
    public String getAppID() {
        return wcpConfigParamsYML.getAppId();
    }

    @Override
    public String getMchID() {
        return wcpConfigParamsYML.getMuchId();
    }

    @Override
    public String getKey() {
        return wcpConfigParamsYML.getApiKey();
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return null;
    }
}
