package com.blueradix.android.jetpackpersistence.monster;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.blueradix.android.jetpackpersistence.MonsterRepository;

import java.util.List;

/**
 * Note in this case we extend from AndroidViewModel which extends ViewModel
 * the reason: our repository needs a reference to the Application class and
 * ViewModel does not provide it.
 */
public class MonsterViewModel extends AndroidViewModel {

    /*  As we can see in the Persistence Library Diagram, the view model will accept data requests
     *   from Fragments / Activities (UI Controllers)
     *   The Model's purpose is to obtain / store this information from the database in order to provide it to
     *   the UI Controllers
     */

    //We need to talk to the repository
    private MonsterRepository monsterRepository;
    //our LiveData for the monsters we will receive from the Repository (database)
    private LiveData<List<Monster>> allMonsters;


    //we need to create a constructor for our ViewModel, since now we will call the repository
    public MonsterViewModel(@NonNull Application application) {
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