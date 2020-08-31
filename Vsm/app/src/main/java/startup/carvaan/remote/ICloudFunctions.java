package startup.carvaan.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import startup.carvaan.Constructor.CashFreeToken;


public interface ICloudFunctions {
    @GET("token")
    Observable<CashFreeToken> getToken(@Query("orderId") String orderId, @Query("orderAmount") String orderAmount);
}
