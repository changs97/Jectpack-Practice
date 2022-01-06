package com.changs.viewmodeldemo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/*이 클래스에서는 환전에 필요한 변수와 데이터 값을 사용하는 데 필요한 함수를 선언하다. setAmount() 함수는
* 현재의 달러 금액을 인자로 받아서 지역 변수인 dollarText에 저장한다. 그리고 달러 금액 문자열을 부동 소수점으로 변환하고
* 환율을 곱하여 유로 금액을 구한 후 result 변수에 저장한다. 반면에 getResult() 함수는 result 변수에 저장된 값만 반환한다.*/

/*LiveData 컴포넌트는 ViewModel의 데이터 값을 포함한다. LiveData 인스턴스에 포함된 데이터(변수)는
* 앱의 다른 객체(일반적으로 액티비티나 프래그먼트와 같은 UI 컨트롤러)가 관찰할 수 있다.
* 따라서 LiveData의 데이터 값이 변경되면 언제든지 UI 컨트롤러가 통보를 받을 수 있다.*/

/*데이터 변경을 관찰하는 옵저버는 Observer 인터페이스를 구현하는 객체를 생성하며 이 객체는
* LiveData의 데이터 값이 변경될 때 호출되는 onChanged() 함수를 구현한다.
*
* 생성된 Observer 인스턴스는 LiveData의 observe() 함수 호출을 통해 LiveData 인스턴스에 연결된다.
* LiveData 인스턴스는 MutableLiveData 클래스를 사용해서 데이터 변경이 가능하게 선언될 수 있으므로,
* ViewModel과 UI 컨트롤러 모두 LiveData의 데이터 값을 변경할 수 있다.*/

/*달러를 유로로 환전한 결과를 갖는 result 변수를 MutableLiveData 인스턴스에 포함시키고 이 변숫값의 변경을 관찰하는
* 옵저버를 생성할 것이다. 데이터 변경이 가능한 MutableLiveData를 선택한 이유는, 사용자가 버튼을 눌러 환전을 요청할 때마다
* 결과 데이터 값을 변경할 수 있어야 하기 때문이다.*/
class MainViewModel : ViewModel() {
    private val usd_to_eu_rate = 0.74f
    private var dollarText = ""
    //private var result : Float = 0f , 아래 코드로 변경
    private var result : MutableLiveData<Float> = MutableLiveData()

    /*
    fun setAmount(value : String) {
        this.dollarText = value
        result = value.toFloat() * usd_to_eu_rate
    }

    fun getResult() : Float? {
        return result
    }

    이제는 result 변수가 MutableLiveData 인스턴스이므로 위에 두 함수도 수정해야 한다.
    setAmount() 함수의 경우는 result 변수에 값을 지정할 때 단순히 대입 연산자를 사용할 수 없다.
    대신에 환전된 결과값을 인자로 전달하여 LiveData의 setValue() 함수를 호출해야 한다. -> (value로 변경)
    그리고 현재 getResult() 함수는 Float 값을 반환하므로 MutableLiveData 객체를 반환하도록 수정해야 한다.
     */

    fun setAmount(value : String) {
        this.dollarText = value
        result.value = value.toFloat() * usd_to_eu_rate
    }

    fun getResult() : MutableLiveData<Float> {
        return result
    }
}