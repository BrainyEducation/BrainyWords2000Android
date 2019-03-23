package com.hearatale.bw2000.data.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hearatale.bw2000.Application;
import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.data.model.student.BaseResponseStudentLogin;
import com.hearatale.bw2000.data.model.teacher_login.BaseResponseTeacherLogin;
import com.hearatale.bw2000.util.Config;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_FILE_NAME = "BrainyWords";

    private final SharedPreferences mPref;
    private final SharedPreferences.Editor mEditor;

    private final Gson mGson;

    @SuppressLint("CommitPrefEdits")
    public AppPreferencesHelper(Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
                .create();
    }

    @Override
    public void remove(String name) {
        mEditor.remove(name).apply();
    }

    @Override
    public void clear() {
        mEditor.clear().apply();
    }

    @Override
    public List<InfoStreetView> getListInfoStreetView() {
        try {
            String jsonLetters = "";
            InputStream inputStream = Application.Context.getAssets().open("DataText/data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonLetters = new String(buffer, "UTF-8");

            Type listType = new TypeToken<List<InfoStreetView>>() {
            }.getType();

            return mGson.fromJson(jsonLetters, listType);
        } catch (Exception e) {
            return new ArrayList<>();
        }


    }

    @Override
    public List<InfoStreetView> getListInfoInteriorView() {
        try {
            String jsonLetters = "";
            InputStream inputStream = Application.Context.getAssets().open("DataText/interior.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonLetters = new String(buffer, "UTF-8");

            Type listType = new TypeToken<List<InfoStreetView>>() {
            }.getType();

            return mGson.fromJson(jsonLetters, listType);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public float getScore(String category) {
        return mPref.getFloat(category, 0.0f);
    }

    @Override
    public void setScore(String category, float score) {
        mEditor.putFloat(category, score);
        mEditor.apply();
    }

    @Override
    public Map<String, List<Item>> getDisPlayItems() {
        try {
            String jsonLetters = "";
            InputStream inputStream = Application.Context.getAssets().open("DataText/displayitems.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonLetters = new String(buffer, "UTF-8");

            Type type = new TypeToken<Map<String, List<Item>>>() {
            }.getType();

            return mGson.fromJson(jsonLetters, type);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    @Override
    public void setCurrentPraiseIndex(int index) {
        mEditor.putInt(Config.CURENT_PRAISE_INDEX, index);
        mEditor.apply();
    }

    @Override
    public int getCurrentPraiseIndex() {
        return mPref.getInt(Config.CURENT_PRAISE_INDEX, 0);
    }

    @Override
    public void setSessionTeacherLogin(BaseResponseTeacherLogin baseResponseTeacherLogin) {
        if(baseResponseTeacherLogin == null)
        {
            mEditor.putString(Config.SESSION_TEACHER,"");
            mEditor.apply();
        }else {

            mEditor.putString(Config.SESSION_TEACHER, mGson.toJson(baseResponseTeacherLogin));
            mEditor.apply();
        }
    }

    @Override
    public BaseResponseTeacherLogin getSessionTeacherLogin() {
        String data = mPref.getString(Config.SESSION_TEACHER,"");
        try {
            return mGson.fromJson(data, BaseResponseTeacherLogin.class);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public BaseResponseStudentLogin getSessionStudentLogin() {
        String data = mPref.getString(Config.SESSION_STUDENT,"");
        try {
            return mGson.fromJson(data, BaseResponseStudentLogin.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setSessionStudentLogin(BaseResponseStudentLogin baseResponseStudentLogin) {
        if(baseResponseStudentLogin == null)
        {
            mEditor.putString(Config.SESSION_STUDENT,"");
            mEditor.apply();
        }else {

            mEditor.putString(Config.SESSION_STUDENT, mGson.toJson(baseResponseStudentLogin));
            mEditor.apply();
        }
    }
}
