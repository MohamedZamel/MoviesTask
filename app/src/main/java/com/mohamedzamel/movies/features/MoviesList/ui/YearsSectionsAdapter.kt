package com.mohamedzamel.movies.features.MoviesList.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedzamel.movies.R
import com.mohamedzamel.movies.databinding.MovieListItemBinding
import com.mohamedzamel.movies.features.MoviesList.MoviesAdapter
import com.mohamedzamel.movies.shared.database.entities.Movie
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters


class YearsSection(list: List<Movie>, val header: String, val parent: ViewGroup) : Section(
    SectionParameters.builder()
        .itemResourceId(R.layout.search_movies_section_list_item)
        .headerResourceId(R.layout.search_movies_header_list_item)
        .build()
) {
    var itemList = list
    override fun getContentItemsTotal(): Int {
        return itemList.size // number of items of this section
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
        /**
        return [MoviesAdapter.MovieViewHolder] for the items of this section
         */
        return MoviesAdapter.MovieViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(view?.context),
                parent,
                false
            )
        )
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieItem = itemList[position]
        (holder as MoviesAdapter.MovieViewHolder).bind(movieItem)
    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder {
        // return an empty instance of ViewHolder for the headers of this section
        return view?.let { TitleHeaderViewHolder(it) } as RecyclerView.ViewHolder
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        (holder as TitleHeaderViewHolder).sectionedHeaderTv.text = header
    }


}

internal class TitleHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val sectionedHeaderTv: TextView =
        itemView.findViewById<View>(R.id.sectionedHeaderTv) as TextView

}