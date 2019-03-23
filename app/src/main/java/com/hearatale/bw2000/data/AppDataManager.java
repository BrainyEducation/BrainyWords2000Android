package com.hearatale.bw2000.data;

import com.hearatale.bw2000.Application;
import com.hearatale.bw2000.data.model.BaseResponseGetAllStudent;
import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.data.model.student.BaseResponseStudentLogin;
import com.hearatale.bw2000.data.model.teacher_login.BaseResponseTeacherLogin;
import com.hearatale.bw2000.data.model.typedef.InteriorViewDef;
import com.hearatale.bw2000.data.prefs.AppPreferencesHelper;
import com.hearatale.bw2000.data.prefs.PreferencesHelper;
import com.hearatale.bw2000.network.ApiService;
import com.hearatale.bw2000.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppDataManager implements DataManager {

    private static volatile AppDataManager Instance = null;
    private PreferencesHelper mPreferencesHelper;

    private List<InfoStreetView> mInfoStreetViewList;

    private volatile List<InfoStreetView> mInfoInteriorViewList;

    private Map<String, List<Item>> dataDisplayItem;

    private BaseResponseTeacherLogin mSessionTeacher;

    private BaseResponseStudentLogin baseResponseStudentLogin;

    private BaseResponseGetAllStudent baseResponseGetAllStudent;

    private boolean isStartGame = false;

    private boolean isFirstTimeOpenApp = true;

    public boolean isFirstTimeOpenApp() {
        return isFirstTimeOpenApp;
    }

    public void setFirstTimeOpenApp(boolean firstTimeOpenApp) {
        isFirstTimeOpenApp = firstTimeOpenApp;
    }

    public boolean isStartGame() {
        return isStartGame;
    }

    public void setStartGame(boolean studentLogin) {
        this.isStartGame = studentLogin;
    }

    public BaseResponseGetAllStudent getBaseResponseGetAllStudent() {
        return baseResponseGetAllStudent;
    }

    public void setBaseResponseGetAllStudent(BaseResponseGetAllStudent baseResponseGetAllStudent) {
        this.baseResponseGetAllStudent = baseResponseGetAllStudent;
    }

    private ApiService apiService;

    private AppDataManager() {
        mPreferencesHelper = new AppPreferencesHelper(Application.Context);
        mSessionTeacher = getSessionTeacherLogin();
        baseResponseStudentLogin = getSessionStudentLogin();
        apiService = RetrofitClient.getClient(Application.Context).create(ApiService.class);
    }

    public static AppDataManager getInstance() {
        AppDataManager localInstance = Instance;
        if (localInstance == null) {
            synchronized (AppDataManager.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new AppDataManager();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void remove(String name) {
        mPreferencesHelper.remove(name);
    }

    @Override
    public void clear() {
        mPreferencesHelper.clear();
    }

    @Override
    public List<InfoStreetView> getListInfoStreetView() {
        if (mInfoStreetViewList == null) {
            mInfoStreetViewList = mPreferencesHelper.getListInfoStreetView();
        }
        return mInfoStreetViewList;
    }

    @Override
    public List<InfoStreetView> getListInfoInteriorView() {
        if (mInfoInteriorViewList == null) {
            mInfoInteriorViewList = mPreferencesHelper.getListInfoInteriorView();
        }
        return mInfoInteriorViewList;
    }

    @Override
    public float getScore(String category) {
        return mPreferencesHelper.getScore(category);
    }

    @Override
    public void setScore(String category, float score) {
        mPreferencesHelper.setScore(category, score);
    }

    @Override
    public Map<String, List<Item>> getDisPlayItems() {
        if (dataDisplayItem == null) {
            return dataDisplayItem = mPreferencesHelper.getDisPlayItems();
        } else {
            dataDisplayItem.keySet();
            return dataDisplayItem;
        }
    }

    @Override
    public void setCurrentPraiseIndex(int index) {
        mPreferencesHelper.setCurrentPraiseIndex(index);
    }

    @Override
    public int getCurrentPraiseIndex() {
        return mPreferencesHelper.getCurrentPraiseIndex();
    }

    @Override
    public void setSessionTeacherLogin(BaseResponseTeacherLogin baseResponseTeacherLogin) {
        this.mSessionTeacher = baseResponseTeacherLogin;
        mPreferencesHelper.setSessionTeacherLogin(baseResponseTeacherLogin);
    }

    @Override
    public BaseResponseTeacherLogin getSessionTeacherLogin() {
        return mPreferencesHelper.getSessionTeacherLogin();
    }

    @Override
    public BaseResponseStudentLogin getSessionStudentLogin() {

        return mPreferencesHelper.getSessionStudentLogin();
    }

    @Override
    public void setSessionStudentLogin(BaseResponseStudentLogin baseResponseStudentLogin) {
        mPreferencesHelper.setSessionStudentLogin(baseResponseStudentLogin);
        this.baseResponseStudentLogin = baseResponseStudentLogin;
    }

    @Override
    public InfoStreetView getInfoInteriorView(@InteriorViewDef int interiorDef) {
        return getListInfoInteriorView().get(interiorDef);
    }

    @Override
    public List<Item> getDataDisplayItem(String category) {

        if (dataDisplayItem == null) {
            return getDisPlayItems().get(category);
        } else {
            return dataDisplayItem.get(category);
        }
    }

    @Override
    public List<String> getListCategory() {
        if (dataDisplayItem != null) {
            return new ArrayList<String>(dataDisplayItem.keySet());
        }
        return new ArrayList<>();
    }

    @Override
    public float getTotalScore() {
        float score = 0;
        List<String> listCategory = getListCategory();
        if (listCategory.size() > 0) {
            for (String category : listCategory) {
                score += getScore(category);
            }
            return score;
        }
        return score;
    }

    @Override
    public void studentLogout() {
        setSessionStudentLogin(null);
    }

    @Override
    public void teacherLogout() {
        setSessionTeacherLogin(null);
    }

    public BaseResponseTeacherLogin getSessionTeacher() {
        return mSessionTeacher;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
