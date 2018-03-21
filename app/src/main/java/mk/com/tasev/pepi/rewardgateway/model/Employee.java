package mk.com.tasev.pepi.rewardgateway.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ptasev on 3/12/18.
 */

public class Employee implements Parcelable {
    private String uuid;
    private String company;
    private String bio;
    private String name;
    private String title;
    private String avatar;

    public Employee() {
    }

    public Employee(String uuid, String company, String bio, String name, String title, String avatar) {
        this.uuid = uuid;
        this.company = company;
        this.bio = bio;
        this.name = name;
        this.title = title;
        this.avatar = avatar;
    }

    protected Employee(Parcel in) {
        uuid = in.readString();
        company = in.readString();
        bio = in.readString();
        name = in.readString();
        title = in.readString();
        avatar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(company);
        dest.writeString(bio);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
