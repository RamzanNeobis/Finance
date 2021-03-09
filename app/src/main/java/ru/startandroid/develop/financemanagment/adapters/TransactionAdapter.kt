package ru.startandroid.develop.financemanagment.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_transaction.view.*
import ru.startandroid.develop.financemanagment.R
import ru.startandroid.develop.financemanagment.activity.AddActivity
import ru.startandroid.develop.financemanagment.models.Data
import ru.startandroid.develop.financemanagment.test.EditIncomeTransaction


class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private val transactionList = arrayListOf<Data>()

    fun setData(items: List<Data>) {
        this.transactionList.clear()
        this.transactionList.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
        holder.bindItems(transactionList[position])
        holder.itemView.setOnClickListener {
            //RetrofitBuilder.setToken(transactionList[position])
//            val date =  transactionList[position].transactionDate
//            val account = transactionList[position].score
//            val sum = transactionList[position].sum
//            val category = transactionList[position].operationName
//            val counterParty = transactionList[position].counterPartyName
//            val project = transactionList[position].projectName


            if(transactionList[position].transactionType == "Доход") {
                val intent = Intent(holder.itemView.context, EditIncomeTransaction::class.java)
                val id = transactionList[position].id.toString()

                intent.putExtra("id", id)
//                intent.putExtra("date", date)
//                intent.putExtra("account", account)
//                intent.putExtra("sum", sum).toString()
//                intent.putExtra("category", category)
//                intent.putExtra("counterParty", counterParty)
//                intent.putExtra("project", project)
                holder.itemView.context.startActivity(intent)

            }
            else if (transactionList[position].transactionType == "Расход"){
                val intent = Intent(holder.itemView.context, EditIncomeTransaction::class.java)
                val id = transactionList[position].id.toString()
                intent.putExtra("id", id)
                holder.itemView.context.startActivity(intent)
            }

            else{
                Toast.makeText(holder.itemView.context, "ИЗВИНИ БРАТ НЕ МОГУ", Toast.LENGTH_SHORT).show()
            }
        }


    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return transactionList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindItems(transaction: Data) {
//            when(transaction.isIncome){
//                true -> itemView.card_main.setCardBackgroundColor(Color.GREEN)
//                false -> itemView.card_main.setCardBackgroundColor(Color.RED)
//            }

            itemView.date.text = "Дата: ${transaction.transactionDate}"
            itemView.account.text = "Счет: ${transaction.score}"
            itemView.sum.text = "Сумма: ${transaction.sum}сом "
            itemView.category.text = "Операция: ${transaction.operationName}"
            itemView.counterParty.text = "Конрагент: ${transaction.counterPartyName}"
            itemView.project.text = "Проект: ${transaction.projectName}"
            itemView.transactionType.text = "Тип транзакции: ${transaction.transactionType}"

        }
    }
}