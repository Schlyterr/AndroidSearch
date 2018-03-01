package com.example.fredrik.myapplication

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import kotlin.collections.ArrayList

/**
 * Created by fredrik on 2018-02-11.
 */
class SuggestionAdapter<T>(context: Context?, resource: Int, objects: List<String>) : ArrayAdapter<String>(context, resource, objects) {

    var storeWords = mutableMapOf<Char, List<String>>()

    init {
        if (storeWords.isEmpty() and objects.isNotEmpty()) {
            var letter = 'a'
            var index = 0
            var tempList = ArrayList<String>()
            while (letter <= 'ö') {
                val word = objects[index]
                if (word.first() == letter) tempList.add(word)
                else {
                    storeWords.put(letter, tempList)
                    tempList = ArrayList<String>()
                    tempList.add(word)
                    ++letter
                }
                index++
            }
        }
    }

    override fun getFilter(): Filter {
        return wordFilter
    }

    var wordFilter = object: Filter() {
        override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
            val values = (filterResults?.values as ArrayList<String>)
            clear()
            values.forEach { add(it) }
            notifyDataSetChanged()
        }

        override fun performFiltering(userInput: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            val suggestions = ArrayList<String>()
            val words = storeWords[userInput?.first()] ?: ArrayList<String>()
            var index = 0
            while ((suggestions.size <= 100) and (index < words.size)) {
                val word = words[index]
                if (word.startsWith(userInput.toString())) suggestions.add(word)
                index++
            }
            filterResults.values = suggestions
            filterResults.count = suggestions.size
            return filterResults
        }
    }
}