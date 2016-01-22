package com.miaotech.health.ui.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miaotech.health.R;
import com.miaotech.health.ui.fragement.EssayFragment;
import com.miaotech.health.ui.fragement.FoodFragment;
import com.miaotech.health.ui.fragement.MineFragment;
import com.miaotech.health.utils.ExitApplication;
import com.miaotech.health.widget.MyToast;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * 负责控制下方按钮与页面切换
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private FoodFragment foodFragment=new FoodFragment();
    private EssayFragment essayFragment=new EssayFragment();
    private MineFragment mineFragment=new MineFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private Fragment mfrom = foodFragment;// 默认显示控制第一个默认的界面 为 第一个 为后面 交换界面初始化 参数

    private ImageView fragementfoodButton;// 食材按钮
    private ImageView fragementessayButton;//文章按钮
    private ImageView fragementmineButton;//我的按钮

    private LinearLayout fragementfoodly;// 食材按钮
    private LinearLayout fragementessayly;//文章按钮
    private LinearLayout fragementminely;//我的按钮

    private TextView fragementfoodtv;
    private TextView fragementessaytv;
    private TextView fragementminetv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExitApplication.getInstance().addActivity(this);// 添加到应用界面队列用于统一退出
        AnalyticsConfig.enableEncrypt(true);//友盟加密传输
        assignViews();
        addDefaultFragment();
    }
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        //super.onSaveInstanceState(outState);   //将这一行注释掉，阻止activity崩溃时候保存fragment的状态防止重叠现象
    }

    /**
     * 分配屏幕控件
     */
    private void assignViews() {

        fragementfoodtv=(TextView)findViewById(R.id.fragementfood_tv);
        fragementessaytv=(TextView)findViewById(R.id.fragementessay_tv);;
        fragementminetv=(TextView)findViewById(R.id.fragementmine_tv);;
        fragementfoodButton = (ImageView) findViewById(R.id.fragementfood_button);
        fragementessayButton = (ImageView) findViewById(R.id.fragementessay_button);
        fragementmineButton = (ImageView) findViewById(R.id.fragementmine_button);
        fragementfoodly = (LinearLayout) findViewById(R.id.fragementfood_ly);
        fragementessayly= (LinearLayout) findViewById(R.id.fragementessay_ly);
        fragementminely= (LinearLayout) findViewById(R.id.fragementmine_ly);
        fragementessayly.setOnClickListener(this);
        fragementminely.setOnClickListener(this);
        fragementfoodly.setOnClickListener(this);

    }
    /**
     * 添加默认显示的Fragment
     */
    public void addDefaultFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragContent, foodFragment);
        fragmentTransaction.commit();

    }
    /**
     * 屏幕底部按钮点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragementfood_ly:
                changeFoodMenu();
                switchContent(mfrom, foodFragment);
                mfrom = foodFragment;
                break;
            case R.id.fragementessay_ly:
                changeEssayMenu();
                switchContent(mfrom, essayFragment);
                mfrom = essayFragment;
                break;
            case R.id.fragementmine_ly:
                changeMineMenu();
                switchContent(mfrom, mineFragment);
                mfrom = mineFragment;
                break;
            default:
                break;

        }
    }

    /**
     * 控制从activity
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (!to.isAdded()) { // 先判断是否被add过
            fragmentTransaction.hide(from).add(R.id.fragContent, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            Log.i("noADD过===", "noADD过===");
        } else {
            fragmentTransaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            Log.i("ADD过===", "ADD过===");
        }
    }
    /**
     * 修改食物按钮样式
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void changeFoodMenu(){
       // Drawable drawable = getResources().getDrawable(R.mipmap.book_menu_normal);
        //drawable.setBounds(0, 0, drawable.getMinimumWidth(),
       //         drawable.getMinimumHeight()); // 设置边界
       // fragementessayButton.setCompoundDrawables(null, drawable, null, null);// 画在右边
       // fragementessayButton.setTextColor(getResources().getColor(R.color.menu_normal_color));
        fragementessayButton.setBackground(getResources().getDrawable(R.drawable.book_menu_normal));
        fragementfoodButton.setBackground(getResources().getDrawable(R.drawable.food_menu_pressed));
        fragementmineButton.setBackground(getResources().getDrawable(R.drawable.mine_menu_normal));
        fragementfoodtv.setTextColor(getResources().getColor(R.color.menu_pressed_color));
        fragementessaytv.setTextColor(getResources().getColor(R.color.menu_normal_color));
        fragementminetv.setTextColor(getResources().getColor(R.color.menu_normal_color));



    }
    /**
     * 修改知识按钮样式
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void changeEssayMenu(){
        fragementessayButton.setBackground(getResources().getDrawable(R.drawable.book_menu_pressed));
        fragementfoodButton.setBackground(getResources().getDrawable(R.drawable.food_menu_normal));
        fragementmineButton.setBackground(getResources().getDrawable(R.drawable.mine_menu_normal));
        fragementfoodtv.setTextColor(getResources().getColor(R.color.menu_normal_color));
        fragementessaytv.setTextColor(getResources().getColor(R.color.menu_pressed_color));
        fragementminetv.setTextColor(getResources().getColor(R.color.menu_normal_color));
    }
    /**
     * 修改我的按钮样式
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void changeMineMenu(){
        fragementessayButton.setBackground(getResources().getDrawable(R.drawable.book_menu_normal));
        fragementfoodButton.setBackground(getResources().getDrawable(R.drawable.food_menu_normal));
        fragementmineButton.setBackground(getResources().getDrawable(R.drawable.mine_menu_pressed));
        fragementfoodtv.setTextColor(getResources().getColor(R.color.menu_normal_color));
        fragementessaytv.setTextColor(getResources().getColor(R.color.menu_normal_color));
        fragementminetv.setTextColor(getResources().getColor(R.color.menu_pressed_color));
    }

    /**
     * 重写返回键
     */
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

           // showDialog();
            if((System.currentTimeMillis()-exitTime) > 2000){
                MyToast.showToastShort(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                ExitApplication.getInstance().exit();//退出
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //友盟统计------------------
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
