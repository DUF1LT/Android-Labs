package by.belstu.narkevich.contact_cards.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import by.belstu.narkevich.contact_cards.datasource.AppRepository
import by.belstu.narkevich.contact_cards.models.ContactCard

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val appRepository: AppRepository
    private lateinit var contactCards: LiveData<List<ContactCard>>

    init {
        appRepository = AppRepository(application)
    }

    fun getCards(): LiveData<List<ContactCard>> {
        contactCards = appRepository.getCards()

        return contactCards
    }

    suspend fun delete(card: ContactCard) = appRepository.deleteCard(card)
}