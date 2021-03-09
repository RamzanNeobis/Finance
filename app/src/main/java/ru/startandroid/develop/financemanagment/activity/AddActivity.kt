package ru.startandroid.develop.financemanagment.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_add.*
import ru.startandroid.develop.financemanagment.R
import ru.startandroid.develop.financemanagment.fragments.ConsumptionFragment
import ru.startandroid.develop.financemanagment.fragments.IncomeFragment
import ru.startandroid.develop.financemanagment.fragments.TransferFragment

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(IncomeFragment(), "+Доход")
        adapter.addFragment(ConsumptionFragment(), "-Расход")
        adapter.addFragment(TransferFragment(), "Перевод")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }


    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }
}

fun AppCompatActivity.replaceFragment(fragment:Fragment){
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.transactionFragment,fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}

