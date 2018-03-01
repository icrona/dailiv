package com.dailiv.util.network;

import com.dailiv.BuildConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import rx.Single;

import static com.dailiv.util.IConstants.TIMEOUT;

/**
 * Created by aldo on 3/1/18.
 */

public final class NetworkHelper {

    private static final String HOST = BuildConfig.HOST;
    private static final int PORT = 80;
    private static final int INTERVAL_IN_MS = TIMEOUT * 1000;

    public static Single<Void> checkConnection() {

        return Single.fromCallable(NetworkHelper::isConnected)
                .map(aBoolean -> {
                    if(!aBoolean) {
                        throw new NetworkException("not connection to host");
                    }
                    return null;
                });
    }

    public static boolean isConnected() {

        boolean isConnected;

        final Socket socket = new Socket();

        try {
            socket.connect(new InetSocketAddress(HOST, PORT), INTERVAL_IN_MS);
            isConnected = socket.isConnected();
        }
        catch (IOException e) {
            isConnected = false;
        }
        finally {
            try{
                socket.close();
            }
            catch (IOException e){

            }
        }
        return isConnected;
    }

}
