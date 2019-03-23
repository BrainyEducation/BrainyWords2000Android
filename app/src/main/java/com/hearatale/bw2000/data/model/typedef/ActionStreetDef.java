package com.hearatale.bw2000.data.model.typedef;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ActionStreetDef.PLAY_SOUND,
        ActionStreetDef.OPEN_STORE,
        ActionStreetDef.OPEN_INTERIOR,
        ActionStreetDef.OTHER,
})
@Retention(RetentionPolicy.SOURCE)
public @interface ActionStreetDef {
    int PLAY_SOUND = 0;
    int OPEN_STORE = 1;
    int OPEN_INTERIOR = 2;
    int OTHER = 3;
}
