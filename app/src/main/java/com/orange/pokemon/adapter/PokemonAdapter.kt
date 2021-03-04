package com.orange.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.orange.pokemon.R
import com.orange.pokemon.data.PokemonEntity
import com.orange.pokemon.databinding.ItemPokemonLayoutBinding

class pokemonAdapter :
    ListAdapter<PokemonEntity, pokemonAdapter.PokemonViewholder>(PokemonDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewholder {
        val binding = ItemPokemonLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewholder(binding)
    }


    override fun onBindViewHolder(holder: PokemonViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class PokemonViewholder(val binding: ItemPokemonLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(PokemonEntity: PokemonEntity) {
            with(binding) {
                pokemonBasexp.text = PokemonEntity.baseExp
                pokemonCycles.text = PokemonEntity.cycles
                pokemonName.text = PokemonEntity.name
                pokemonCategory.text = PokemonEntity.category
            }
            Glide.with(binding.imageView)
                .load(PokemonEntity.imageurl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_android_black_24dp)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(binding.imageView)
            binding.root.setOnClickListener {

            }

        }
    }
}

class PokemonDiffUtils : DiffUtil.ItemCallback<PokemonEntity>() {

    override fun areItemsTheSame(
        oldItem: PokemonEntity,
        newItem: PokemonEntity
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PokemonEntity,
        newItem: PokemonEntity
    ) = oldItem == newItem
}