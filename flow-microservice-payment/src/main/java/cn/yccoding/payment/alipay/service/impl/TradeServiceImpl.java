package cn.yccoding.payment.alipay.service.impl;

import cn.yccoding.payment.alipay.model.TradePaySummary;
import cn.yccoding.payment.alipay.sdk.config.Configs;
import cn.yccoding.payment.alipay.sdk.model.ExtendParams;
import cn.yccoding.payment.alipay.sdk.model.GoodsDetail;
import cn.yccoding.payment.alipay.sdk.model.builder.AlipayTradePrecreateRequestBuilder;
import cn.yccoding.payment.alipay.sdk.model.builder.AlipayTradeQueryRequestBuilder;
import cn.yccoding.payment.alipay.sdk.model.result.AlipayF2FPrecreateResult;
import cn.yccoding.payment.alipay.sdk.model.result.AlipayF2FQueryResult;
import cn.yccoding.payment.alipay.sdk.service.AlipayTradeService;
import cn.yccoding.payment.alipay.sdk.service.impl.AlipayTradeServiceImpl;
import cn.yccoding.payment.alipay.sdk.utils.Utils;
import cn.yccoding.payment.alipay.sdk.utils.ZxingUtils;
import cn.yccoding.payment.alipay.service.TradeService;
import cn.yccoding.payment.property.AliPayTradeProperties;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * impl
 *
 * @author YC
 * @since 2020/9/29
 */
@Slf4j
@Service
public class TradeServiceImpl implements TradeService {
    // 支付宝当面付2.0服务
    private static final AlipayTradeService alipayTradeService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("payment/zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        alipayTradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    @Autowired
    private AliPayTradeProperties aliPayTradeProperties;

    @Override
    public String f2fPayTradePay(@RequestBody TradePaySummary tradePaySummary) {
        // 订单列表
        List<TradePaySummary.Item> items = tradePaySummary.getItems();
        //支付二维码的访问路径
        String qrCodePath = null;

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = "flow" + System.currentTimeMillis() + (long) (Math.random() * 10000000L);

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
        String subject = tradePaySummary.getSubject();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = items.stream().map(item -> new BigDecimal(item.getPrice())).reduce(new BigDecimal("0"), BigDecimal::add).toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0.0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
        String body = String.format("购买商品%s件共%s元", items.size(), totalAmount);

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = tradePaySummary.getStoreId();

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        String providerId = "2088100200300400500";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(providerId);

        // 支付超时，线下扫码交易定义为5分钟
        String timeoutExpress = "15m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        List<GoodsDetail> instances = items.stream().map(item -> {
            long price = new BigDecimal(item.getPrice()).multiply(new BigDecimal(100)).longValue();
            GoodsDetail instance = GoodsDetail.newInstance(item.getGoodsId(), item.getGoodsName(), price, item.getQuantity());
            return instance;
        }).collect(Collectors.toList());
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.addAll(instances);

        // 创建支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(tradePaySummary.getOperatorId()).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                // 支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setNotifyUrl(aliPayTradeProperties.getCallbackUrl())
                .setGoodsDetailList(goodsDetailList);

        // 调用tradePay方法获取当面付应答
        AlipayF2FPrecreateResult result = alipayTradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝支付成功: )");
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                // 生成二维码保存路径，创建二维码
                String filename = String.format("/qr-%s.png", response.getOutTradeNo());
                // 本地绝对路径：/Users/yc/temp/qr-code/alipay/qr-123.png
                String storePath = aliPayTradeProperties.getQrcode().getStorePath()+filename;
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, storePath);
                // http请求相对路径：http://localhost:9150/static/qrcode/alipay/qr-123.png
                qrCodePath = aliPayTradeProperties.getQrcode().getHttpBasePath() + filename;
                break;

            case FAILED:
                log.error("支付宝支付失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return qrCodePath;
    }

    @Override
    public AlipayF2FQueryResult f2fTradeQuery(String outTradeNo) {
        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = alipayTradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");

                AlipayTradeQueryResponse response = result.getResponse();
                dumpResponse(response);
                log.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        log.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                break;

            case FAILED:
                log.error("查询返回该订单支付失败或被关闭!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return result;
    }

    /**
     * 简单打印应答
     *
     * @param response
     */
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }
}
