package com.example.cinema.view.note_fragment

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
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.view.details_fragment.DetailsFragment
import com.example.cinema.viewmodel.NoteFragmentViewModel

class NoteFragment : Fragment() {

    companion object {
        const val DOCS_EXTRA = "DOCS"
        fun newInstance(bundle: Bundle): NoteFragment {
            val fragment = NoteFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

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

            var old_note: Docs = arguments?.getParcelable(DOCS_EXTRA) ?: Docs()

            viewModel.getDataNote(old_note)

        val observer = Observer<Docs> { displayNote(it) }
        viewModel.getNote().observe(viewLifecycleOwner, observer)
    }

    private fun displayNote(docsDataBase: Docs) {
        var newNote =""
        with(binding){
            note.apply {
                setText(docsDataBase.note)
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                        newNote = charSequence.toString()
                    }
                    override fun afterTextChanged(editable: Editable) {} })
                apply.setOnClickListener {
                    docsDataBase.note=newNote
                    viewModel.updateNote(docsDataBase)
                }
                backAction.setOnClickListener {
                    callBackAction()
                }
            }
        }
    }



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