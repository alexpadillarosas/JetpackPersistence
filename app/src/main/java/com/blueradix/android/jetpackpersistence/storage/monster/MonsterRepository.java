package com.blueradix.android.jetpackpersistence.storage.monster;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blueradix.android.jetpackpersistence.storage.MonsterRoomDatabase;
import com.blueradix.android.jetpackpersistence.storage.monster.dao.MonsterDao;
import com.blueradix.android.jetpackpersistence.storage.monster.entity.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

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
                Log.i("XYZ", "Monster is NOT null in Repository, " + monster.toString());
            }else{
                Log.i("XYZ", "Monster is null in Repository");
            }*/
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return monster;

    }

    /*
    public List<Monster> findAllNoLiveData(){

        final List<Monster>[] allNoLiveData =  (ArrayList<Monster>[]) new ArrayList[1];
        //ArrayList<Individual>[] group = (ArrayList<Individual>[]) new ArrayList[4];
        MonsterRoomDatabase.databaseWriteExecutor.execute( () -> {
            allNoLiveData[0] = monsterDao.findAllNoLiveData();
            if(allNoLiveData[0] != null) {
                for (Monster monster : allNoLiveData[0]) {
                    Log.i("XYZ", monster.toString());
                }
            }else{
                Log.i("XYZ", "NOTHING!!!");
            }
        });
        return allNoLiveData[0];

    }

    public List<Monster> findAllNoLiveDataNoExecutor(){
        List<Monster> allNoLiveData = monsterDao.findAllNoLiveData();
        return allNoLiveData;
    }
    */

}
