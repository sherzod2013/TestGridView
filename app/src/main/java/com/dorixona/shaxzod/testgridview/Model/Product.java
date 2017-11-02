package com.dorixona.shaxzod.testgridview.Model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shaxzod on 17.07.2017.
 */

public class Product  implements Parcelable {
    private int id;
    private String name;
    private String info;
    private int count_view;
    private String cost;
    private String currency;
    private int vip;
    private String orientation;
    private int company;
    private String address;
    private boolean admin_check;
    private int user_id;
    private int region_id;
    private int category_id;
    private int image_id;
    private String created_at;
    private String updated_at;
    private Bitmap imageBitmap;

    public Product(Parcel source) {
        this.id = source.readInt();
        this.name = source.readString();
        this.info = source.readString();
        this. count_view = source.readInt();
        this.cost = source.readString();
        this.currency = source.readString();
        this.vip = source.readInt();
        this.orientation = source.readString();
        this.company = source.readInt();
        this.address = source.readString();
        this.admin_check = source.readByte() != 0;
        this.user_id = source.readInt();
        this.region_id = source.readInt();
        this.category_id = source.readInt();
        this.image_id = source.readInt();
        this.created_at = source.readString();
        this.updated_at = source.readString();
    }

    public int describeContents() {
        return this.hashCode();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.info);
        dest.writeInt(this.count_view);
        dest.writeString(this.cost);
        dest.writeString(this.currency);
        dest.writeInt(this.vip);
        dest.writeString(this.orientation);
        dest.writeInt(this.company);
        dest.writeString(this.address);
        dest.writeByte((byte) (this.admin_check ? 1 : 0));
        dest.writeInt(this.user_id);
        dest.writeInt(this.region_id);
        dest.writeInt(this.category_id);
        dest.writeInt(this.image_id);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
    }

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCount_view() {
        return count_view;
    }

    public void setCount_view(int count_view) {
        this.count_view = count_view;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int isVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public int isCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAdmin_check() {
        return admin_check;
    }

    public void setAdmin_check(boolean admin_check) {
        this.admin_check = admin_check;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
