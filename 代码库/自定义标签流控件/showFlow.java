package com.miaotech.health.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.miaotech.health.model.PubTags;

import java.util.List;

/**
 * Created by liang on 2015/12/5 0005.
 */
public class showFlow {
    /**
     * 设置公共标签
     */
    public static void showFlow(Context context ,String[] mVals, FlowLayout mFlowLayout, final List<Integer> signlist,
                          int layout, final int tvcolor, final int tvcolord, final int tvbgcolor, final int tvbgcolord) {

        LayoutInflater mInflater = LayoutInflater.from(context);
        for (int i = 0; i < mVals.length; i++) {
            final TextView tv = (TextView) mInflater.inflate(layout,
                    mFlowLayout, false);
            tv.setText(mVals[i]);

            //String mtagkey=mVals[i];
          //  final int mtag= PubTags.getTagmap().get(mtagkey);

            tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String mtagkey=tv.getText().toString();
                    Integer mtag= PubTags.getTagmap().get(mtagkey);
                    if (signlist.contains(mtag)) {//检查已选list中有没有当前点击标签

                        signlist.remove(mtag);
                        tv.setTextColor(tvcolor);
                        tv.setBackgroundResource(tvbgcolor);
                        System.out.println("tag====================move" + signlist + "");
                    } else {

                        signlist.add(mtag);
                        tv.setTextColor(tvcolord);
                        tv.setBackgroundResource(tvbgcolord);
                        System.out.println("tag====================add" + signlist + "");
                    }

/*
                    if (signlist.contains(tv.getText().toString())) {//检查已选list中有没有当前点击标签

                        signlist.remove(tv.getText().toString());
                        tv.setTextColor(tvcolor);
                        tv.setBackgroundResource(tvbgcolor);

                    } else {

                        signlist.add(tv.getText().toString());
                        tv.setTextColor(tvcolord);
                        tv.setBackgroundResource(tvbgcolord);

                    }*/
                }
            });

            mFlowLayout.addView(tv);
        }

    }

    /**
     *修改个人资料中显示标签方法，比公共设置标签方法多了后台传入数据时默认显示后台传入的数组为选中状态
     */
    public static void showFlow2(Context context ,String[] mVals, FlowLayout mFlowLayout, final List<String> signlist,
                                int layout, final int tvcolor, final int tvcolord, final int tvbgcolor, final int tvbgcolord) {

        LayoutInflater mInflater = LayoutInflater.from(context);
        for (int i = 0; i < mVals.length; i++) {
            final TextView tv = (TextView) mInflater.inflate(layout,
                    mFlowLayout, false);
            tv.setText(mVals[i]);
            if (signlist.contains(tv.getText().toString())) {//检查已选list中有没有当前点击标签

                tv.setTextColor(tvcolord);
                tv.setBackgroundResource(tvbgcolord);
            }
            tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (signlist.contains(tv.getText().toString())) {//检查已选list中有没有当前点击标签

                        signlist.remove(tv.getText().toString());
                        tv.setTextColor(tvcolor);
                        tv.setBackgroundResource(tvbgcolor);

                    } else {

                        signlist.add(tv.getText().toString());
                        tv.setTextColor(tvcolord);
                        tv.setBackgroundResource(tvbgcolord);

                    }
                }
            });
            mFlowLayout.addView(tv);
        }

    }
    public static void showFlow3(Context context ,String[] mVals, FlowLayout mFlowLayout, final List<String> signlist,
                                 int layout, final int tvcolor, final int tvcolord, final int tvbgcolor, final int tvbgcolord) {

        LayoutInflater mInflater = LayoutInflater.from(context);
        for (int i = 0; i < mVals.length; i++) {
            final TextView tv = (TextView) mInflater.inflate(layout,
                    mFlowLayout, false);
            tv.setText(mVals[i]);
            tv.setTextColor(tvcolord);
            tv.setBackgroundResource(tvbgcolord);
            mFlowLayout.addView(tv);
        }

    }

}