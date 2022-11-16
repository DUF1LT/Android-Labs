package by.belstu.narkevich.contact_cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import by.belstu.narkevich.contact_cards.databinding.ActivityMainBinding
import by.belstu.narkevich.contact_cards.fragments.AddEditFragment
import by.belstu.narkevich.contact_cards.fragments.ListFragment
import by.belstu.narkevich.contact_cards.helpers.FragmentManagerService
import by.belstu.narkevich.contact_cards.viewmodels.CardViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityMainBinding
    private lateinit var _viewModel: CardViewModel

    private lateinit var _listFragment: ListFragment
    private lateinit var _addEditFragment: AddEditFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setSupportActionBar(_binding.toolbarLayout.toolbar)

        _viewModel = ViewModelProvider(this)[CardViewModel::class.java]

        registerListFragment()
    }

    private fun registerListFragment() {
        _listFragment = ListFragment()

        _listFragment.onAddClick = {
            _addEditFragment = AddEditFragment()
            _viewModel.contactCard.value = null

            _addEditFragment.onMovieAddOrEdit = {
                FragmentManagerService.openFragment(supportFragmentManager, R.id.frameLayout, _listFragment)
            }

            FragmentManagerService.openFragment(supportFragmentManager, R.id.frameLayout, _addEditFragment, true)
        }

        _listFragment.onEditClick = {
            _addEditFragment = AddEditFragment()
            _viewModel.contactCard.value = it
            _viewModel.isEditMode.set(true)

            _addEditFragment.onMovieAddOrEdit = {
                FragmentManagerService.openFragment(supportFragmentManager, R.id.frameLayout, _listFragment)
            }

            FragmentManagerService.openFragment(supportFragmentManager, R.id.frameLayout, _addEditFragment, true)
        }

        FragmentManagerService.openFragment(supportFragmentManager, R.id.frameLayout, _listFragment)
    }
}