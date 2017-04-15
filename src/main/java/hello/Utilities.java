package hello;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class Utilities {
    public Utilities() {
    }

    public String shippmentID(String carrierTaken, String shipNo, String customer_id) {
        String carrierCode = new String();
        if(carrierTaken.equalsIgnoreCase("澳邮")) {
            carrierCode = "2176";
        }else carrierCode = "2176";
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
        String strTime= formatter.format(today);
        shipNo = carrierCode + customer_id.split("\\|")[0] + strTime;
        return shipNo;
    }

    public List parseJSON(String jsonStr) {
        List<String> jsonList = new ArrayList();
        JSONObject jObject = new JSONObject(jsonStr.trim());

        Iterator<String> jobject_keys = ((JSONObject) jObject).keys();
        do {
            String jobject_key = jobject_keys.next();
            JSONArray items = (JSONArray) jObject.get(jobject_key);
            for(Object item: items) {
                if (item instanceof JSONObject) {
                    Iterator<String> keys = ((JSONObject) item).keys();
                    do {
                        String key = keys.next();
                        String value = ((JSONObject) item).getString(key);
                        jsonList.add(key + ":" + value);
                    } while (keys.hasNext());
                }
            }
        } while(jobject_keys.hasNext());
        return jsonList;
    }

    public WechatRefund wechatPayRefundPutRequest(String order_id, double fee) {
        WechatPayment wechatPayment = new WechatPayment();
        order_id     = "11000055";
        String refund_id = order_id+"_"+wechatPayment.getUnixTime();
        wechatPayment.setFee((int)(fee*100));
        String url = "https://mpay.royalpay.com.au/api/v1.0/gateway/partners/"+wechatPayment.getPartnerCode()+"/orders/"+order_id+"/refunds/"+refund_id+
                "?"+"valid_string="+wechatPayment.getPartnerCode()+"&time="+wechatPayment.getUnixTime()+
                    "&nonce_str="  +wechatPayment.getNonce()      +"&sign="+wechatPayment.getSign();
        wechatPayment.setUrl(url);
        String json = "{\"fee\":"+wechatPayment.getFee()+"}"; //"{\"fee\":\"0.01\"}"; "{fee:0.01}";
        wechatPayment.setJson(json);
        printWechatPayInfo(wechatPayment);
        HttpPut httpRequest = new HttpPut(url);
        String s = httpJsonWithResponse(url, json, httpRequest);

        /////////////////////////////////////////////////////
        /* convert JSON response to Java and update name */
        WechatRefund wechatRefund = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            wechatRefund = mapper.readValue(s, WechatRefund.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       /////////////////////////////////////////////////////
        return wechatRefund;
    }

    public void  wechatPayGetRequest(String refund_id) {
        WechatPayment wechatPayment = new WechatPayment();
        String order_id = refund_id.split("_")[0];
        String url = "https://mpay.royalpay.com.au/api/v1.0/gateway/partners/"+wechatPayment.getPartnerCode()+"/orders/"+order_id+"/refunds/"+refund_id+
                "?"+"valid_string="+wechatPayment.getPartnerCode()+"&time="+wechatPayment.getUnixTime()+
                "&nonce_str="  +wechatPayment.getNonce()      +"&sign="+wechatPayment.getSign();
        wechatPayment.setUrl(url);
        printWechatPayInfo(wechatPayment);
        HttpGet httpRequest = new HttpGet(url);

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(httpRequest);
            httpResponseProcess(response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String httpJsonWithResponse(String url, String json, HttpEntityEnclosingRequestBase httpRequest) {
        /* Prepare put request */
        httpRequest.setHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity jsonData = new StringEntity(json, "UTF-8");
        /* Body of request */
        httpRequest.setEntity(jsonData);
        String s = httpRequestExecute(httpRequest);
        return s;
    }

    private String httpRequestExecute(HttpEntityEnclosingRequestBase httpRequest) { //this Only for HTTP PUT and POST requests
        String s = null;
        try {
            /* Execute URL and attach after execution response handler */
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(httpRequest);
            s = httpResponseProcess(response);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return s;
    }

    private String httpResponseProcess(HttpResponse response) throws IOException {
        HttpEntity entity = null;
        entity = response.getEntity();
        String s = EntityUtils.toString(entity, "UTF-8");
        System.out.println(s);
        return s;
        /////////////////////////////////////////////////////
        /* convert JSON response to Java and update name */
        //ObjectMapper mapper = new ObjectMapper()
        //WechatPayment wechatPay = mapper.readValue(s, WechatPayment.class);
        /////////////////////////////////////////////////////
    }

    public void printWechatPayInfo(WechatPayment wechatPayment) {
        System.out.println("WeChat Sign     is:|"+wechatPayment.getSign());
        System.out.println("WeChat nonce    is:|"+wechatPayment.getNonce());
        System.out.println("WeChat unixTime is:|"+wechatPayment.getUnixTime());
        System.out.println("WeChat Refund Json:|"+wechatPayment.getFee());
        System.out.println("WeChat HTTP URL:   |"+wechatPayment.getUrl());
    }
}