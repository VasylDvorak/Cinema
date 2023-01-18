package com.example.cinema.view.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cinema.R
import com.example.cinema.databinding.FragmentNoteBinding
import com.example.cinema.model.model_stuio.Docs
import com.example.cinema.view.PlayMovieFragment
import com.example.cinema.view.details.DetailsFragment
import com.example.cinema.viewmodel.NoteFragmentViewModel

class NoteFragment : Fragment() {

    companion object {
        const val STRING_EXTRA = "note"
        fun newInstance(bundle: Bundle): NoteFragment {
            val fragment = NoteFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
    private var new_note =""
    private var old_note =""

    private var _binding: FragmentNoteBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: NoteFragmentViewModel by lazy {
        ViewModelProvider(this).get(NoteFragmentViewModel::class.java)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        old_note = arguments?.getString(STRING_EXTRA)!!
        displayNote(old_note)

    }



    private fun displayNote(str: String) {
        with(binding){
            note.apply {
                setText(str)
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                        new_note = charSequence.toString()

                    }
                    override fun afterTextChanged(editable: Editable) {} })
                apply.setOnClickListener {}
                backAction.setOnClickListener {
                    callBackAction()
                }
    }}}



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun callBackAction() {

        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.flFragment, DetailsFragment.newInstance(Bundle().apply {
                }))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }

    }

}