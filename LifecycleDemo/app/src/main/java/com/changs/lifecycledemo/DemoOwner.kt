package com.changs.lifecycledemo

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

//커스텀 생명주기 소유자 클래스 이 클래스에서 이벤트를 발생시키고 생명주기 상태를 변경하는 방법을 알아본다.
/*DemoOwner 클래스는 자신의 인스턴스 참조로 최화된 LifecycleRegistry 인스턴스가 필요하다.
* 또한, LifecycleRegistry 인스턴스를 반환하는 getLifecycle() 함수도 있어야 한다 LifecycleResistry 인스턴스 참조를
* 저장하는 변수를 선언하고 초기화하는 코드와 getLifecycle() 함수를 추가하자. */
class DemoOwner : LifecycleOwner {
    private val lifecycleRegistry : LifecycleRegistry

    init {
        lifecycleRegistry = LifecycleRegistry(this)
        //DemoObserver를 옵저버로 추가
        lifecycle.addObserver(DemoObserver())
    }
    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    /*그 다음에 이 클래스는 자신의 생명주기 상태 변화를 알려 주어야 한다. 이것은 LifecycleRegistry 객체의
    * markState() 함수를 호출하거나, 또는 handleLifecycleEvent() 함수를 사용해서 생명주기 이벤트를
    * 발생시키면 된다. 커스텀 클래스에 어떤 상태 변화를 구성할 것인지는 해당 클래스의 목적에 달려있다.
    * 여기서는 호출될 때 단순히 생멍주기 이벤트를 발생시키는 함수를 추가할 것이다.*/
     fun startOwner() {
         lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
     }

    fun stopOwner() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

}