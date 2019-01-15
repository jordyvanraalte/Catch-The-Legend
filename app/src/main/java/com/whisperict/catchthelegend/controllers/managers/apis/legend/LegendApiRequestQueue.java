package com.whisperict.catchthelegend.controllers.managers.apis.legend;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class LegendApiRequestQueue {
    private static LegendApiRequestQueue instance;
    private RequestQueue requestQueue;
    private Context currentContext;

    private LegendApiRequestQueue(Context context){
        this.currentContext = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            Cache cache  = new DiskBasedCache(currentContext.getCacheDir(), 10*1024*1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
        }
        return requestQueue;
    }

    public static LegendApiRequestQueue getInstance(Context context){
        if(instance == null){
            instance = new LegendApiRequestQueue(context);
        }
        return instance;
    }
}
