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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.d3if3055.assesment2.BaseApplication
import com.d3if3055.assesment2.R
import com.d3if3055.assesment2.data.SettingDataStore
import com.d3if3055.assesment2.data.dataStore
import com.d3if3055.assesment2.databinding.FragmentForageableListBinding
import com.d3if3055.assesment2.ui.adapter.ForageableListAdapter
import com.d3if3055.assesment2.ui.viewmodel.ForageableViewModel
import com.d3if3055.assesment2.ui.viewmodel.ForageableViewModelFactory
import kotlinx.coroutines.launch


class ForageableListFragment : Fragment() {

    private var isLinearLayout = true
    private val layoutDataStore: SettingDataStore by lazy {
        SettingDataStore(requireContext().dataStore)
    }
    private val viewModel: ForageableViewModel by activityViewModels {
        ForageableViewModelFactory(
            (activity?.application as BaseApplication).database.forageableDao()
        )
    }

    private var _binding: FragmentForageableListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForageableListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) {
            isLinearLayout = it
            setLayout()
            activity?.invalidateOptionsMenu()
        }
        val adapter = ForageableListAdapter { forageable ->
            val action = ForageableListFragmentDirections
                .actionForageableListFragmentToForageableDetailFragment(forageable.id)
            findNavController().navigate(action)
        }

        // TODO: observe the list of forageables from the view model and submit it the adapter
        viewModel.forageables.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {
            recyclerView.adapter = adapter
            addForageableFab.setOnClickListener {
                findNavController().navigate(
                    R.id.action_forageableListFragment_to_addForageableFragment
                )
            }
        }
    }
    private fun setLayout() {
        binding.recyclerView.layoutManager = if (isLinearLayout)
            LinearLayoutManager(context)
        else
            GridLayoutManager(context, 2)
    }
    private fun setIcon(menuItem: MenuItem) {
        val iconId = if (isLinearLayout)
            R.drawable.baseline_grid_view_24
        else
            R.drawable.baseline_view_list_24
        menuItem.icon = ContextCompat.getDrawable(requireContext(), iconId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
        val switchLayoutMenuItem = menu.findItem(R.id.action_switch_layout)
        setIcon(switchLayoutMenuItem)
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
            R.id.action_switch_layout -> {
                lifecycleScope.launch {
                    layoutDataStore.saveLayout(!isLinearLayout, requireContext())
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }




}
