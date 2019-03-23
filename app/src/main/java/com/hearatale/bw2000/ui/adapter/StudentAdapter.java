package com.hearatale.bw2000.ui.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hearatale.bw2000.Application;
import com.hearatale.bw2000.R;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.data.model.student.Student;
import com.hearatale.bw2000.util.glide.GlideApp;

import java.util.List;

public class StudentAdapter extends BaseQuickAdapter<Student, BaseViewHolder> {


    public StudentAdapter(@Nullable List<Student> data) {
        super(R.layout.item_student, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Student item) {
        helper.setText(R.id.text_name_student,"Student "+item.getStudentId());
    }

}
