package com.nazar.customview

import android.content.Context
import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import com.nazar.customview.databinding.BottomButtonsBinding

class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: BottomButtonsBinding

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, R.style.MyBottomButtonsStyle)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.bottomButtonsStyle)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.bottom_buttons, this, true)
        binding = BottomButtonsBinding.bind(this)

        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        attrs ?: return

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomButtonsView, defStyleAttr, defStyleRes)

        val negativeButtonText: String
        val positiveButtonText: String

        @ColorInt val negativeButtonColor: Int
        @ColorInt val positiveButtonColor: Int

        val inProgress: Boolean

        with(typedArray) {
            negativeButtonText = getString(R.styleable.BottomButtonsView_negativeButtonText) ?: "Cancel"
            positiveButtonText = getString(R.styleable.BottomButtonsView_positiveButtonText) ?: "Ok"
            negativeButtonColor = getColor(R.styleable.BottomButtonsView_negativeButtonBackgroundColor, Color.WHITE)
            positiveButtonColor = getColor(R.styleable.BottomButtonsView_positiveButtonBackgroundColor, Color.BLACK)
            inProgress = getBoolean(R.styleable.BottomButtonsView_inProgress, false)
        }

        with(binding) {
            negativeButton.text = negativeButtonText
            positiveButton.text = positiveButtonText
            negativeButton.setBackgroundColor(negativeButtonColor)
            positiveButton.setBackgroundColor(positiveButtonColor)
            setVisibility(inProgress)
        }

        typedArray.recycle()
    }

    private fun BottomButtonsBinding.setVisibility(inProgress: Boolean) {
        if (inProgress) {
            progressBar.visibility = VISIBLE
            negativeButton.visibility = GONE
            positiveButton.visibility = GONE
        } else {
            progressBar.visibility = GONE
            negativeButton.visibility = VISIBLE
            positiveButton.visibility = VISIBLE
        }
    }
}