package com.changs.roomdemo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.viewModels
import com.changs.roomdemo.Product
import com.changs.roomdemo.R
import com.changs.roomdemo.databinding.MainFragmentBinding
import java.util.*

class MainFragment : Fragment() {

    private var adapter : ProductListAdapter? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    val viewModel: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listenerSetUp()
        observerSetup()
        recyclerSetUp()
    }



    private fun listenerSetUp() {
        binding.addButton.setOnClickListener{
            val name = binding.productName.text.toString()
            val quantity = binding.productQuantity.text.toString()

            if (name != "" && quantity != "") {
                val product = Product(name, Integer.parseInt(quantity))
                viewModel.insertProduct(product)
                clearFields()
            } else {
                binding.productID.text = "Incomplate information"
            }
        }

        binding.findButton.setOnClickListener {
            viewModel.findProduct(binding.productName.text.toString())
        }
        binding.deleteButton.setOnClickListener{
            viewModel.deleteProduct(binding.productName.text.toString())
            clearFields()
        }
    }

    //UI에 RecyclerView로 보여주는 제품 리스트는 ViewModel의 데이터베이스 조회 결과 및 LiveData 객체와 동기화 해야한다.
    private fun observerSetup() {
        viewModel.getAllProducts()?.observe(viewLifecycleOwner, Observer { products ->
            products?.let {
                adapter?.setProductList(it)
            }
        })

        viewModel.getSearchResults().observe(viewLifecycleOwner, Observer { products ->
            products?.let {
                if (it.isNotEmpty()) {
                    binding.productID.text = String.format(Locale.US, "%d", it[0].id)
                    binding.productName.setText(it[0].productName)
                    binding.productQuantity.setText(String.format(Locale.US, "%d",
                        it[0].quantity))
                } else {
                    binding.productID.text = "No Match"
                }
            }
        })
    }

/*모든 제품의 변경을 관찰하는 옵저버에서는 현재의 제품 리스트를 RecyclerAdapter의 setProductList() 함수 인자로 전달
* 한다. 화면에 보여 주는 제품 리스트를 변경하기 위해서다.
*
* 조회된 결과를 관찰하는 옵저버에서는 최소한 하나의 조회된 제품이 결과에 있는지 확인한 후 List에서 첫 번째 Product 엔터티
* 객체를 추출하여 이것의 데이터를 변환하고 레이아웃의 TextCiew와 EditText에 지정한다. 만일 조회된 제품 데이터가
* 없을 때는 제품 id를 보여 주는 TextCiew에 조회된 제품이 없다는 메시지를 보여준다.
* */


    private fun recyclerSetUp() {
        adapter = ProductListAdapter(R.layout.product_list_item)
        val recyclerView : RecyclerView? = view?.findViewById(R.id.product_recycler)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
    }







    //코드의 여러 곳에서 UI에 나타난 제품 정보 필드의 값을 지워야 하는 경우가 있다.
    //코드의 중복을 방지하기 위해 다음 함수를 추가
    private fun clearFields() {
        binding.productID.text = ""
        binding.productName.setText("")
        binding.productQuantity.setText("")

    }

}