package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.R
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.databinding.FragmentListBinding
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.contract.MainContract
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.adapter.ClassAdapter
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.states.ClassesState
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment(R.layout.fragment_list) {

    // Koristimo by sharedViewModel jer sada view modele instanciramo kroz koin
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

    private var _binding: FragmentListBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: ClassAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initSpinners()
        initListeners()
    }

    private fun initSpinners(){
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.days_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.daySpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.groups_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.groupsSpinner.adapter = adapter
        }
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = ClassAdapter()
        binding.listRv.adapter = adapter
    }

    private fun initListeners() {
        binding.traziBttn.setOnClickListener {
            val filter = binding.inputEt.text.toString()
            // mainViewModel.getClassesByName(filter)
            var day = ""
            if(binding.daySpinner.selectedItem.toString() != "ALL")
                day = binding.daySpinner.selectedItem.toString()
            var group = ""
            if(binding.groupsSpinner.selectedItem.toString() != "ALL")
                group =  binding.groupsSpinner.selectedItem.toString()
            Timber.e("Saljem " + filter + " " + day + " " + group)
            mainViewModel.getClassesByNameDayClass(filter, day, group)

        }
        /*binding.inputEt.doAfterTextChanged {
            val filter = it.toString()
           // mainViewModel.getClassesByName(filter)
            mainViewModel.getClassesByNameDayClass(filter, binding.daySpinner.selectedItem.toString(), binding.groupsSpinner.selectedItem.toString())
        }*/
    }

    private fun initObservers() {
        mainViewModel.classState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
        // koji zadovoljavaju query
        mainViewModel.getAllClasses()
        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
        // preko metode getAllMovies()
        mainViewModel.fetchAllClasses()
    }

    private fun renderState(state: ClassesState) {
        when (state) {
            is ClassesState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.classes)
            }
            is ClassesState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is ClassesState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is ClassesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.inputEt.isVisible = !loading
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}