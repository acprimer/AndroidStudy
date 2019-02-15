package com.xueleme.retrofit;

import io.reactivex.Single;
import retrofit2.http.POST;

/*
 * Created by yaodh on 2019/2/15.
 */
public interface Api {
    @POST("api/ad/strategy")
    Single<BaseModel<StrategyData>> getStrategy();
}
