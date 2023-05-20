package com.d3if3055.assesment2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.d3if3055.assesment2.BaseApplication
import com.d3if3055.assesment2.R
import com.d3if3055.assesment2.databinding.FragmentForageableDetailBinding
import com.d3if3055.assesment2.model.Forageable
import com.d3if3055.assesment2.ui.viewmodel.ForageableViewModel
import com.d3if3055.assesment2.ui.viewmodel.ForageableViewModelFactory

class ForageableDetailFragment : Fragment() {

    private val navigationArgs: ForageableDetailFragmentArgs by navArgs()


    //  from BaseApplication
    private val viewModel: ForageableViewModel by activityViewModels {
        ForageableViewModelFactory(
            (activity?.application as BaseApplication).database.forageableDao()
        )
    }

    private lateinit var forageable: Forageable

    private var _binding: FragmentForageableDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForageableDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        // TODO: Observe a forageable that is retrieved by id, set the forageable variable,
        //  and call the bind forageable method
        viewModel.getForageablesById(id).observe(this.viewLifecycleOwner) {
            forageable = it
            bindForageable()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> {
                val currentDestination = findNavController().currentDestination?.id
                when (currentDestination) {
                    R.id.forageableDetailFragment -> {
                        findNavController().navigate(R.id.action_forageableDetailFragment_to_aboutFragment)
                        return true
                    }
                    R.id.forageableListFragment -> {
                        findNavController().navigate(R.id.action_forageableListFragment_to_aboutFragment)
                        return true
                    }
                    R.id.addForageableFragment -> {
                        findNavController().navigate(R.id.action_addForageableFragment_to_aboutFragment)
                        return true
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun bindForageable() {
        binding.apply {
            name.text = forageable.name
            location.text = forageable.address
            notes.text = forageable.notes
            if (forageable.inSeason) {
                season.text = getString(R.string.in_season)
            } else {
                season.text = getString(R.string.out_of_season)
            }
            editForageableFab.setOnClickListener {
                val action = ForageableDetailFragmentDirections
                    .actionForageableDetailFragmentToAddForageableFragment(forageable.id)
                findNavController().navigate(action)
            }
            btnShare.setOnClickListener {
                shareData()
            }

            location.setOnClickListener {
                launchMap()
            }
        }
    }

    private fun shareData() {
        val message = getString(
            R.string.bagikan_template,
            forageable.name,
            forageable.address,
            forageable.notes,
            if (forageable.inSeason) getString(R.string.in_season) else getString(R.string.out_of_season)
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }

    private fun launchMap() {
        val address = forageable.address.let {
            it.replace(", ", ",")
            it.replace(". ", " ")
            it.replace(" ", "+")
        }
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}
