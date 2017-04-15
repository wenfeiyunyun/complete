package hello;

import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

public class WechatPayment {
    public String partnerCode = "TOPC";
    public String credentialCode = "QF7ezz0HhZlnuBrwnsXuSd7QRg0EqRK4";
    public String nonce = UUID.randomUUID().toString();
    public long unixTime = System.currentTimeMillis();
    public String url;
    public String sign;
    public int fee;
    public String json;
    public String result_code;
    public String return_code;

    public void printSign() {
        System.out.println(sign);
    }

    public String getSign() {
        String validString = new StringBuilder(partnerCode).append("&").append(unixTime).append("&").append(nonce).append("&").append(credentialCode).toString();
        StringBuffer signBuffer = new StringBuffer();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(validString.getBytes("UTF-8"));

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) signBuffer.append('0');
                signBuffer.append(hex);
            }
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        sign = signBuffer.toString();
/*        System.out.println("WeChat Sign     is:|"+signString);
        System.out.println("WeChat nonce    is:|"+nonce);
        System.out.println("WeChat unixTime is:|"+unixTime);*/
        return sign;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getCredentialCode() {
        return credentialCode;
    }

    public void setCredentialCode(String credentialCode) {
        this.credentialCode = credentialCode;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }

/*    public String getSignString() {
        return signString;
    }*/

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }
}
