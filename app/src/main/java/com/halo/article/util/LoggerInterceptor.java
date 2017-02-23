package com.halo.article.util;

import android.text.TextUtils;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by zx on 2017/2/8.
 * Description: 打印日志
 */

public class LoggerInterceptor implements Interceptor {
    public static final String TAG = LoggerInterceptor.class.getSimpleName();
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);

        return logForResponse(response);
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            Log.d(tag, "---------------------request log start---------------------");
            Log.d(tag, "method : " + request.method());
            Log.d(tag, "url : " + url);
            if (headers != null && headers.size() > 0) {
                Log.d(tag, "headers : \n");
                Log.d(tag, headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.d(tag, "contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.d(tag, "content : " + bodyToString(request));
                    } else {
                        Log.d(tag, "content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(e);
        } finally {
            Log.d(tag, "---------------------request log end-----------------------");
        }
    }

    private Response logForResponse(Response response) {
        try {
            Log.d(tag, "---------------------response log start---------------------");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.d(tag, "url : " + clone.request().url());
            Log.d(tag, "code : " + clone.code());
            Log.d(tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message())) Log.e(tag, "message : " + clone.message());

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        Log.d(tag, "contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            Log.d(tag, "content : " + resp);
                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            Log.d(tag, "content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(e);
        } finally {
            Log.d(tag, "---------------------response log end-----------------------");
        }

        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.toString().equals("application/x-www-form-urlencoded") ||
                    mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")) //
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}