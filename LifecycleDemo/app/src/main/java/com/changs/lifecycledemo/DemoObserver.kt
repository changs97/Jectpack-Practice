package com.changs.lifecycledemo

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

//MainFragment 인스턴스를 관찰할 수 있는 새로운 클래스 추가
//LifecycleObserver 인터페이스를 구현하도록 상속
/*다음으로 할 일은 생명주기 함수를 추가하고 이 함수를 생명주기 이벤트 핸들러로 지정하는 것이다.
* 여기서는 모든 이벤트를 처리하고 이벤트 타입을 보여 주는 로그 메시지를 출력할 것이다.
* 프래그먼ㅌ으의 현재 상태와 관련된 이벤트를 추적하기 위해 ON_ANY 이벤트 핸들러도 추가할 것이다.
* 이 이벤트가 생길 때 호출되는 onAny 함수에는 생명주기 소유자가 인자로 전달되므로
* 현재 상태를 얻는 코드를 추가할 수 있다.*/
class DemoObserver : LifecycleObserver {
    private val LOG_TAG = "DemoObserver"

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.i(LOG_TAG, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.i(LOG_TAG, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.i(LOG_TAG, "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.i(LOG_TAG, "onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.i(LOG_TAG, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.i(LOG_TAG, "onDestroy")
    }


}