//package ru.startandroid.develop.financemanagment.adapters
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.synthetic.main.item_transaction.view.*
//import kotlinx.android.synthetic.main.item_transaction.view.account
//import kotlinx.android.synthetic.main.item_transaction.view.category
//import kotlinx.android.synthetic.main.item_transaction.view.counterParty
//import kotlinx.android.synthetic.main.item_transaction.view.date
//import kotlinx.android.synthetic.main.item_transaction.view.project
//import kotlinx.android.synthetic.main.item_transaction.view.sum
//import kotlinx.android.synthetic.main.item_transaction.view.transactionType
//import kotlinx.android.synthetic.main.item_transaction.view.*
//import ru.startandroid.develop.financemanagment.R
//import ru.startandroid.develop.financemanagment.models.Project
//
//class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
//    private val userList = ArrayList<Project>()
//
//    fun setData(items: List<Project>) {
//        this.userList.clear()
//        this.userList.addAll(items)
//        notifyDataSetChanged()
//    }
//
//    //this method is returning the view for each item in the list
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
//        return ViewHolder(v)
//    }
//
//    //this method is binding the data on the list
//    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
//        holder.bindItems(userList[position])
//    }
//
//    //this method is giving the size of the list
//    override fun getItemCount(): Int {
//        return userList.size
//    }
//
//
//
//    //the class is hodling the list view
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val urlProject = "https://yt3.ggpht.com/a/AATXAJyo_D848IE-MugVYXd796RFTPCQPj96aDk-JMoQpA=s900-c-k-c0x00ffffff-no-rj" +
//                ""
//
//        @SuppressLint("SetTextI18n")
//        fun bindItems(project: Project) {
//            itemView.date.text = "Дата: ${transaction.transactionDate}"
//            itemView.account.text = "Счет: ${transaction.score}"
//            itemView.sum.text = "Сумма: ${transaction.sum}сом "
//            itemView.category.text = "Операция: ${transaction.operationName}"
//            itemView.counterParty.text = "Конрагент: ${transaction.counterPartyName}"
//            itemView.project.text = "Проект: ${transaction.projectName}"
//            itemView.transactionType.text = "Тип транзакции: ${transaction.transactionType}"
//
//
//
//        }
//    }
//}