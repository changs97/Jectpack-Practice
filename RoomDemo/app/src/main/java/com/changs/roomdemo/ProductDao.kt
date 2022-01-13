package com.changs.roomdemo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: Product)

    @Query("SELECT * FROM products WHERE productName = :name")
    fun findProduct(name: String): List<Product>

    @Query("DELETE FROM products WHERE productName = :name")
    fun deleteProduct(name: String)

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>
}
/*DAO에서 products 데이터베이스 테이블의 레코드를 추가, 조회, 삭제하는 함수를 구현한다.
* 추가 함수에서는 저장될 데이터를 포함하는 Product 엔터티 객체를 인자로 받는 반면,
* 조회와 삭제 함수에서는 SQL을 실행할 제품명을 포함하는 문자열을 인자로 받는다.
* getAllProducts() 함수는 데이터베이스의 모든 레코드를 포함하는 LiveData 객체를 반환한다. 이 함수는
* 사용자 인터페이스 레이아웃의 RecyclerView 제품 리스트를 데이터베이스와 동기화하는 데 사용될 것이다.*/