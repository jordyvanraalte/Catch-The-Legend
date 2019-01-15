package com.whisperict.catchthelegend.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.whisperict.catchthelegend.managers.SoundManager;

public class HelpDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!SoundManager.getInstance().getConstantPlayer().isPlaying()){
            SoundManager.getInstance().getConstantPlayer().start();
        }
    }
}
