package mk.com.tasev.pepi.rewardgateway.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mk.com.tasev.pepi.rewardgateway.DetailsActivity;
import mk.com.tasev.pepi.rewardgateway.R;
import mk.com.tasev.pepi.rewardgateway.model.Employee;

/**
 * Created by ptasev on 3/12/18.
 */

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Employee> employees;

    public EmployeesAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
        inflater = LayoutInflater.from(this.context);
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, final int position) {
        holder.getTvName().setText(employees.get(position).getName());
        holder.getTvCompany().setText(employees.get(position).getCompany());
        Picasso.get().load(employees.get(position).getAvatar()).into(holder.getIvAvatar());
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(context, DetailsActivity.class);
                detailsIntent.putExtra(DetailsActivity.EmployeeKey, employees.get(position));
                context.startActivity(detailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (employees != null)
            return employees.size();
        return 0;
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private TextView tvName;
        private TextView tvCompany;
        private ImageView ivAvatar;

        public EmployeeViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            tvName = this.rootView.findViewById(R.id.tvName);
            tvCompany = this.rootView.findViewById(R.id.tvCompany);
            ivAvatar = this.rootView.findViewById(R.id.ivAvatar);
        }

        public View getRootView() {
            return rootView;
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvCompany() {
            return tvCompany;
        }

        public ImageView getIvAvatar() {
            return ivAvatar;
        }
    }
}
