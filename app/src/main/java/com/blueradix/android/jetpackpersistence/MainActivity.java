package com.blueradix.android.jetpackpersistence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.blueradix.android.jetpackpersistence.storage.MonsterRoomDatabase;
import com.blueradix.android.jetpackpersistence.storage.monster.MonsterRepository;
import com.blueradix.android.jetpackpersistence.storage.monster.entity.Monster;
import com.blueradix.android.jetpackpersistence.ui.main.MainFragment;
import com.blueradix.android.jetpackpersistence.ui.main.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

//        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);








//        MonsterRepository monsterRepository = new MonsterRepository(this.getApplication());
//        Log.i("XYZ", monsterRepository.toString());


/*

        Monster monster1 = new Monster();
        monster1.setName("Voodoo");
        monster1.setDescription("Too shy to reveal myself to the world");
        monster1.setImage("");
        monster1.setScariness(1);
        monster1.setStars(0);
        monster1.setVotes(0);

        monsterRepository.insert(monster1);

*/
/*
        LiveData<List<Monster>> allMonsters = monsterRepository.getAllMonsters();

        List<Monster> monsters = allMonsters.getValue();
*/
//        Log.i("XYZ", "total: " + monsters.size());

//        allMonsters.getValue().stream().forEach( e ->{
//            Log.i("XYZ", e.toString());
//        });




//        List<Monster> allMonstersNoLiveData = monsterRepository.findAllNoLiveData();


//        if(allMonstersNoLiveData != null) {
//            for (Monster monster : allMonstersNoLiveData) {
//                Log.i("XYZ", monster.toString());
//            }
//        }else{
//            Log.i("XYZ", "NOTHING!!!");
//        }

//        Monster monster = monsterRepository.findById(1);
//        Log.i("XYZ", monster.toString());

//        allMonstersNoLiveData.stream().forEach( e ->{
//            Log.i("XYZ", e.toString());
//        });
//        Log.i("XYZ", "Yes I have finnn");





    }
}