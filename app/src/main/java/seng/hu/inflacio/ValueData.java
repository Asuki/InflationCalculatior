package seng.hu.inflacio;

import android.os.Parcel;
import android.os.Parcelable;


public class ValueData implements Parcelable {
    int oldValue;
    int newValue;
    int newRoundedValue;
    int quantity;
    int newPrice;
    double rate;

    public void setOldValue(int oldValue) {
        this.oldValue = oldValue;
        this.newValue = (int) (oldValue * this.rate);
        this.newRoundedValue = getNewInflation(oldValue);
        this.newPrice = this.quantity * this.newRoundedValue;
    }

    public static final Parcelable.Creator<ValueData> CREATOR = new Parcelable.Creator<ValueData>(){

        @Override
        public ValueData createFromParcel(Parcel in) {
            return new ValueData(in);
        }

        @Override
        public ValueData[] newArray(int size) {
            return new ValueData[size];
        }
    };

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }

    public int getNewRoundedValue() {
        return newRoundedValue;
    }

    public void setNewRoundedValue(int newRoundedValue) {
        this.newRoundedValue = newRoundedValue;
        this.newPrice = newRoundedValue * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.newPrice = this.newValue * this.quantity;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public ValueData(Parcel parcel) {
    }

    public ValueData(int oldValue, int quantity, double rate){
        this.rate = 1 + (rate / 100);
        this.oldValue = oldValue;
        this.quantity = quantity;
        this.newValue = (int) (oldValue * this.rate);
        this.newRoundedValue = getNewInflation(oldValue);
        this.newPrice = this.quantity * this.newRoundedValue;
    }

    private int getNewInflation(int prevValue){
        int result;
        int tmpValue;
        int mod;

        tmpValue = (int) (prevValue * rate);
        mod = tmpValue % 5;

        if (mod == 0)
            result = tmpValue;
        else if (mod <= 2)
            result = tmpValue - mod;
        else
            result = tmpValue + (5 - mod);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(oldValue);
        out.writeInt(newValue);
        out.writeInt(newRoundedValue);
        out.writeInt(quantity);
        out.writeInt(newPrice);
    }

    @Override
    public String toString() {
        return "ValueData{" +
                "oldValue=" + oldValue +
                ", newValue=" + newValue +
                ", newRoundedValue=" + newRoundedValue +
                ", quantity=" + quantity +
                ", newPrice=" + newPrice +
                ", rate=" + rate +
                '}';
    }
}
