package com.vishnevskiypro.activitystate

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.os.PersistableBundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import com.vishnevskiypro.activitystate.databinding.ActivityMainBinding
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import kotlin.properties.Delegates.notNull
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var state: State


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.increment.setOnClickListener { increment() }
        binding.randomColor.setOnClickListener { setRandomColor() }
        binding.switchVisibility.setOnClickListener { switchVisibility() }

        state = savedInstanceState?.getParcelable(KEY_STATE) ?: State(
            counterValue = 0,
            counterTextColor = ContextCompat.getColor(this, R.color.purple_700),
            counterVisibility = true
        )
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, state)
    }


    private fun increment(){
        state.counterValue++
        renderState()
    }

    private fun setRandomColor(){
        state.counterTextColor = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        renderState()
    }

    private fun switchVisibility() {
        state.counterVisibility = !state.counterVisibility
        renderState()
    }

    private fun renderState() = with(binding){
        counterTextView.setText(state.counterValue.toString())
        counterTextView.setTextColor(state.counterTextColor)
        counterTextView.visibility = if (state.counterVisibility) View.VISIBLE else View.INVISIBLE
    }

    @Parcelize
    class State(
        var counterValue: Int,
        var counterTextColor: Int,
        var counterVisibility: Boolean
    ): Parcelable

    companion object{
        @JvmStatic private val KEY_STATE = "STATE"
    }



}