package com.changs.roomdemo.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.changs.roomdemo.Product
import com.changs.roomdemo.ProductRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository = ProductRepository(application)
    private val allProducts: LiveData<List<Product>>?
    private val searchResults: MutableLiveData<List<Product>>

    init {
        allProducts = repository.allProducts
        searchResults = repository.searchResults
    }

    fun insertProduct(product: Product) {
        repository.insertProduct(product)
    }

    fun findProduct(name: String) {
        repository.findProduct(name)
    }

    fun deleteProduct(name: String) {
        repository.deleteProduct(name)
    }

    fun getSearchResults(): MutableLiveData<List<Product>> {
        return searchResults
    }

    fun getAllProducts(): LiveData<List<Product>>? {
        return allProducts
    }
}

/*ViewModel에서는 리포지터리 인스턴스를 생성하고 사용자 인터페이스를 데이터베이스 데이터와 동기화하는 데
* 필요한 함수와 LiveData 객체를 제공해야 한다. ProductRepository.kt 파일에 구현되었듯이,
* 리포지터리 클래스의 생성자에는 Room 데이터베이스 인스턴스 참조를 얻을 수 있도록 애플리케이션 컨텍스트를 사용해야 한다.
* 따라서 우리의 ViewModel에서 애플리케이션 컨택스트를 사용할 수 있게 하려면, androidx.lifecycle.ViewModel 클래스
* 가 아닌 androidx.lifecycle.AndroidViewModel 클래스의 서브 클래스가 되도록 수정해야 한다.
* MainViewModel 클래스에서는 repository 속성이 초기화될 때 리포지터리 인스턴스가 생성되어 이것의 참조가
* repository 속성에 저장된다. 그리고 init 초기화 블럭에서 이 속성을 사용해서 데이터베이스 쿼리 결과 참조와
* LiveData 객체의 참조를 얻는다. 이 참조를 UI 컨트롤러의 옵저버가 알 수 있게 하기 위해서다.
* 이제는 ViewModel에 함수를 추가해야 한다. 이러한 함수는 사용자 인터페이스의 버튼을 눌렀을 때 그리고 LiveData 객체
* 옵저버가 설정되었을 때 UI 컨트롤러에서 호출될 것이다.*/