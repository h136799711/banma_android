package com.itboye.banma.entity;

import org.apache.http.conn.ssl.PrivateKeyStrategy;

import android.os.Parcel;
import android.os.Parcelable;

public class YouHuiList implements Parcelable {
	private String discount_ratio;
	public String getDiscount_ratio() {
		return discount_ratio;
	}

	public void setDiscount_ratio(String discount_ratio) {
		this.discount_ratio = discount_ratio;
	}

	public String getCommission_ratio() {
		return commission_ratio;
	}

	public void setCommission_ratio(String commission_ratio) {
		this.commission_ratio = commission_ratio;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStoreuid() {
		return storeuid;
	}

	public void setStoreuid(String storeuid) {
		this.storeuid = storeuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String commission_ratio;
	private String p_id;
	private String uid;
	private String store_id;
	private String storeuid;
	private String name;
	private String remark;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(discount_ratio);
		dest.writeString(commission_ratio);
		dest.writeString(p_id);
		dest.writeString(uid);
		dest.writeString(store_id);
		dest.writeString(storeuid);
		dest.writeString(name);
		dest.writeString(remark);
	}
	
	public static final Parcelable.Creator<YouHuiList> CREATOR = new Creator<YouHuiList>()
    {
        @Override
        public YouHuiList[] newArray(int size)
        {
            return new YouHuiList[size];
        }
        
        @Override
        public YouHuiList createFromParcel(Parcel in)
        {
        	YouHuiList youHuiList=new YouHuiList();
        	youHuiList.discount_ratio = in.readString();
        	youHuiList.commission_ratio = in.readString();
        	youHuiList.p_id = in.readString();
        	youHuiList.uid = in.readString();
        	youHuiList.store_id = in.readString();
        	youHuiList.storeuid = in.readString();
        	youHuiList.name = in.readString();
        	youHuiList.remark = in.readString();
            return youHuiList;
        }
    };

	 public YouHuiList()
	    {
		 
	    }
}
