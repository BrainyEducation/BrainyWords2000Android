package com.hearatale.bw2000.data.model.typedef;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        InteriorSpecialDef.NONE,
        InteriorSpecialDef.INTERIOR_ZOO,
        InteriorSpecialDef.INTERIOR_PARK,
        InteriorSpecialDef.INTERIOR_BUILDING7A,
        InteriorSpecialDef.INTERIOR_PEDIATRICIAN,
        InteriorSpecialDef.INTERIOR_BUILDING11A,
        InteriorSpecialDef.INTERIOR_SCHOOL_PLAYGROUND,

})
@Retention(RetentionPolicy.SOURCE)
public @interface InteriorSpecialDef {
    int NONE = -1;
    int INTERIOR_ZOO = 0;
    int INTERIOR_PARK = 1;
    int INTERIOR_BUILDING7A = 2;
    int INTERIOR_PEDIATRICIAN = 3;
    int INTERIOR_BUILDING11A = 4;
    int INTERIOR_SCHOOL_PLAYGROUND = 5;
}
