package ru.alexanurin.alexanurinnavigationdrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ru.alexanurin.alexanurinnavigationdrawer.databinding.FragmentMyfragmentBinding
import ru.alexanurin.alexanurinnavigationdrawer.livedata.SharedViewModel

class MyFragment : Fragment() {
    //  view binding
    private lateinit var binding: FragmentMyfragmentBinding

    //  экземпляр SharedViewModel.
    private val sharedViewModel: SharedViewModel by activityViewModels()

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

        val arguments: Bundle? = arguments
        //  Если аргументы вдруг не придут, то requireArguments() выдаст ошибку, поэтому проверка.
        if (arguments != null && arguments.containsKey(FRAGMENT_BACKGROUND_COLOR) && arguments.containsKey(
                FRAGMENT_ITEM_NUMBER
            )
        ) {
            //  Return the arguments supplied when the fragment was instantiated.
            val argsItemNumber = requireArguments().getInt(FRAGMENT_ITEM_NUMBER)
            val argsBackgroundColor = requireArguments().getInt(FRAGMENT_BACKGROUND_COLOR)
            binding.clMyFragment.setBackgroundColor(argsBackgroundColor)
            binding.tvSelectedItemMyFragment.text = "Item $argsItemNumber"
        } else {
            //  Если аргументы не пришли, то фрагмент не был пересоздан, или создан с пустым
            // конструктором. Подписываемся на LiveData в SharedViewModel.
            sharedViewModel.updateUiDataEvent.observe(viewLifecycleOwner, Observer {
                binding.tvSelectedItemMyFragment.text = "Item ${it.first}"
                binding.clMyFragment.setBackgroundColor(it.second)
            })
        }

    }

    companion object {
        private const val FRAGMENT_BACKGROUND_COLOR = "FRAGMENT_BACKGROUND_COLOR"
        private const val FRAGMENT_ITEM_NUMBER = "FRAGMENT_ITEM_NUMBER"

        //  Создание экземпляра фрагмента с параметрами.
        fun createMyFragmentInstance(itemNumber: Int, backGroundColor: Int): MyFragment {
            val myFragment = MyFragment()
            myFragment.arguments = bundleOf(
                FRAGMENT_ITEM_NUMBER to itemNumber,
                FRAGMENT_BACKGROUND_COLOR to backGroundColor
            )

            return myFragment
        }

        //  Создание экземпляра фрагмента без параметров.
        fun createMyFragmentInstance() = MyFragment()
    }
}