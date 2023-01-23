package com.example.cinema.viewmodel

import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.R
import com.example.cinema.app.App
import com.example.cinema.model.room_data_base.TelephoneContact
import com.example.cinema.model.room_data_base.TelephoneNumbersImpl


class ContentProviderFragmentViewModel (
    private val liveDataToObserve: MutableLiveData<MutableList<TelephoneContact>> = MutableLiveData(),
    private val telephoneNumbersImpl: TelephoneNumbersImpl = TelephoneNumbersImpl()
) : ViewModel() {

    fun getDataFromDataBase() {

        try {
            Thread{
                var contacts = telephoneNumbersImpl.getContacts()
                contacts.let {
                    liveDataToObserve.postValue(it)
                }}.start()

        } catch (e: NullPointerException) {
        }
    }

    fun getData(): MutableLiveData<MutableList<TelephoneContact>> {
        return liveDataToObserve
    }

    fun call(number: String) {
        val toDial = "tel:" + number
        App.appInstance!!.applicationContext.startActivity(
            Intent(Intent.ACTION_CALL, Uri.parse(toDial)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    fun sendSMS(number: String, message : String){
       var full_message = App.appInstance!!.applicationContext.resources.
       getText(R.string.sms).toString()+"  "+ message
        val sm: SmsManager = SmsManager.getDefault()
        sm.sendTextMessage(number, null, full_message, null, null)
    }

}