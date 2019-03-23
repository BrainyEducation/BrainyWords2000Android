package com.hearatale.bw2000.data.model.typedef;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ToolbarDef.NONE,
        ToolbarDef.MENU_BACK_HOME,
        ToolbarDef.BACK,
        ToolbarDef.BACK_HOME,
})

@Retention(RetentionPolicy.SOURCE)
public @interface ToolbarDef {
    int NONE = 0;
    int MENU_BACK_HOME = 1;
    int BACK = 2;
    int BACK_HOME = 3;
}
