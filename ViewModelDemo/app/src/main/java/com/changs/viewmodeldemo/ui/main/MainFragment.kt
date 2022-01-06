package com.changs.viewmodeldemo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.changs.viewmodeldemo.R
import com.changs.viewmodeldemo.databinding.MainFragmentBinding
/*데이터 모델을 사용하면서 데이터 변경을 관찰하려면 프래그먼트에서 ViewModel의 참조를 얻어야 한다.
* 프래그먼트나 액티비티에서 필요한 ViewModel의 참조는 ViewModelProvider 인스턴스를 사용해서 얻는다.*/
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel의 참조는 ViewModelProvider을 통해 얻는다.
        //ViewModelProvider의 인스턴스는 프래그먼트 내부에서 ViewModelProvider 클래스를 사용해서 생성하며,
        //이때 이 클래스의 생성자에는 현재의 프래그먼트나 액티비티 참조를 전달한다.
        //val viewModelProvider = ViewModelProvider(this)
        /*그리고 ViewModelProvider 인스턴스가 생성되면 이 인스턴스의 get() 함수를 호출하며,
        * 이때 우리가 필요한 ViewModel 클래스를 인자로 전달한다. 그러면 해당 ViewModel 클래스의
        * 새로운 인스턴스가 반환된다. 단, 해당 ViewModel 인스턴스가 이미 있을 때는 기존 인스턴스가 반환된다.*/
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        /*사용자 버튼을 누르면 onClick 함수가 실행되며 이 함수는 우선 EditText 뷰의 현재 값이
        * 있는지 확인한 후(isNotEmpty()) 있으면 ViewModel 인스턴스의 setAmount() 함수를 호출한다.
        * 그 다음에 ViewModel 인스턴스의 getResult() 함수를 호출하여 환전된 금액을 얻은 후 resultText TextView에
        * 지정하여 보여준다.
        * 주석처리된 코드는 LiveData를 사용하지 않았으므로 프래그먼트가 매번 생성될 때마다 ViewModel로부터
        * 가장 최근 결과값을 가져와야 한다. LiveData를 사용한 코드와 사용하지 않은 코드를 비교해보자.*/

        //binding.resultText.text = viewModel.getResult().toString() 아래 코드로 변경

        /*resultObserver 인스턴스는 람다 코드를 선언하고 있으며, 이 코드에서는 현재의 결괏값을 받아서
        * 문자열로 변환한 후 resultText 객체에 지정한다. (resultObserver 인스턴스는 Observer
        * 인터페이스를 구현하는 객체이며, 람다 코드는 Observer 인터페이스의 유일한 함수인 onChanged()를 구현하고
        * 있다. 이 함수는 LiveData의 데이터 값이 변경될 때 자동 호출된다.)*/
        val resultObserver = Observer<Float> { result ->
            binding.resultText.text = result.toString()
        }
        /*다음으로 할 일은 결과값을 갖는 LiveData 객체에 resultObserver 를 추가하는 것이다.
        * 이 LiveData 객체의 참조는 ViewModel 객체의 getResult() 함수를 호출하여 얻을 수 있다.
        * 그리고 observe() 함수를 호출하면 LiveData 객체의 옵저버로 resultObserver가 설정된다.
        *
        * 아래 코드를 추가하면 LiveData 객체의 환전 결과 데이터가 변경될 때 바로 앞에서 구현한
        * resultObserver의 onChanged() 콜백 함수가 실행되어 resultText의 값이 변경된다.*/

        //생성된 Observer 인스턴스는 LiveData의 observe() 함수 호출을 통해 LiveData 인스턴스에 연결
        viewModel.getResult().observe(viewLifecycleOwner, resultObserver)

        binding.run {
            convertButton.setOnClickListener {
                if (binding.dollarText.text.isNotEmpty()) {
                    viewModel.setAmount(dollarText.text.toString())
                    //장치를 회전해도 결괏값인 유로 금액이 보전된다. 왜냐하면 프래그먼트가 소멸되었다가 재생성되더라도
                    //ViewModel은 메모리에 남아 있기 때문이다.

                    //프래그먼트가 재생성되어 다시 시작될 때마다 ViewModel의 결과 데이터로 resultText를 변경
                    // resultText.text = viewModel.getResult().toString()

                    /*위에 코드 변경
                    * 왜냐하면 프래그먼트가 어떤 이유로든 소멸 및 재생성될 때 또는 사용자가 버튼을 클릭할 때
                    * 결괏값을 보여 주기 위해 삭제된 코드가 필요했지만 옵저버가 추가되어 이제는 필요없기 때문이다.
                    * 여기서 LiveData는 자신을 관찰하는 옵저버(UI 컨트롤러인 MainFragment)의 생명주기 상태를
                    * 살핀다. 그리고 옵저버가 재생성되면 자동으로 모든 관련 옵저버에게 통보하고(onChanged() 함수 호출)
                    * 최신 데이터를 제공한다.*/
                } else {
                    resultText.text = "No Value"
                }
            }

        }

    }

}