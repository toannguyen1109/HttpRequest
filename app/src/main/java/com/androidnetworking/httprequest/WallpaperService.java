package com.androidnetworking.httprequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WallpaperService {

    //lay danh sach chuyen muc
    //page la so trang bat dau tu 1
    //per_page la so phan tu muon lay

    @GET("wp-json/wp/v2/categories")
    Call<String> getCategories(@Query("page") String page, @Query("per_page") String per_page);
}
