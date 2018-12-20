package com.whisperict.catchthelegend.managers.apis.legend;

import android.graphics.Bitmap;

import com.whisperict.catchthelegend.entities.Legend;

import java.util.ArrayList;

public interface OnLegendApiResponseListener {
    void OnLegendReceive(Legend legend);
    void OnLegendsReceive(ArrayList<String> names);
    void OnLegendCountReceive(int count);
}
