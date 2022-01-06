package com.changs.viewmodeldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.changs.viewmodeldemo.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        /*FrameLayout 컨테이너는 플레이스 홀더의 역할만 한다. 따라서 앱이 시작되면 첫 번째 화면의 콘텐츠로 교체된다.
        * 앱이 최초 시작될 때 컨테이너의 id를 MainFragment 클래스로 교체하는 것을 알 수 있다*/
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}