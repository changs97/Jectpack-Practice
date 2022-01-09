package com.changs.lifecycledemo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.changs.lifecycledemo.DemoObserver
import com.changs.lifecycledemo.DemoOwner
import com.changs.lifecycledemo.R

//옵저버 클래스인 DemoObserver가 작성되었으므로 다음은 옵저버 클래스를 프래그먼트의 옵저버로 추가할 것이다.
/*생명주기 소유자의 Lifecycle 객체의 addObserver() 함수를 호출하면 생명주기 소유자에 옵저버를 추가할 수 있다.
* Lifecycle 객체의 참조는 lifecycle 속성을 참조하거나 getLifecycle() 함수를 호출하여 얻을 수 있다.
**/

private lateinit var demoOwner : DemoOwner
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //생명주기 소유자에 옵저버를 추가
        //lifecycle.addObserver(DemoObserver())

        /*DemoOwner 클래스의 인스턴스를 생성하고 두 함수를 호출한다.
        * DemoObserver를 옵저버로 추가한 코드는 제거한다. 하나의 옵저버를 다수의 생명주기 소유자에 사용할 수 있지만
        * 여기서는 삭제하였다. 중복된 메시지가 로그캣 창에 출력되지 않도록 하기 위함이다.*/
        demoOwner = DemoOwner()
        demoOwner.startOwner()
        demoOwner.stopOwner()

    }

}