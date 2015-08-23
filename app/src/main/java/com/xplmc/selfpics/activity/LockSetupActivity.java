package com.xplmc.selfpics.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.xplmc.selfpics.R;
import com.xplmc.selfpics.component.LockPatternView;

import java.util.List;

public class LockSetupActivity extends Activity implements LockPatternView.OnPatternListener{

    private LockPatternView lockPatternView = null;

    private static final String GESTER_LOCK_KEY = "com.xiaoping.meier.activity.GesterLockKey";

    private enum LockStatus{
        FirstSetLock,//第一次输入锁
        SecondSetLock,//第二次输入锁
        CheckLock//校验锁
    }

    private LockStatus lockStatus = null;

    private String firstGesterLock = null;

    private String savedGesterLock = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_setup);

        lockPatternView = (LockPatternView)findViewById(R.id.lpvLockSetup);
        lockPatternView.setOnPatternListener(this);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        String gesterLock = preferences.getString(GESTER_LOCK_KEY, null);
        if(gesterLock == null){
            lockStatus = LockStatus.FirstSetLock;
        }else{
            lockStatus = LockStatus.CheckLock;
            savedGesterLock = gesterLock;
        }
    }

    /**
     * 开始时调用，在第一次调用Added方法之后
     */
    @Override
    public void onPatternStart() {
        Log.i("lockSetupActivity", "onPatternStart");

    }

    @Override
    public void onPatternCleared() {
        Log.i("lockSetupActivity", "onPatternCleared");
    }

    /**
     * 每次新增一个数字时调用
     */
    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {
        // TODO Auto-generated method stub
        Log.i("lockSetupActivity", "onPatternCellAdded:" + LockPatternView.patternToString(pattern));

    }

    /**
     * 手势完成后调用
     */
    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
            Toast.makeText(this,
                    R.string.lockpattern_recording_incorrect_too_short,
                    Toast.LENGTH_LONG).show();
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            return;
        }
        switch (lockStatus) {
            case FirstSetLock:
                firstGesterLock = LockPatternView.patternToString(pattern);
                lockStatus = LockStatus.SecondSetLock;
                lockPatternView.clearPattern();
                break;
            case SecondSetLock:
                //第二次手势，必须与第一次的手势一样
                if(firstGesterLock.equals(LockPatternView.patternToString(pattern))){
                    //保存手势密码
                    SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
                    preferences.edit().putString(GESTER_LOCK_KEY,firstGesterLock).commit();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                }else{
                    //两次输入的手势不一致，需要重新输入
                    Toast.makeText(this, "两次输入的密码不一致，需要重新输入！", Toast.LENGTH_SHORT).show();
                    lockStatus = LockStatus.FirstSetLock;
                    firstGesterLock = null;
                    lockPatternView.clearPattern();
                }
                break;
            case CheckLock:
                //必须与保存的手势一致
                if(savedGesterLock.equals(LockPatternView.patternToString(pattern))){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                }else{
                    lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    Toast.makeText(this, "密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, "异常逻辑！" + lockStatus, Toast.LENGTH_SHORT).show();
                this.finish();
                break;
        }
    }

}
