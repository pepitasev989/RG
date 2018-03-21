package mk.com.tasev.pepi.rewardgateway;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import mk.com.tasev.pepi.rewardgateway.connection.ApiClient;
import mk.com.tasev.pepi.rewardgateway.connection.ApiInterface;
import mk.com.tasev.pepi.rewardgateway.database.RGDatabaseHandler;
import mk.com.tasev.pepi.rewardgateway.model.Employee;
import mk.com.tasev.pepi.rewardgateway.utils.EmployeesAdapter;
import mk.com.tasev.pepi.rewardgateway.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String username = "medium";
    private static final String password = "medium";
    private String authenticationHeader;

    private List<Employee> employees;
    private RecyclerView rvEmployees;
    private EmployeesAdapter employeesAdapter;
    private FloatingActionButton fabRefresh;
    private ProgressBar progressBar;

    private ApiInterface apiService;
    private RGDatabaseHandler databaseHandler;

    private static final int REQUEST_PERMISSION_INTERNET = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvEmployees = findViewById(R.id.rvEmployees);
        rvEmployees.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        databaseHandler = new RGDatabaseHandler(this);

        fabRefresh = findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(fabRefreshListener);

        progressBar = findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

        checkForEmployees();
    }

    private View.OnClickListener fabRefreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkForEmployees();
        }
    };

    private void checkForEmployees() {
        if (authenticationHeader == null)
            authenticationHeader = "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
        if (Utils.checkForPermission(this, Manifest.permission.INTERNET, Manifest.permission.INTERNET + " is mandatory for this app", REQUEST_PERMISSION_INTERNET)) {
            if (Utils.checkForInternetConnection(this))
                requestEmployees();
            else {
                if (employees == null) {
                    employees = databaseHandler.getEmployees();
                    updateEmployeesList();
                }
                if (employees == null || employees.size() == 0) {
                    Utils.showInternetConnectionDialog(MainActivity.this);
                } else {
                    Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_INTERNET:
                requestEmployees();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void requestEmployees() {
        progressBar.setVisibility(View.VISIBLE);
        if (apiService == null)
            apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Employee>> employeesCall = apiService.getEmployees(authenticationHeader);
        employeesCall.enqueue(responseCallback);
    }

    private Callback<List<Employee>> responseCallback = new Callback<List<Employee>>() {
        @Override
        public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
            progressBar.setVisibility(View.GONE);
            try {
                if (response != null) {
                    if (response.body() != null) {
                        employees = response.body();
                        updateEmployeesList();
                        databaseHandler.saveEmployees(employees);
                    } else {// Request error
                    }
                } else {// Request error
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<List<Employee>> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
            t.printStackTrace();
        }
    };

    private void updateEmployeesList() {
        if (employees == null || employees.size() == 0)
            return;
        if (employeesAdapter == null) {
            employeesAdapter = new EmployeesAdapter(MainActivity.this, employees);
            rvEmployees.setAdapter(employeesAdapter);
        } else {
            employeesAdapter.setEmployees(employees);
            employeesAdapter.notifyDataSetChanged();
        }
    }
}
