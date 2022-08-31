package com.bt.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/29 14:50
 **/
public class WeiXinResult {
    @JSONField(name = "result")
    private ResultDTO result;

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public static class ResultDTO {
        @JSONField(name = "appid")
        private String appid;
        @JSONField(name = "bank_type")
        private String bankType;
        @JSONField(name = "cash_fee")
        private String cashFee;
        @JSONField(name = "fee_type")
        private String feeType;
        @JSONField(name = "is_subscribe")
        private String isSubscribe;
        @JSONField(name = "mch_id")
        private String mchId;
        @JSONField(name = "nonce_str")
        private String nonceStr;
        @JSONField(name = "openid")
        private String openid;
        @JSONField(name = "out_trade_no")
        private String outTradeNo;
        @JSONField(name = "result_code")
        private String resultCode;
        @JSONField(name = "return_code")
        private String returnCode;
        @JSONField(name = "sign")
        private String sign;
        @JSONField(name = "time_end")
        private String timeEnd;
        @JSONField(name = "total_fee")
        private String totalFee;
        @JSONField(name = "trade_type")
        private String tradeType;
        @JSONField(name = "transaction_id")
        private String transactionId;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getBankType() {
            return bankType;
        }

        public void setBankType(String bankType) {
            this.bankType = bankType;
        }

        public String getCashFee() {
            return cashFee;
        }

        public void setCashFee(String cashFee) {
            this.cashFee = cashFee;
        }

        public String getFeeType() {
            return feeType;
        }

        public void setFeeType(String feeType) {
            this.feeType = feeType;
        }

        public String getIsSubscribe() {
            return isSubscribe;
        }

        public void setIsSubscribe(String isSubscribe) {
            this.isSubscribe = isSubscribe;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }

        public String getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(String totalFee) {
            this.totalFee = totalFee;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }
    }
}
