package ru.alexanurin.alexanurinnavigationdrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.alexanurin.alexanurinnavigationdrawer.databinding.FragmentMyfragmentBinding

class MyFragment : Fragment() {
    //  view binding
    private lateinit var binding: FragmentMyfragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyfragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  Return the arguments supplied when the fragment was instantiated.
        val argsItemNumber = requireArguments().getInt(FRAGMENT_ITEM_NUMBER)
        val argsBackgroundColor = requireArguments().getInt(FRAGMENT_BACKGROUND_COLOR)
        binding.clMyFragment.setBackgroundColor(argsBackgroundColor)
        binding.tvSelectedItemMyFragment.text = "Item $argsItemNumber"
        println("onViewCreated")
    }

    companion object {
        private const val FRAGMENT_BACKGROUND_COLOR = "FRAGMENT_BACKGROUND_COLOR"
        private const val FRAGMENT_ITEM_NUMBER = "FRAGMENT_ITEM_NUMBER"

        fun createMyFragmentInstance(itemNumber: Int, backGroundColor: Int): MyFragment {
            val myFragment = MyFragment()
            myFragment.arguments = bundleOf(
                FRAGMENT_ITEM_NUMBER to itemNumber,
                FRAGMENT_BACKGROUND_COLOR to backGroundColor
            )
            println("createMyFragmentInstance")
            return myFragment
        }
    }
}