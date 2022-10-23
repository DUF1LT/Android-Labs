package by.belstu.narkevich.movies.helpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.belstu.narkevich.movies.R

class FragmentManagerService {
    companion object {
        fun openMovieFragment(fragmentManager: FragmentManager, frameLayoutId: Int, fragment: Fragment, withAddToBackStack: Boolean = false) {
            fragmentManager.executePendingTransactions();
            fragmentManager.beginTransaction().apply {
                replace(frameLayoutId, fragment)
                if(withAddToBackStack) {
                    addToBackStack(null)
                }
                commit()
            }
        }

        fun openMovieFragmentWithRemove(fragmentManager: FragmentManager, frameLayoutId: Int, fragment: Fragment, withAddToBackStack: Boolean = false) {
            fragmentManager.beginTransaction().remove(fragment).commit()
            fragmentManager.executePendingTransactions();
            fragmentManager.beginTransaction().apply {
                replace(frameLayoutId, fragment)
                if(withAddToBackStack) {
                    addToBackStack(null)
                }
                commit()
            }
        }
    }
}