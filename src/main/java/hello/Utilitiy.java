/*
package hello;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Utilitiy {
    public Utilitiy() {
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

    public void  wechatPayPutRequest(String order_id, double fee) {
        WechatPayment wechatPayment = new WechatPayment();

        order_id     = "11000055";
        String refund_id = "RF"+order_id+"_"+wechatPayment.getUnixTime();

        String url = "https://mpay.royalpay.com.au/api/v1.0/gateway/partners/"+wechatPayment.getPartnerCode()+"/orders/"+order_id+"/refunds/"+refund_id+
                "?"+
                "valid_string="+wechatPayment.getPartnerCode()+
                "&time="+wechatPayment.getUnixTime()+
                "&nonce_str="+wechatPayment.getNonce()+
                "&sign="+wechatPayment.getSign();
        //url="https://mpay.royalpay.com.au/api/v1.0/gateway/partners/"+partner_code                  +"/orders/"+order_id+"/refunds/"+refund_id+
        //      "?"+"valid_string="+partner_code                  +"&time="+unixTime                      +"&nonce_str="+nonc                    +"&sign="+signString;
        String json = "{\"fee\":"+(int)(fee*100)+"}"; //"{\"fee\":\"0.01\"}"; "{fee:0.01}";
        //String json = "{\"fee\":1}"; //"{\"fee\":\"0.01\"}"; "{fee:0.01}";

        System.out.println("WeChat Sign     is:|"+wechatPayment.getSign());
        System.out.println("WeChat nonce    is:|"+wechatPayment.getNonce());
        System.out.println("WeChat unixTime is:|"+wechatPayment.getUnixTime());
        System.out.println("WeChat Refund Json:|"+json);
        System.out.println("WeChat PUT URL:    |"+url);

        HttpPut httpPut = new HttpPut(url);
        httpJsonWithResponse(url, json, httpPut);
    }

    public void  wechatPayGetRequest(String order_id) {
        WechatPayment wechatPayment = new WechatPayment();

        order_id     = "11000055";
        String refund_id = "RF"+order_id+"_"+wechatPayment.getUnixTime();
        String url = "https://mpay.royalpay.com.au/api/v1.0/gateway/partners/"+wechatPayment.getPartnerCode()+"/orders/"+order_id+"/refunds/"+refund_id;

        //String json = "{\"fee\":"+(int)(fee*100)+"}"; //"{\"fee\":\"0.01\"}"; "{fee:0.01}";
        String json = "{\"fee\":0}"; //"{\"fee\":\"0.01\"}"; "{fee:0.01}";

        System.out.println("WeChat Sign     is:|"+wechatPayment.getSign());
        System.out.println("WeChat nonce    is:|"+wechatPayment.getNonce());
        System.out.println("WeChat unixTime is:|"+wechatPayment.getUnixTime());
        System.out.println("WeChat Refund Json:|"+json);
        System.out.println("WeChat PUT URL:    |"+url);

        HttpGet httpGet = new HttpGet(url);
        httpJsonWithResponse(url, json, httpGet);
    }

    public void httpJsonWithResponse(String url, String json, HttpRequestBase httpRequest) {
        //String CONTENT_TYPE_TEXT_JSON = "text/json";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        */
/* Prepare put request *//*

        //HttpPut httpPut = new HttpPut(url);
        httpRequest.setHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity jsonData = new StringEntity(json, "UTF-8");

        */
/* Body of request *//*

        httpRequest.setEntity(jsonData);

        */
/* Response handler for after request execution *//*

        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                */
/* Get status code *//*

                int httpResponseCode = httpResponse.getStatusLine().getStatusCode();
                System.out.println("Response code: " + httpResponseCode);
                if (httpResponseCode >= 200 && httpResponseCode < 300) {
                    */
/* Convert response to String *//*

                    HttpEntity entity = httpResponse.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    return null;
                    */
/* throw new ClientProtocolException("Unexpected response status: " + httpResponseCode); *//*

                }
            }
        };

    try {
        */
/* Execute URL and attach after execution response handler *//*

        HttpResponse response2 = null;

        response2 = httpClient.execute(httpRequest);
        HttpEntity entity2 = null;
        entity2 = response2.getEntity();
        String s2 = EntityUtils.toString(entity2, "UTF-8");
        System.out.println(s2);

        */
/* Print the response *//*

        //System.out.println("Response: " + strResponse);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /////////////////////////////////////////////////////////////////
    public void httpPutJsonWithResponse() {
        */
/* Create object of CloseableHttpClient *//*

        String url       = "www.google.com.au";
        String partner_code = "TOPC";
        WechatPayment wechatPayment = new WechatPayment();
        String signString = wechatPayment.getSign();
        String nonce = wechatPayment.nonce;
        long unixTime = wechatPayment.unixTime;

        String order_id     = "11000053";
        String refund_id = "RF"+order_id+"_"+unixTime;
        url = "https://mpay.royalpay.com.au/api/v1.0/gateway/partners/"+partner_code+"/orders/"+order_id+"/refunds/"+refund_id+
                "?"+"valid_string="+partner_code+"&time="+unixTime+"&nonce_str="+nonce+"&sign="+signString;
        String json = "{\"fee\":1}"; //"{\"fee\":\"0.01\"}"; "{fee:0.01}";


        System.out.println("WeChat Sign     is:|"+signString);
        System.out.println("WeChat nonce    is:|"+nonce);
        System.out.println("WeChat unixTime is:|"+unixTime);
        //https://mpay.royalpay.com.au/api/v1.0/gateway/partners/{partner_code}/orders/{order_id}/refunds/{refund_id}

        String CONTENT_TYPE_TEXT_JSON = "text/json";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        */
/* Prepare put request *//*

        System.out.println("WeChat Refund Json:|"+json);
        System.out.println("WeChat PUT URL:|"+url);
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity jsonData = new StringEntity(json, "UTF-8");

        */
/* Body of request *//*

        httpPut.setEntity(jsonData);

        */
/* Response handler for after request execution *//*

        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                */
/* Get status code *//*

                int httpResponseCode = httpResponse.getStatusLine().getStatusCode();
                System.out.println("Response code: " + httpResponseCode);
                if (httpResponseCode >= 200 && httpResponseCode < 300) {
                    */
/* Convert response to String *//*

                    HttpEntity entity = httpResponse.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    return null;
                    */
/* throw new ClientProtocolException("Unexpected response status: " + httpResponseCode); *//*

                }
            }
        };

        try {
            */
/* Execute URL and attach after execution response handler *//*

            HttpResponse response2 = null;

            response2 = httpClient.execute(httpPut);
            HttpEntity entity2 = null;
            entity2 = response2.getEntity();
            String s2 = EntityUtils.toString(entity2, "UTF-8");
            System.out.println(s2);

            */
/* Print the response *//*

            //System.out.println("Response: " + strResponse);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}*/
