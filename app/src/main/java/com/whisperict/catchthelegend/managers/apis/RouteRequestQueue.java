package com.whisperict.catchthelegend.managers.apis;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class RouteRequestQueue {
    private static RouteRequestQueue instance;
    private RequestQueue requestQueue;
    private Context currentContext;

    private RouteRequestQueue(Context context) {
        this.currentContext = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(currentContext.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
        }
        return requestQueue;
    }

    public static RouteRequestQueue getInstance(Context context){
        if (instance == null){
            instance = new RouteRequestQueue(context);
        }
        return instance;
    }
}
