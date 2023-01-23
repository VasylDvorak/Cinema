package com.example.cinema.view.contentprovider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.model.room_data_base.TelephoneContact
import kotlinx.android.synthetic.main.contact_item.view.*

class ContentProviderFragmentAdapter(
    private var onItemViewClickListener: OnItemViewClickListener?,
    private var sendSNSClickListener: SendSMSClickListener?,
   private var action: Boolean
) :
    RecyclerView.Adapter<ContentProviderFragmentAdapter.MainViewHolder>() {

    private var contacts: MutableList<TelephoneContact> = mutableListOf()


    interface OnItemViewClickListener {
        fun onItemClick(contact: TelephoneContact)
    }
    interface SendSMSClickListener {
        fun onItemClick(contact: TelephoneContact)
    }

    fun setNewContacts(new_contacts: MutableList<TelephoneContact>) {
        contacts = new_contacts
        notifyDataSetChanged()
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(aboutContact: TelephoneContact) {

            itemView.apply {

                name.text = resources.getText(R.string.abonent_name) as String + " " + aboutContact.name
                number.text = resources.getText(R.string.number) as String + " " + aboutContact.number
            if(!action){
                push_call.visibility=View.VISIBLE
                send_sms.visibility=View.GONE
                push_call.setOnClickListener {
                    onItemViewClickListener?.onItemClick(aboutContact)
                }
            }else{
                push_call.visibility=View.GONE
                send_sms.visibility=View.VISIBLE
                send_sms.setOnClickListener {
                    sendSNSClickListener?.onItemClick(aboutContact)
                }
            }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.contact_item,
                parent, false
            ) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

}