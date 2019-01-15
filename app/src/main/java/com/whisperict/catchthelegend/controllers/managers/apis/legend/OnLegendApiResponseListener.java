package com.whisperict.catchthelegend.controllers.managers.apis.legend;

import com.whisperict.catchthelegend.model.entities.Legend;

import java.util.ArrayList;

public interface OnLegendApiResponseListener {
    void OnLegendReceive(Legend legend);
    void OnLegendsReceive(ArrayList<String> names);
    void OnLegendCountReceive(int count);
    void OnRandomLegendReceive(Legend legend);
}
