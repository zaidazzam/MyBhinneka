/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.d3if3055.assesment2.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.d3if3055.assesment2.BaseApplication
import com.d3if3055.assesment2.R
import com.d3if3055.assesment2.model.Forageable
import com.d3if3055.assesment2.ui.viewmodel.ForageableViewModel
import com.d3if3055.assesment2.ui.viewmodel.ForageableViewModelFactory
import com.d3if3055.assesment2.databinding.FragmentAddForageableBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AddForageableFragment : Fragment() {

    private val navigationArgs: AddForageableFragmentArgs by navArgs()

    private var _binding: FragmentAddForageableBinding? = null

    private lateinit var forageable: Forageable

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // TODO: Refactor the creation of the view model to take an instance of
    //  ForageableViewModelFactory. The factory should take an instance of the Database retrieved
    //  from BaseApplication
    private val viewModel: ForageableViewModel by activityViewModels {
        ForageableViewModelFactory(
            (activity?.application as BaseApplication).database.forageableDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddForageableBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        if (id > 0) {

            // TODO: Observe a Forageable that is retrieved by id, set the forageable variable,
            //  and call the bindForageable method
            viewModel.getForageablesById(id).observe(this.viewLifecycleOwner){
                forageable = it
                bindForageable(forageable)
            }

            binding.deleteBtn.visibility = View.VISIBLE
            binding.deleteBtn.setOnClickListener {
                deleteForageable(forageable)
            }
        } else {
            binding.saveBtn.setOnClickListener {
                addForageable()
            }
        }
    }
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteForageable(forageable)
            }
            .show()
    }
    private fun deleteForageable(forageable: Forageable) {
        viewModel.deleteForageable(forageable)
        findNavController().navigate(
            R.id.action_addForageableFragment_to_forageableListFragment
        )
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_forageableDetailFragment_to_aboutFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_forageableListFragment_to_aboutFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_addForageableFragment_to_aboutFragment)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun addForageable() {
        if (isValidEntry()) {
            viewModel.addForageable(
                binding.nameInput.text.toString(),
                binding.locationAddressInput.text.toString(),
                binding.inSeasonCheckbox.isChecked,
                binding.notesInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addForageableFragment_to_forageableListFragment
            )
        }
    }

    private fun updateForageable() {
        if (isValidEntry()) {
            viewModel.updateForageable(
                id = navigationArgs.id,
                name = binding.nameInput.text.toString(),
                address = binding.locationAddressInput.text.toString(),
                inSeason = binding.inSeasonCheckbox.isChecked,
                notes = binding.notesInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addForageableFragment_to_forageableListFragment
            )
        }
    }

    private fun bindForageable(forageable: Forageable) {
        binding.apply{
            nameInput.setText(forageable.name, TextView.BufferType.SPANNABLE)
            locationAddressInput.setText(forageable.address, TextView.BufferType.SPANNABLE)
            inSeasonCheckbox.isChecked = forageable.inSeason
            notesInput.setText(forageable.notes, TextView.BufferType.SPANNABLE)
            binding.deleteBtn.setOnClickListener{showConfirmationDialog()}
            saveBtn.setOnClickListener {
                updateForageable()
            }
        }

    }




    private fun isValidEntry() = viewModel.isValidEntry(
        binding.nameInput.text.toString(),
        binding.locationAddressInput.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
