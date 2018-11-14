package com.example.administrator.wanandroid.http


import com.example.administrator.wanandroid.R.id.page
import com.example.administrator.wanandroid.bean.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author  : Alex
 * @date    : 2018/08/31
 * @version : V 2.0.0
 */
const val BASE_URL = "http://www.wanandroid.com/"

interface Api {

    @FormUrlEncoded
    @POST("user/login/")
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<LoginBean>

    @FormUrlEncoded
    @POST("user/register/")
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") repassword: String): Observable<LoginBean>


    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * @param page
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<HttpResponse<ArticleResponseBody>>

    /**
     * 获取文章列表
     * http://www.wanandroid.com/lg/collect/5865/json
     * @param page
     */
    @POST("lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Observable<Bean>

    @POST("lg/uncollect_originId/{originId}/json")
    fun cancelCollectArticle(@Path("originId") id: Int): Observable<Bean>


    /**
     * 获取收藏文章的列表
     * http://www.wanandroid.com/lg/collect/list/0/json
     * @param page
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectArticleList(@Path("page") page: Int): Observable<HttpResponse<CollectArticleResponseBody>>

    @GET("tree/json")
    fun getCategories(): Observable<CategoryResponse>

    // 获取Tag文章
    @GET("article/list/{page}/json")
    fun getArticleByTag(@Path("page") page: Int, @Query("cid") cid: Int):Observable<HttpResponse<ArticleResponseBody>>

    @GET("hotkey/json")
    fun getHostTag(): Observable<HotTagBean>

    @POST("article/query/{page}/json")
    fun queryArticle(@Path("page")page:Int,@Query("k") k:String): Observable<HttpResponse<ArticleResponseBody>>




}