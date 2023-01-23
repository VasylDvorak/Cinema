package com.example.cinema.view.contentprovider

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.databinding.FragmentContentProviderBinding
import com.example.cinema.model.room_data_base.TelephoneContact
import com.example.cinema.viewmodel.ContentProviderFragmentViewModel


const val REQUEST_CODE = 42

class ContentProviderFragment : Fragment() {
    private var _binding: FragmentContentProviderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContentProviderFragmentViewModel by lazy {
        ViewModelProvider(this).get(ContentProviderFragmentViewModel::class.java)
    }
    private lateinit var adapter_main: ContentProviderFragmentAdapter
    private lateinit var message: String
    private  var action: Boolean = false

    companion object {
        const val MESSAGE = "MESSAGE"
        const val ACT = "ACT"
        fun newInstance(bundle: Bundle): ContentProviderFragment {
            val fragment = ContentProviderFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       message = arguments?.getString(MESSAGE).toString()
        action = arguments?.getBoolean(ACT)!!

        checkPermission()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun checkPermission() {
        context?.let {

            when {
                (ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) ==
                        PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(it, Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(it, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED)-> {
                    viewModel.getDataFromDataBase()
                    val observer = Observer<MutableList<TelephoneContact>> {
                        showData(it)
                    }
                    viewModel.getData().observe(viewLifecycleOwner, observer)

                }

                (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS )||
                        shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE ) ||
                        shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS ))-> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ")
                        .setMessage("Необходимо получить от вас разрешение")
                        .setPositiveButton("Предоставить доступ") { _, _ ->
                            requestPermission()
                        }
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {

        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult (
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    val observer = Observer<MutableList<TelephoneContact>> {
                        showData(it)
                    }
                    viewModel.getData().observe(viewLifecycleOwner, observer)

                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ")
                            .setMessage("Вы будете закрывать доступ")
                            .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
            }
        }
    }

    private fun showData(conactsList: MutableList<TelephoneContact>) {
        with(binding) {
            loadingLayout.visibility = View.GONE
            adapter_main = initAdapter()
            adapter_main.setNewContacts(conactsList)

            val recyclerViewContacts: RecyclerView = recyclerViewLinesContacts
            recyclerViewContacts.apply {
                layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
                )
                recycledViewPool.clear()
                adapter = adapter_main
            }

        }
    }

    private fun initAdapter(): ContentProviderFragmentAdapter {

        return ContentProviderFragmentAdapter(object :
            ContentProviderFragmentAdapter.OnItemViewClickListener {

            override fun onItemClick(contact: TelephoneContact) {

                viewModel.call(contact.number)
            }
        }, object :
            ContentProviderFragmentAdapter.SendSMSClickListener {

            override fun onItemClick(contact: TelephoneContact) {
                viewModel.sendSMS(contact.number, message)
                requireFragmentManager().popBackStack()
            }
        }, action)
    }
}