package ru.startandroid.develop.financemanagment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faskn.lib.PieChart
import com.faskn.lib.buildChart
import kotlinx.android.synthetic.main.fragment_statistic.*
import ru.startandroid.develop.financemanagment.R


class StatisticFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_statistic, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = ArrayList<PieCharData>()

        date.add(PieCharData(1,"12e"))
        date.add(PieCharData(2,"1212423"))
        date.add(PieCharData(3,"wefwf"))
    }


}