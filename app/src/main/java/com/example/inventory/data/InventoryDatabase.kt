package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with singleton Instance object.
 */

// version - increase version number whenever database table schema changes.
// exportSchema - set as false as to not keep schema version history backup
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    // allows access to methods to create or get database and use class name as
    // qualifier
    companion object {
        // Instance - keeps reference to database, when one has been created.
        // This helps maintain single database instance opened at given time,
        // which is expensive resource to create and maintain.
        // volatile - value never cached, and all reads and writes are to and
        // from main memory. These features help ensure Instance value is always
        // up to date and is same for all execution threads. It means that
        // changes made by one thread to Instance are immediately visible to all
        // other threads.
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new
            // database instance.
            // synchronized block means that only one thread of execution at
            // time can enter this block of code, which makes sure database only
            // gets initialized once.
            return Instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    /**
                     * Setting option in app's database builder means that Room
                     * permanently deletes all data from database tables when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    // required migration strategy
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
