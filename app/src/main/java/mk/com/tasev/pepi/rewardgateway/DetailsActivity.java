package mk.com.tasev.pepi.rewardgateway;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mk.com.tasev.pepi.rewardgateway.model.Employee;
import mk.com.tasev.pepi.rewardgateway.utils.Utils;

/**
 * Created by ptasev on 3/14/18.
 */

public class DetailsActivity extends Activity {

    public static final String EmployeeKey = "EmployeeKey";

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvCompany)
    TextView tvCompany;

    @BindView(R.id.tvBiography)
    TextView tvBiography;

    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;

    private Employee employee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        if (getIntent() == null)
            return;
        if (!getIntent().hasExtra(EmployeeKey))
            return;
        employee = getIntent().getParcelableExtra(EmployeeKey);
        tvName.setText(employee.getName());
        tvTitle.setText(employee.getTitle());
        tvCompany.setText(employee.getCompany());
        tvBiography.setText(Utils.getHtmlFormattedString(employee.getBio()));
        Picasso.get().load(employee.getAvatar()).into(ivAvatar);
    }
}
