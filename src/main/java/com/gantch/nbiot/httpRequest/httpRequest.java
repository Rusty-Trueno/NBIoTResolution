package com.gantch.nbiot.httpRequest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import okhttp3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
/**
 * Created by rongshuai on 2019/9/11
 */
public class httpRequest {
    static private Cookie ck;
    private String host = "10.108.218.64";
    private static String session;
    public static String id;
    public String deviceToken;
    private static final MediaType js = MediaType.parse("application/json; charset=utf-8");
    private static final Random random = new Random();
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();
    public String httpcreate(String devicename,String gatewayName,String type,String model){
        System.out.println("devicename:" + devicename);
        System.out.println("gatewayName:" + gatewayName);
        System.out.println("deviceType:" + type);
        System.out.println("model:" + model);
        return getRandomString(10);
    }
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    /**
     * 创建设备的post请求
     */
    public String httpcreate2(String devicename,String gatewayName,String type,String model) throws Exception{
        //请求体
        JSONObject obj = new JSONObject();
        obj.put("name",type+random.nextInt(4));
        obj.put("tenantId",2);
        obj.put("manufacture","Gantch");
        obj.put("deviceType",type);
        obj.put("model",model);
        obj.put("parentDeviceId","7b198c80-d34b-11e8-be05-112f1ebe9142");

        RequestBody bodyCreate = RequestBody.create(js,obj.toString());
        //创建一个Request Request是OkHttp中访问的请求，Builder是辅助类。Response即OKHttp中的响应
        Request requestCreate = new Request.Builder()
                .url("http://47.105.120.203:30080/api/v1/deviceaccess/device")
                .post(bodyCreate)
                .addHeader("Accept","application/json, text/plain, */*")
//                .addHeader("Accept","text/plain, */*, q=0.01")
                .addHeader("Connection","keep-alive")
                .addHeader("Content-Type","application/json;charset=UTF-8")
                //.addHeader("Cookie",session.toString())
                .build();
        //得到一个call对象
        Response response = mOkHttpClient.newCall(requestCreate).execute();
        if (response.isSuccessful()){
            String result = response.body().string();
            System.out.println(result);

            JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();//把deviceaccess返回来的savedDevice转化为json格式
            id = jsonObject.get("id").getAsString();//从被保存的设备中提取出设备的id
            System.out.println("id is :"+id);
            return id;//返回id
        }else{
            //httplogin();
            System.out.println(response.body().string());
            return null;
        }
    }
    /*
    查找令牌的get请求
     */

    public String httpfind(String id)throws Exception {//根据设备的id获取设备的token

        //创建一个Request Request是OkHttp中访问的请求，Builder是辅助类。Response即OkHttp中的响应。
        Request requestCreate = new Request.Builder()
                .url("http://47.105.120.203:30080/api/v1/deviceaccess/credentialbyid/" + id.toString())
                .get()
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Connection", "keep-alive")
                //.addHeader("Cookie", session.toString())
                .build();
        //得到一个call对象
        Response response = mOkHttpClient.newCall(requestCreate).execute();
        if (response.isSuccessful()) {

            String result = response.body().string();
//           Log.e("http", "find_response : " + result);

            JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();//将收到的消息转化成设备证书的json对象
            deviceToken = jsonObject.get("deviceToken").getAsString();
            System.out.println("find_token is :"+deviceToken);
            return deviceToken;//返回设备的token
        }
        return null;
    }
}
