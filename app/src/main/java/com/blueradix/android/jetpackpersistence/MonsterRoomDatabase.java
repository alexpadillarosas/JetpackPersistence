package com.blueradix.android.jetpackpersistence;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.blueradix.android.jetpackpersistence.monster.Monster;
import com.blueradix.android.jetpackpersistence.monster.MonsterDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Monster.class}, version = 1, exportSchema = false)
public abstract class MonsterRoomDatabase extends RoomDatabase{

    /*  The database will expose the DAOs here. Declare one method per dao, the method will just return the dao  */
    //============= BEGIN DAO's methods section ===========
    // declare here as many DAO's as you app requires..
    public abstract MonsterDao monsterDao();
    // public abstract ...


    //============= END DAO's methods section ===========

    private static volatile MonsterRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    //This is a fixed thread pool we will use to run database operations asynchronously on a background thread
    //operations such as Insert, Delete, Update
    public static  final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //This method is what is called Singleton, this avoid creating more than 1 instance of the Database
    public static MonsterRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MonsterRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), MonsterRoomDatabase.class, "monster_database")
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        //onCreate will be call only the first time the database is created.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            populateInitialData(INSTANCE);
            Log.i("XYZ", "onCreate Called");
        }

        //onOpen will be called every time the database is opened
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.i("XYZ", "onOpen Called");
        }
    };

    /**
     * method used to set up initial data for us to play with it.
     * @param instance
     */
    private static void populateInitialData(MonsterRoomDatabase instance) {
        //we execute database operations in threads
        MonsterRoomDatabase.databaseWriteExecutor.execute( () -> {
            MonsterDao monsterDao = INSTANCE.monsterDao();
            monsterDao.insert(new Monster("Alex", "I'm an ugly mate", "", 5, 1, 4));
            monsterDao.insert(new Monster("Bogeyman", "BooOOOooOOO", "", 5, 2, 3));
            monsterDao.insert(new Monster("Bigfoot", "I run long distances ", "", 2, 3, 8));
        });

    }

}
