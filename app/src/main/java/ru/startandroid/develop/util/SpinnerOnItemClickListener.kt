package ru.startandroid.develop.util

import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class SpinnerOnItemClickListener(
    private val listener :((AdapterView<*>?,Int) -> Unit)? = null
) : AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        listener?.invoke(parent,position)
    }
}

class OnItemSelectListener(
    private val listener: ((Int) -> Unit)? = null
): AdapterView.OnItemClickListener  {

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        listener?.invoke(p2)
    }
}

class FragmentAdd {
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

}