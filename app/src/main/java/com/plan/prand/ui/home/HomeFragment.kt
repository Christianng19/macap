package com.plan.prand.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.plan.prand.R
import com.plan.prand.databinding.FragmentHomeBinding
import kotlin.math.abs
import android.content.Context

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var carouselAdapter: carruadapter
    private lateinit var carouselViewPager: ViewPager2
    private val handler = Handler(Looper.getMainLooper())

    private val items = listOf(
        itemcarru(R.drawable.img_fisio, "¿Necesitas relajarte?"),
        itemcarru(R.drawable.img_pelu, "¿Un corte a tiempo?"),
        itemcarru(R.drawable.img_pasea, "¿Sin tiempo para salir?"),
        itemcarru(R.drawable.img_charl, "Si necesitas hablar.")
    )

    private val categorias = listOf(
        "✂ Peluquería",
        "🐾 Cuidado Mascotas",
        "🙊 Encuentros",
        "🏠 Cuidado Hogar",
        "📦 Mudanzas",
        "⛱ Masajes",
        "❓ Otros"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        carouselViewPager = binding.carouselViewPager ?: run {
            Log.e("HomeFragment", "carouselViewPager es null! Revisa tu layout fragment_home.xml")
            return root
        }

        // CODIGO CARTAS CAROUSEL


        carouselAdapter = carruadapter(items + items + items + items + items )  // Triplicamos para simular scroll infinito
        carouselViewPager.adapter = carouselAdapter

        carouselViewPager.clipToPadding = false
        carouselViewPager.clipChildren = false
        carouselViewPager.offscreenPageLimit = 99
        carouselViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.viewpager_page_margin)
        val pageOffsetPx = resources.getDimensionPixelOffset(R.dimen.viewpager_offset)

        carouselViewPager.setPageTransformer { page, position ->
            val offset = position * (page.width + pageMarginPx)
            page.translationX = -offset
            val scaleFactor = 0.85f.coerceAtLeast(1 - abs(position))
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
        }

        carouselViewPager.setPageTransformer(zoomtransi())

        carouselViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(autoScrollRunnable)
                handler.postDelayed(autoScrollRunnable, 3000)
            }
        })

        handler.postDelayed(autoScrollRunnable, 3000)


        // CODIGO CATEGORIAS

        val categoriasList = binding.categoriasList

        categorias.take(4).forEach { categoria ->
            val card = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_categoria_button, categoriasList, false) as ViewGroup

            val texto = card.findViewById<TextView>(R.id.textCategoria)
            texto.text = categoria

            categoriasList.addView(card)

            card.setOnClickListener {
                Log.d("Categorias", "Has pulsado: $categoria")
            }
        }

        binding.btnVerTodas.setOnClickListener {
            mostrarCategoriasCompletas()
        }


        // CODIGO DIRECCION



        val addressInput = binding.root.findViewById<EditText>(R.id.addressInput)
        val addressToggle = binding.root.findViewById<ImageButton>(R.id.addressToggle)


        val sharedPref = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        addressToggle.setOnClickListener {
            if (addressInput.visibility == View.GONE) {
                // Cuando el EditText está oculto, lo mostramos
                addressInput.visibility = View.VISIBLE
                addressToggle.setImageResource(R.drawable.ic_expand_cerrar)

                // Recuperar el texto guardado de SharedPreferences que no se lo que es pero funciona
                val savedText = sharedPref.getString("address", "")
                addressInput.setText(savedText)

            } else {
                // Cuando el EditText está visible, lo ocultamos
                val addressText = addressInput.text.toString()

                // Guardar el texto escrito en SharedPreferences
                sharedPref.edit().putString("address", addressText).apply()

                // Ocultar el EditText y cambiar la flecha
                addressInput.visibility = View.GONE
                addressToggle.setImageResource(R.drawable.ic_expand_abrir)
            }
        }

        return root
    }

    private fun mostrarCategoriasCompletas() {
        val bottomSheet = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottomsheet_categorias, null)

        val container = view.findViewById<LinearLayout>(R.id.categoriasContainer)

        categorias.forEach { categoria ->
            val textView = TextView(requireContext()).apply {
                text = categoria
                textSize = 16f
                setPadding(16, 16, 16, 16)
            }
            container.addView(textView)

            textView.setOnClickListener {
                Log.d("Categorias", "Seleccionaste: $categoria")
                bottomSheet.dismiss()
            }
        }

        bottomSheet.setContentView(view)
        bottomSheet.show()
    }

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = carouselAdapter.itemCount
            if (itemCount > 0) {
                val nextItem = (carouselViewPager.currentItem + 1) % itemCount
                carouselViewPager.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 3000)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable)
        _binding = null
    }
}
