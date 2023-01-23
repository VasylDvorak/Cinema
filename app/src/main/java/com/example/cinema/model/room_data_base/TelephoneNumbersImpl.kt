package com.example.cinema.model.room_data_base

import android.annotation.SuppressLint
import android.database.Cursor
import android.provider.ContactsContract
import com.example.cinema.app.App


class TelephoneNumbersImpl() : TelephoneNumbers {

    @SuppressLint("Range")
    override fun getContacts(): MutableList<TelephoneContact> {
        var telephoneContacts:  MutableList<TelephoneContact> = mutableListOf()
        var cursorWithContacts: Cursor? =  App.appInstance!!.applicationContext?.contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        if (cursorWithContacts != null) {
            while (cursorWithContacts.moveToNext()) {
                val name =
                    cursorWithContacts.getString(cursorWithContacts.
                    getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                var phoneNumber =
                    cursorWithContacts.getString(cursorWithContacts.
                    getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            val info = TelephoneContact(name, phoneNumber)
                telephoneContacts.add(info)
                }
            }

        cursorWithContacts?.close()
        return telephoneContacts
        }

    }












