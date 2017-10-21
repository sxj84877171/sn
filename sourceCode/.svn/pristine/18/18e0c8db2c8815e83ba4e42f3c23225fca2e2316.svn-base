package com.sunvote.txpad.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sunvote.txpad.Constants;
import com.sunvote.util.LogUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class Api {

    public Retrofit retrofit;
    public ApiService service;

    public ApiService getService() {
        return service;
    }

    public void setService(ApiService service) {
        this.service = service;
    }

    private String url ;

    /**
     * 拦截器1
     * 如果后台服务器需要用到token，则每次请求自动对token的添加验证
     * 自动添加请求数据的格式，所有请求URL连接输出
     */
    Interceptor mInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            LogUtil.i("URL","url=="+chain.request().url());
            return chain.proceed(chain.request().newBuilder()
//                    .addHeader("Token", "Token")
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .build());
        }
    };

    private Api(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
               /* .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)*/
                .addInterceptor(mInterceptor)
//                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
//                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

        //initURL();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();
        service = retrofit.create(ApiService.class);
    }

    /**
     *     在访问HttpMethods时创建单例
     */
    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }

    /**
     *     获取单例
     */
    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 网络缓存
     */
    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            return chain.proceed(request);
        }
    }
}
