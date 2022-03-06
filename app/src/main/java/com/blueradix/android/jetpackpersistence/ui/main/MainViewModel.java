package com.blueradix.android.jetpackpersistence.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.MutableLiveData;

import com.blueradix.android.jetpackpersistence.storage.monster.MonsterRepository;
import com.blueradix.android.jetpackpersistence.storage.monster.entity.Monster;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    /*  As we can see in the Persistence Library Diagram, the view model will accept data requests
     *   from Fragments / Activities (UI Controllers)
     *   The Model's purpose is to grab this information from the database in order to provide it to
     *   the UI Controllers
     */

    //We need to talk to the repository
    private MonsterRepository monsterRepository;
    //our LiveData for the monsters we will receive from the Repository (database)
    private LiveData<List<Monster>> allMonsters;


    //we need to create a constructor for our ViewModel, since now we will call the repository
    public MainViewModel(@NonNull Application application) {
        super(application);
        //create a repository instance
        monsterRepository = new MonsterRepository(application);
        //ask the repository for the monsters data.
        allMonsters = monsterRepository.getAllMonsters();
    }

    public void insert(Monster monster){
        monsterRepository.insert(monster);
    }

    public void delete(Monster monster){
        monsterRepository.delete(monster);
    }

    public void update(Monster monster){
        monsterRepository.update(monster);
    }

    public LiveData<List<Monster>> getAllMonsters(){
        return allMonsters;
    }

    public Monster findById(int id){
        return monsterRepository.findById(id);
    }

}