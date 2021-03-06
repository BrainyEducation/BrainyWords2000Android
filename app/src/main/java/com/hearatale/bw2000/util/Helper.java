package com.hearatale.bw2000.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaMetadataRetriever;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;

import com.hearatale.bw2000.Application;
import com.hearatale.bw2000.data.model.student.PayloadStudent;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {

    public static PayloadStudent createPayloadCreateStudent(String name, String studentId, String teacherId)
    {
        PayloadStudent payload = new PayloadStudent();
        payload.setName(name);
        payload.setTeacher(teacherId);
        payload.setStudentId(studentId);
        payload.setDelete("false");
        return payload;
    }

    public static Map<String,String> createHeader(String token)
    {
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer "+token);
        return header;
    }

    public static Map<String,String> createPayloadStudent(String teacher_id, String student_id)
    {
        Map<String,String> payload = new HashMap<>();
        payload.put("id",teacher_id+student_id);
        return payload;
    }

    public static <T> List<T> safeSubList(List<T> list, int fromIndex, int toIndex) {
        int size = list.size();
        if (fromIndex >= size || toIndex <= 0 || fromIndex >= toIndex) {
            return Collections.emptyList();
        }

        fromIndex = Math.max(0, fromIndex);
        toIndex = Math.min(size, toIndex);

        return list.subList(fromIndex, toIndex);
    }


    /**
     * Detect soft navigation bar availability in android device
     * Return navigation bar height
     *
     * @param context
     * @return
     */
    public static int getHeightSoftNavigationBar(Context context) {
        Resources resources = context.getResources();
        boolean hasSoftKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        if (hasSoftKey) {
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }


    public static int getStatusBarHeight(Context context) {

        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static boolean isListValid(List<?> paramList) {
        return (paramList != null) && (paramList.size() > 0);
    }


    public static InputFilter filterSpaceFirstLine() {
        /* To restrict Space Bar in Keyboard */
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (source != null && dstart == 0 && source.equals(" ")) {
                    return "";
                }
                return null;
            }

        };
        return filter;
    }

    public static int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Application.Context.getResources().getDisplayMetrics()));
    }


    public static float getDurationAudioFromAssets(Context context, String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            AssetFileDescriptor d = context.getAssets().openFd(path);
            mmr.setDataSource(d.getFileDescriptor(), d.getStartOffset(), d.getLength());
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return (float) (Integer.parseInt(durationStr)) / 1000;
        } catch (IOException | NumberFormatException e) {
            return 0.0f;
        }
    }

    public static int getIntDurationAudioFromAssets(Context context, String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            AssetFileDescriptor d = context.getAssets().openFd(path);
            mmr.setDataSource(d.getFileDescriptor(), d.getStartOffset(), d.getLength());
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return (Integer.parseInt(durationStr));
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }


    public static int getRelativeLeft(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }

    public static int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }


}
