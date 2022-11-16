package by.belstu.narkevich.contact_cards.helpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentManagerService {
    companion object {
        fun openFragment(fragmentManager: FragmentManager, frameLayoutId: Int, fragment: Fragment, withAddToBackStack: Boolean = false) {
            fragmentManager.executePendingTransactions();
            fragmentManager.beginTransaction().apply {
                replace(frameLayoutId, fragment)
                if(withAddToBackStack) {
                    addToBackStack(null)
                }
                commit()
            }
        }

        fun openFragmentWithRemove(fragmentManager: FragmentManager, frameLayoutId: Int, fragment: Fragment, withAddToBackStack: Boolean = false) {
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