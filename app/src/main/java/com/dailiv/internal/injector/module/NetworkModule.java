package com.dailiv.internal.injector.module;

import com.dailiv.BuildConfig;
import com.dailiv.internal.data.remote.IApi;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.dailiv.util.IConstants.TIMEOUT;

/**
 * Created by aldo on 3/1/18.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    @Named("public")
    public IApi providePublicApi() {

        return new Retrofit.Builder().client(getOkHttpClient(getInterceptor()))
                .baseUrl(BuildConfig.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(getObjectMapper()))
                .build()
                .create(IApi.class);

    }

    @Provides
    @Singleton
    @Named("common")
    public IApi provideCommonApi() {

        return new Retrofit.Builder().client(getOkHttpClient(getInterceptorWithAuthorization()))
                .baseUrl(BuildConfig.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(getObjectMapper()))
                .build()
                .create(IApi.class);

    }

    private ObjectMapper getObjectMapper() {

        final ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        return objectMapper;
    }

    private OkHttpClient getOkHttpClient(Interceptor interceptor) {

        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        httpLoggingInterceptor.setLevel(BuildConfig.LOG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    private Interceptor getInterceptor() {

        return chain -> {
            final Request request = chain.request();
            final Request.Builder builder = request.newBuilder()
                    .addHeader("Accept", "application/json");

            return chain.proceed(builder.build());
        };
    }

    private Interceptor getInterceptorWithAuthorization() {

        return chain -> {
            final Request request = chain.request();
            final Request.Builder builder = request.newBuilder()
                    .addHeader("Accept", "application/json")
                    //todo
                    .addHeader("Authorization", "test");

            return chain.proceed(builder.build());
        };

    }

}
