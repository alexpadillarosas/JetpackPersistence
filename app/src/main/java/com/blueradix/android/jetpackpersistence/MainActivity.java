package com.blueradix.android.jetpackpersistence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.blueradix.android.jetpackpersistence.monster.MonsterFragment;
import com.blueradix.android.jetpackpersistence.monster.MonsterViewModel;

public class MainActivity extends AppCompatActivity {

    private MonsterViewModel monsterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MonsterFragment.newInstance())
                    .commitNow();
        }
    }
}