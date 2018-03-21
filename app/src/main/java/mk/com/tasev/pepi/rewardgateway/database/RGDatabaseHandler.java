package mk.com.tasev.pepi.rewardgateway.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mk.com.tasev.pepi.rewardgateway.model.Employee;

/**
 * Created by ptasev on 3/12/18.
 */

public class RGDatabaseHandler {

    private static final String SELECT_ALL = "SELECT * FROM " + RGDatabase.TABLE_EMPLOYEES;
    private static final String DELETE_ALL = "DELETE FROM " + RGDatabase.TABLE_EMPLOYEES;

    private SQLiteDatabase database;
    private RGDatabase rgDatabase;

    public RGDatabaseHandler(Context context) {
        rgDatabase = new RGDatabase(context, RGDatabase.DATABASE_VERSION);
    }

    public void finish() {
        closeAll();
    }

    public final void openWritableDatabase() {
        database = rgDatabase.getWritableDatabase();
    }

    public final void openReadableDatabase() {
        database = rgDatabase.getReadableDatabase();
    }

    public final void closeDatabase() {
        try {
            if (database != null && database.isOpen())
                database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void closeAll() {
        try {
            if (database != null && database.isOpen())
                database.close();
            if (rgDatabase != null)
                rgDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveEmployees(List<Employee> employees) {
        openWritableDatabase();
        // Delete all rows, since there is no way to know if some Employee was removed from the server
        database.delete(RGDatabase.TABLE_EMPLOYEES, null, null);
        if (employees == null || employees.size() == 0) {
            closeDatabase();
            return;
        }
        for (int i = 0; i < employees.size(); i++) {
            ContentValues tempContentValues = new ContentValues();
            tempContentValues.put(RGDatabase.COLUMN_UUID, employees.get(i).getUuid());
            tempContentValues.put(RGDatabase.COLUMN_AVATAR, employees.get(i).getAvatar());
            tempContentValues.put(RGDatabase.COLUMN_BIO, employees.get(i).getBio());
            tempContentValues.put(RGDatabase.COLUMN_COMPANY, employees.get(i).getCompany());
            tempContentValues.put(RGDatabase.COLUMN_NAME, employees.get(i).getName());
            tempContentValues.put(RGDatabase.COLUMN_TITLE, employees.get(i).getTitle());
            database.insert(RGDatabase.TABLE_EMPLOYEES, null, tempContentValues);
        }
        closeDatabase();
    }

    public List<Employee> getEmployees() {
        List<Employee> employeeList = null;
        openReadableDatabase();
        Cursor cursor = database.rawQuery(SELECT_ALL, null);
        if (cursor.moveToFirst()) {
            employeeList = new ArrayList<>(cursor.getCount());
            do {
                Employee employee = new Employee();
                employee.setAvatar(cursor.getString(RGDatabase.INDEX_COLUMN_AVATAR));
                employee.setBio(cursor.getString(RGDatabase.INDEX_COLUMN_BIO));
                employee.setCompany(cursor.getString(RGDatabase.INDEX_COLUMN_COMPANY));
                employee.setName(cursor.getString(RGDatabase.INDEX_COLUMN_NAME));
                employee.setTitle(cursor.getString(RGDatabase.INDEX_COLUMN_TITLE));
                employee.setUuid(cursor.getString(RGDatabase.INDEX_COLUMN_UUID));
                employeeList.add(employee);
            }
            while (cursor.moveToNext());
        }
        return employeeList;
    }
}
