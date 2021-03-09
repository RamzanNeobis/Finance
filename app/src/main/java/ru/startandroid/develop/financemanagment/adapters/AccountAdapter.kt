package ru.startandroid.develop.financemanagment.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_accounts.view.*
import kotlinx.android.synthetic.main.item_transaction.view.*
import ru.startandroid.develop.financemanagment.R
import ru.startandroid.develop.financemanagment.models.Account
import ru.startandroid.develop.financemanagment.models.Project

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    private val accountList = arrayListOf<Account>()

    fun setData(items: List<Account>) {
        this.accountList.clear()
        this.accountList.addAll(items)
        notifyDataSetChanged()
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_accounts, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: AccountAdapter.ViewHolder, position: Int) {
        holder.bindItems(accountList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return accountList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val urlProject =
            "https://thumbs.dreamstime.com/b/%D0%B7%D0%BD%D0%B0%D1%87%D0%BE%D0%BA-%D0%B8%D0%BB%D0%B8-%D0%BB%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF-%D0%B2%D0%B0%D0%BB%D1%8E%D1%82%D1%8B-%D0%B5%D0%B2%D1%80%D0%BE-%D0%BD%D0%B0-%D0%B1%D0%B0%D0%BD%D0%BA%D0%BD%D0%BE%D1%82%D0%B5-%D1%81%D1%87%D0%B5%D1%82%D0%B5-110056325.jpg"

        @SuppressLint("SetTextI18n")
        fun bindItems(account: Account) {
            itemView.nameAccounts.text = "Имя: ${account.name}"
            itemView.accountNumber.text = "№: ${account.code.toString()}"
            itemView.accountAmount.text = "На счету: ${account.sum.toString()}cом"
//            val nameAccount = itemView.findViewById(R.id.nameAccounts) as TextView
//            val numberAccount = itemView.findViewById(R.id.accountNumber) as TextView
//            val accountAmount = itemView.findViewById(R.id.accountAmount) as TextView
//            itemView.totalAmount.text = "Текущий баланс: \n${project.profit.toString()}сом"
        }
    }
}