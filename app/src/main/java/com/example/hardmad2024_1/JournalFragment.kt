package com.example.hardmad2024_1

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat.LayoutParams
import androidx.fragment.app.Fragment
import com.example.hardmad2024_1.databinding.JournalFragmentBinding
import com.example.hardmad2024_1.utilities.CardData
import com.example.hardmad2024_1.utilities.ShortNote
import com.example.hardmad2024_1.utilities.toPx
import com.example.hardmad2024_1.views.CustomProgressView
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class JournalFragment:Fragment(R.layout.journal_fragment){
    private lateinit var binding: JournalFragmentBinding
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = JournalFragmentBinding.bind(view)

        binding.records.text = this.resources.getQuantityString(R.plurals.records, 5, 5)
        binding.perDay.text = this.resources.getQuantityString(R.plurals.records, 2, 2)
        binding.streak.text = this.resources.getQuantityString(R.plurals.days, 3, 3)

        val params = binding.progressBarContainer.layoutParams
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val calculatedWidth = screenWidth - 48.toPx(requireContext())
        params.width = calculatedWidth
        params.height = calculatedWidth
        binding.progressBarContainer.layoutParams = params

        if(requireActivity().intent.getBooleanExtra("isEmpty", true)){
            binding.progressBarEmpty.visibility = VISIBLE
            val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotation_gradient)
            binding.progressBarEmpty.animation = rotateAnimation
        }
        else{
            binding.progressBarEmpty.visibility = GONE
            val progressView = CustomProgressView(requireContext(),
                colors = listOf(
                    ShortNote(color = Color.YELLOW, amount = 4),
                    ShortNote(color = Color.RED, amount = 1),
                    ShortNote(color = Color.GREEN, amount = 2),
                    ShortNote(color = Color.BLUE, amount = 1)),
                totalAmount = 10)
            progressView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            binding.progressBarContainer.addView(progressView)
        }



        binding.addBtn.setOnClickListener {
            startActivity(Intent(context, AddNoteActivity::class.java))
        }

        initCards()
    }

    private fun initCards(){
        val card = layoutInflater.inflate(R.layout.emotion_card, binding.emotionsList, false)
        val card1 = layoutInflater.inflate(R.layout.emotion_card, binding.emotionsList, false)
        val card2 = layoutInflater.inflate(R.layout.emotion_card, binding.emotionsList, false)
        val card3 = layoutInflater.inflate(R.layout.emotion_card, binding.emotionsList, false)

        editCard(
            CardData("сегодня, 12:00",
                "выгорание",
                R.color.blue_text,
                R.drawable.card_shape_blue,
                R.drawable.ic_blue_emote), card)

        editCard(
            CardData("сегодня, 10:45",
                "спокойствие",
                R.color.green_text,
                R.drawable.card_shape_green,
                R.drawable.ic_green_emote), card1)

        editCard(
            CardData("вчера, 23:11",
                "продуктивность",
                R.color.yellow_text,
                R.drawable.card_shape_yellow,
                R.drawable.ic_yellow_emote), card2)

        editCard(
            CardData("вчера, 11:11",
                "выгорание",
                R.color.red_text,
                R.drawable.card_shape_red,
                R.drawable.ic_red_emote), card3)
    }

    private fun editCard(card: CardData, cardView:View){
        cardView.background = resources.getDrawable(card.backgroundDrawable)
        cardView.findViewById<ImageView>(R.id.icon).setImageResource(card.icon)
        cardView.findViewById<TextView>(R.id.date).text = card.date
        val emotionType = cardView.findViewById<TextView>(R.id.emotion_type)
        emotionType.apply {
            text = card.emoteText
            setTextColor(resources.getColor(card.textColor))
        }

        cardView.setOnClickListener {
            startActivity(Intent(requireContext(), AddNoteDetailsActivity::class.java).apply {
                putExtra("card", card)
            })
        }

        binding.emotionsList.addView(cardView)
    }
}