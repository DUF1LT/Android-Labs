package by.belstu.narkevich.contact_cards.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.belstu.narkevich.contact_cards.models.ContactCard
import by.belstu.narkevich.contact_cards.models.ContactCardDAO


@Database(entities = [ContactCard::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val ContactCardsDao: ContactCardDAO

    companion object {
        @Volatile
        private var _instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return _instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "contact_cards"
                ).allowMainThreadQueries()
                    .build()

                _instance = instance
                instance
            }
        }
    }
}

