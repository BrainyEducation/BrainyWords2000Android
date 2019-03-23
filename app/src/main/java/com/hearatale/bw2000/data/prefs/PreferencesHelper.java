package com.hearatale.bw2000.data.prefs;


import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.data.model.student.BaseResponseStudentLogin;
import com.hearatale.bw2000.data.model.teacher_login.BaseResponseTeacherLogin;

import java.util.List;
import java.util.Map;

public interface PreferencesHelper {

    void remove(String name);

    void clear();

    List<InfoStreetView> getListInfoStreetView();

    List<InfoStreetView> getListInfoInteriorView();

    float getScore(String category);

    void setScore(String category, float score);

    Map<String, List<Item>> getDisPlayItems();

    void setCurrentPraiseIndex(int index);


    int getCurrentPraiseIndex();

    void setSessionTeacherLogin(BaseResponseTeacherLogin baseResponseTeacherLogin);

    BaseResponseTeacherLogin getSessionTeacherLogin();

    BaseResponseStudentLogin getSessionStudentLogin();

    void setSessionStudentLogin(BaseResponseStudentLogin baseResponseStudentLogin);
}
