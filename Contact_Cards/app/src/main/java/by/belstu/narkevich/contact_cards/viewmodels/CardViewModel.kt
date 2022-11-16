package by.belstu.narkevich.contact_cards.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import by.belstu.narkevich.contact_cards.datasource.AppRepository
import by.belstu.narkevich.contact_cards.models.ContactCard

class CardViewModel(application: Application): AndroidViewModel(application) {
    private val appRepository: AppRepository
    var isEditMode = ObservableField(false)

    val contactCard = MutableLiveData<ContactCard>()
    val name = ObservableField<String>()
    val phoneNumber = ObservableField<String>()
    val address = ObservableField<String>()
    val website = ObservableField<String>()
    val image = ObservableField<String>()

    init {
        appRepository = AppRepository(application)
    }

    fun bindObservables() {
        name.set(contactCard.value?.Name)
        address.set(contactCard.value?.Address)
        phoneNumber.set(contactCard.value?.PhoneNumber)
        website.set(contactCard.value?.Website)
        image.set(contactCard.value?.Image)
    }

    suspend fun add(card: ContactCard) = appRepository.addCard(card)

    suspend fun update(card: ContactCard) = appRepository.updateCard(card)
}