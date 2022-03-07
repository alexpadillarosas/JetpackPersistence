package com.blueradix.android.jetpackpersistence;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.blueradix.android.jetpackpersistence.monster.MonsterDao;
import com.blueradix.android.jetpackpersistence.monster.Monster;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MonsterRepository {

    private MonsterRoomDatabase db;
    //My repository needs to provide the DAOs, so here list all DAO's requested by the application.
    private MonsterDao monsterDao;
    /*
      The LiveData is done in order to use this list in a Recycler View.
        Therefore any changes in the database will be immediately reflected in the recyclerview */
    private LiveData<List<Monster>> allMonsters;
    private Monster monster;

    public MonsterRepository(Application application){

        /*  We use the application passed as argument and get the database  */
        db = MonsterRoomDatabase.getDatabase(application);
        /*  then we request a dao from the database */
        monsterDao = db.monsterDao();
        /*  We retrieve all monsters registered in the database, but wrapped in a liveData object to listen for changes in realtime */
        allMonsters = monsterDao.findAll();
    }

    public void insert(Monster monster){
        //we have to execute these operations not in the main thread since it will freeze our app. (It won't be allowed if you try)
        MonsterRoomDatabase.databaseWriteExecutor.execute( () -> {
            monsterDao.insert(monster);
        });
    }

    public void update(Monster monster){
        MonsterRoomDatabase.databaseWriteExecutor.execute( () -> {
            monsterDao.update(monster);
        });
    }

    public void delete(Monster monster){
        MonsterRoomDatabase.databaseWriteExecutor.execute( () -> {
            monsterDao.delete(monster);
        });
    }

    public LiveData<List<Monster>> getAllMonsters() {
        //In this case Room takes care of the LiveData execution, so we won't have to the databaseWriteExecutor asn in the previous methods.
        return allMonsters;
    }

    public Monster findById(int id){

        Callable c = () -> {   // Lambda Expression
            Monster monster = monsterDao.findById(id);
            return monster;
        };
        Future<Monster> future = MonsterRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            monster = future.get();
            /*
            if(monster != null) {
                Log.i("XYZ", "Monster is NOT null in MonsterRepository, " + monster.toString());
            }else{
                Log.i("XYZ", "Monster is null in MonsterRepository");
            }*/
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return monster;

    }

}