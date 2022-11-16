package by.belstu.narkevich.contact_cards.datasource

import android.app.Application
import by.belstu.narkevich.contact_cards.models.ContactCard
import by.belstu.narkevich.contact_cards.models.ContactCardDAO


class AppRepository(application: Application) {
    private val contactCardDAO: ContactCardDAO

    init {
        val database = AppDatabase.getInstance(application)
        contactCardDAO = database.ContactCardsDao
    }

    fun getCards() = contactCardDAO.getAllContactCard()
    fun getCardById(id: Int) = contactCardDAO.getContactCardById(id)
    fun getCardsFilter(filter: String) = contactCardDAO.getContactCardLike(filter)
    fun addCard(card: ContactCard) = contactCardDAO.insert(card)
    fun deleteCard(card: ContactCard) = contactCardDAO.delete(card)
    fun updateCard(card: ContactCard) = contactCardDAO.update(card)
}