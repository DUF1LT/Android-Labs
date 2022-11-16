package by.belstu.narkevich.contact_cards.viewholders

import android.view.ContextMenu
import android.view.View
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import by.belstu.narkevich.contact_cards.BR
import by.belstu.narkevich.contact_cards.R
import by.belstu.narkevich.contact_cards.databinding.ListItemBinding
import by.belstu.narkevich.contact_cards.helpers.ImageService
import by.belstu.narkevich.contact_cards.models.ContactCard

class CardViewHolder constructor(private var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
    lateinit var contactCard: ContactCard

    fun bind(contactCard: ContactCard) {
        binding.setVariable(BR.contactCard, contactCard)
        ImageService.loadImageFromStorage(contactCard.Image, binding.logoImage)
        binding.card.setOnCreateContextMenuListener(this);
        this.contactCard = contactCard
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu?.setHeaderTitle(R.string.cardOptions)
        menu?.add(adapterPosition, R.id.item_edit, 0, R.string.edit)
        menu?.add(adapterPosition, R.id.call_phone, 0, R.string.callTheNumber)
        menu?.add(adapterPosition, R.id.open_website, 0, R.string.openWebsite)
        menu?.add(adapterPosition, R.id.item_delete, 1, R.string.delete)
    }
}