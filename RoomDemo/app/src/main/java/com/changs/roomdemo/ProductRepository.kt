package com.changs.roomdemo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class ProductRepository(application: Application) {
    val searchResults = MutableLiveData<List<Product>>()

    private var productDao: ProductDao?
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val allProducts: LiveData<List<Product>>?

    init {
        val db: ProductRoomDatabase? =
            ProductRoomDatabase.getDatabase(application)
        productDao = db?.productDao()
        allProducts = productDao?.getAllProducts()
    }

    fun insertProduct(newproduct: Product) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsert(newproduct)
        }
    }

    private suspend fun asyncInsert(product: Product) {
        productDao?.insertProduct(product)
    }

    fun deleteProduct(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncDelete(name)
        }
    }

    private suspend fun asyncDelete(name: String) {
        productDao?.deleteProduct(name)
    }

    fun findProduct(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    private suspend fun asyncFind(name: String): Deferred<List<Product>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async productDao?.findProduct(name)
        }
}

/*리포지터리 클래스인 ProductRepository는 ViewModel을 대신하여 Room 데이터베이스와 상호작용하는 책임을 갖는다.
* 그리고 DAO를 사용해서 제품 레코드를 추가, 삭제, 조회하는 함수를 제공해야 한다.
* 이때 LiveDate 객체를 반환하는 getAllProducts() DAO 함수를 제외한 나머지 함수는
* 메인 스레드와 별개의 스레드에서 실행되어야 한다.
* 이 코드에서는 searchResult라는 이름으로 MutableLiveData 변수를 선언한다.
* MutableLiveData에는 비동기로 실행되는 데이터베이스 조회 작업이 끝날 때마다 그 결과가 저장된다.
* 데이터베이스 작업을 시작시키기 위해 ViewModel에서 호출할 수 있는 함수를 ProductRepository 클래스에 추가해야 한다.
* 그러나 이렇게 하려면 ProductRepository 클래스에서 ProductRoomDatabase 인스턴스를 통해 DAO 참조를 얻어야 한다.
*
* 메인 스레드와 별개의 스레드에서 데이터베이스 작업을 수행하기 위해 이 리퍼지터리에서는 코틀린의 코루틴을 사용할 것이다.
* 따라서 코루틴 관련 라이브러리를 프로젝트에 추가해야 한다.
*
* 이 코드에서는 데이터 추가와 삭제 작업을 위해 각각 두 개의 함수를 추가하였다. 하나는 일반 함수이고 다른 것은
* 코틀린 코루틴(suspend) 함수다. 그리고 일반 함수에서는 정지 함수를 호출하여 메인 스레드와 별개의 스레드에서
* 코루틴이 실행되게 한다.(여기서는 데이터베이스 작업을 하므로 IO디스패처를 사용한다.) 데이터베이스 작업을 하는 동안
* 앱의 실행을 방해하지 않기 위함이다. 데이터 조회 작업의 경우는 findProduct() 함수에 조회 결과를 반환하기 위해
* asyncFind() 정지 함수에서 Deferred 객체를 사용한다.(Deferred 객체는 향후 언젠가 값을 제공한다는 약속을 나타낸다)
* 그리고 findProduct() 함수에서는 Deferred 객체의 await() 함수를 호출하여 코루틴에서 메인 스레드로 값이 반환될 때
* searchResults 변수로 받는다.
*
* 리포지터리 클래스를 완성하기 위해 마지막으로 한 가지 남은 것이 있다. 사용자 인터페이스 레이아웃의 RecyclerView에서는
* 데이터베이스에 저장된 최신 제품 데이터를 반영하여 제품 리스트를 보여 줄 수 있어야 한다.
* SQL 쿼리를 사용하여 데이터베이스의 모든 레코드를 조회하고 LiveData 객체로 반환하는 getAllProduct() 함수는
* ProductDao 클래스에 이미 포함되어 있다. 따라서 리포지터리 클래스에서는 인스턴스 초기화 시에 이 함수를 한 번
* 호출하여 그 결과를 ViewModel 그리고 UI 컨트롤러에서 관찰할 수 있는 LiveData 객체에 저장하면 된다. 이렇게 하면
* 데이터베이스 테이블의 데이터가 변경될 때 마다 UI 컨트롤러의 옵저버가 알게 되어 최신 제품 데이터로
* RecyclerView가 업데이트될 수 있다.
* */