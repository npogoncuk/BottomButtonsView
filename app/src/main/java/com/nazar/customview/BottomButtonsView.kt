package com.nazar.customview

import android.content.Context
import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import com.nazar.customview.databinding.BottomButtonsBinding

enum class ButtonType {
    NEGATIVE, POSITIVE
}

typealias BottomButtonsClickListener = (ButtonType) -> Unit


class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: BottomButtonsBinding
    private var clickListener: BottomButtonsClickListener? = null

    var inProgress: Boolean = false
        set(value) {
            field = value

            with(binding) {
                if (value) {
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



    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, R.style.MyBottomButtonsStyle)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.bottomButtonsStyle)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.bottom_buttons, this, true)
        binding = BottomButtonsBinding.bind(this)

        initializeAttributes(attrs, defStyleAttr, defStyleRes)
        initializeClickListeners()
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
            setNegativeButtonText(negativeButtonText)
            setPositiveButtonText(positiveButtonText)
            negativeButton.setBackgroundColor(negativeButtonColor)
            positiveButton.setBackgroundColor(positiveButtonColor)
            this@BottomButtonsView.inProgress = inProgress
        }

        typedArray.recycle()
    }

    private fun initializeClickListeners() {
        with(binding) {
            negativeButton.setOnClickListener {
                clickListener?.invoke(ButtonType.NEGATIVE)
            }
            positiveButton.setOnClickListener {
                clickListener?.invoke(ButtonType.POSITIVE)
            }
        }
    }

    fun setClickListener(listener: BottomButtonsClickListener) {
        clickListener = listener
    }

    fun setNegativeButtonText(text: String?) {
        binding.negativeButton.text = text ?: "Cancel"
    }

    fun setPositiveButtonText(text: String?) {
        binding.positiveButton.text = text ?: "Ok"
    }
}