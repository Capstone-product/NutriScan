package com.dicoding.nutriscan.ui.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.nutriscan.R
import com.capstone.nutriscan.databinding.FragmentDictionaryBinding
import com.dicoding.nutriscan.ui.home.Fruit

class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<Fruit>()

    private lateinit var dictionaryAdapter: DictionaryAdapter
    private lateinit var rvFruit: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFruit = binding.rvEvents
        rvFruit.setHasFixedSize(true)

        list.addAll(getListFruit())
        showRecyclerList()

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = list.filter { fruit ->
                    fruit.name.contains(newText.orEmpty(), ignoreCase = true)
                }
                dictionaryAdapter.setData(filteredList as ArrayList<Fruit>)
                return true
            }
        })
    }

    private fun getListFruit(): ArrayList<Fruit> {
        val fruitNames = resources.getStringArray(R.array.fruit_names)
        val fruitDescriptions = resources.getStringArray(R.array.fruit_dictionary)
        val fruitImages = resources.obtainTypedArray(R.array.data_image)
        val listFruit = ArrayList<Fruit>()
        for (i in fruitNames.indices) {
            val fruit = Fruit(fruitNames[i], fruitDescriptions[i], fruitImages.getResourceId(i, -1))
            listFruit.add(fruit)
        }
        return listFruit
    }

    private fun showRecyclerList() {
        rvFruit.layoutManager = LinearLayoutManager(requireContext())
        dictionaryAdapter = DictionaryAdapter(list)
        rvFruit.adapter = dictionaryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
