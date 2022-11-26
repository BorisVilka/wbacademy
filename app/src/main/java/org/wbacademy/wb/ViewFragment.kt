package org.wbacademy.wb

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.wbacademy.wb.databinding.FragmentViewBinding
import java.io.IOException
import java.io.InputStream


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    private lateinit var binding: FragmentViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewBinding.inflate(inflater,container,false)
        binding.textView.setText(arr[param1!!])
        try {
            // get input stream
            val ims: InputStream = requireActivity().assets.open("${param1!! +1}.jpg")
            // load image as Drawable
            val d = Drawable.createFromStream(ims, null)
            // set image to ImageView
            binding.imageView.setImageDrawable(d)
            ims.close()
        } catch (ex: IOException) {

        }
        return binding.root
    }

    var arr = arrayOf(
        "Маркетплейсы — это торговые онлайн-площадки. Любой локальный производитель и крупный бренд может разместить там свой товар и сразу же выйти на миллионную аудиторию.\n" +
                "\n" +
                "Работа с маркетплейсами — востребованный навык, особенно полезный для частных продавцов, руководителей небольших производств, предпринимателей и тех, кто хочет зарабатывать удалённо.",
        "Весной 2022 года потребители совершили покупки на 500 млрд рублей только на 5 крупнейших маркетплейсах. Это на 73% выше, чем весной 2021 года.\n" +
                "36% продавцов отметили, что объём их продаж через маркетплейсы значительно вырос.\n" +
                "Уменьшилось число продавцов, которые открывают собственные интернет-магазины, зато большая часть присутствует минимум на 2 маркетплейсах. ",
        "На маркетплейсы уже заходят миллионы покупателей, которые ищут нужные им товары. По итогам 2021 года у российских маркетплейсов было более 70 млн покупателей— почти половина жителей России.\n" +
                "Для старта достаточно оформить юрлицо, ИП или стать самозанятым, открыть личный кабинет и платить комиссию только с проданного товара. Не надо нанимать программистов — на маркетплейсах уже есть все инструменты для торговли и аналитики. ",
        " Не нужно ломать голову, как доставить товары в отдалённые уголки страны. Вы сможете отправить товар на ближайший склад — а оттуда маркетплейс сам доставит его покупателям в пункты выдачи или прямо на дом с курьером. "
    )

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            ViewFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}