package by.belstu.narkevich.contact_cards.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactCardDAO {
    @Query("select * from contact_card")
    fun getAllContactCard() : LiveData<List<ContactCard>>
    @Query("select * from contact_card where id = :id limit 1")
    fun getContactCardById(id: Int) : LiveData<List<ContactCard>>
    @Query("select * from contact_card where name like :filter")
    fun getContactCardLike(filter: String) : LiveData<List<ContactCard>>
    @Insert
    fun insert(card: ContactCard)
    @Update
    fun update(card: ContactCard)
    @Delete
    fun delete(card: ContactCard)
}