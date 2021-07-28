package com.example.newsfeed.ui.activitylevelUI.sources

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.R
import com.example.newsfeed.adapters.SourcesRecyclerViewAdapter
import com.example.newsfeed.data.models.Source
import com.example.newsfeed.databinding.FragmentSourcelistScreenBinding
import com.example.newsfeed.ui.activitylevelUI.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class SourceListFragment : Fragment(R.layout.fragment_sourcelist_screen), SourcesRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSourcelistScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SourcesRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSourcelistScreenBinding.bind(view)

        setupRecyclerView()

    }

    private fun setupRecyclerView() {

        adapter = SourcesRecyclerViewAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        viewModel.userPreferencesLiveData.observe(viewLifecycleOwner, { preferences ->
            lifecycleScope.launch {
                val sourceList = viewModel.getSource(country = preferences.country, category = preferences.category)
                adapter.submitList(sourceList)
            }
        })
    }

    override fun sourceItemClicked(source: Source) {
        viewModel.sourceId = source.id.toString()
        viewModel.sourceName = source.name
        val sourceId = viewModel.sourceId!!
        val action = SourceListFragmentDirections.actionSourceListScreenFragmentToSourceScreenFragment(sourceId)
        view?.findNavController()?.navigate(action)
    }

    override fun navigateButtonClicked(source: Source) {
        val navigateToWebIntent = Intent(Intent.ACTION_VIEW)
        val url = source.url
        viewModel.sourceUrl = source.url
        navigateToWebIntent.data = Uri.parse(url)
        startActivity(navigateToWebIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
