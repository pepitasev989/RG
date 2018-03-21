package mk.com.tasev.pepi.rewardgateway.connection;

import java.util.List;

import mk.com.tasev.pepi.rewardgateway.model.Employee;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by ptasev on 3/14/18.
 */

public interface ApiInterface {
    @GET("list")
    Call<List<Employee>> getEmployees(@Header("Authorization") String authKey);
}
