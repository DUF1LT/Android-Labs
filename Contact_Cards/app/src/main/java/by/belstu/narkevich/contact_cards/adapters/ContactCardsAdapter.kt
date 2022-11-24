package by.belstu.narkevich.contact_cards.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import by.belstu.narkevich.contact_cards.R
import by.belstu.narkevich.contact_cards.databinding.ListItemBinding
import by.belstu.narkevich.contact_cards.models.ContactCard
import by.belstu.narkevich.contact_cards.viewholders.CardViewHolder


class ContactCardsAdapter internal constructor(lifecycleOwner: LifecycleOwner, private var ContactCards: LiveData<List<ContactCard>>) :
    RecyclerView.Adapter<CardViewHolder>() {

    init {
        ContactCards.observe(lifecycleOwner) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: ListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item,
            parent,
            false)

        return CardViewHolder(binding)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val contactCard: ContactCard = ContactCards.value!![position]
        holder.bind(contactCard)

        holder.cardView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.card_appear)
    }

    override fun getItemCount() = ContactCards.value?.size ?: 0

    fun onItemDelete(position: Int) {
        notifyItemRemoved(position)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getItem(position: Int) = ContactCards.value!![position]
}