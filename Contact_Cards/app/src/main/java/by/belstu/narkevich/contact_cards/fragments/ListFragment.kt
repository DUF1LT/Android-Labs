package by.belstu.narkevich.contact_cards.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import by.belstu.narkevich.contact_cards.R
import by.belstu.narkevich.contact_cards.adapters.ContactCardsAdapter
import by.belstu.narkevich.contact_cards.databinding.FragmentListBinding
import by.belstu.narkevich.contact_cards.models.ContactCard
import by.belstu.narkevich.contact_cards.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private lateinit var _viewModel: MainViewModel
    private lateinit var _adapter: ContactCardsAdapter

    var onAddClick: (() -> Unit)? = null
    var onEditClick: ((ContactCard) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_end)
        exitTransition = inflater.inflateTransition(R.transition.slide_end)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val recyclerView = _binding!!.list
        _adapter = ContactCardsAdapter(viewLifecycleOwner, _viewModel.getCards())

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = _adapter

        _binding!!.fabAdd.setOnClickListener {
            onAddClick?.invoke()
        }

        registerForContextMenu(recyclerView)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_delete -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Warning")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Delete") { dialog, id ->
                        _adapter.onItemDelete(item.groupId)
                        val card = _adapter.getItem(item.groupId)
                        CoroutineScope(Dispatchers.IO).launch {
                            _viewModel.delete(card)
                        }
                    }
                    .setNegativeButton("Cancel") { dialog, id -> }
                    .create()
                    .show()

                return true
            }
            R.id.item_edit -> {
                val card = _adapter.getItem(item.groupId)
                onEditClick?.invoke(card)

                return true
            }
            R.id.open_website -> {
                val card = _adapter.getItem(item.groupId)
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://${card.Website}"))
                startActivity(browserIntent)

                return true
            }
            R.id.call_phone -> {
                val card = _adapter.getItem(item.groupId)
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:${card.PhoneNumber}"))
                startActivity(intent)

                return true
            }
            else ->  super.onContextItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}