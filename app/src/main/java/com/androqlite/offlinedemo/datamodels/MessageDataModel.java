package com.androqlite.offlinedemo.datamodels;

	/*##########################################*/
    /*########## Data Model for Message ########*/
	/*##########################################*/

public class MessageDataModel {
    private String mMTitle;
    private String mMMessage;
    private String mMDateTime;
    private String mMFrom;

    public MessageDataModel(
            String mTitle, String mMessage, String mDateTime, String mFrom) {
        this.mMTitle = mTitle;
        this.mMMessage = mMessage;
        this.mMDateTime = mDateTime;
        this.mMFrom = mFrom;
    }

    public MessageDataModel(String[] values) {
        if(values!=null){
            switch (values.length){
                case 3:{
                    this.mMDateTime = values[2];
                }
                case 2:{
                    this.mMMessage = values[1];
                }
                case 1:{
                    this.mMTitle = values[0];
                }
                default:{}
            }
        }
    }

    public String getMTitle() {
        return mMTitle;
    }

    public String getMMessage() {
        return mMMessage;
    }

    public String getMDateTime() {
        return mMDateTime;
    }

    public String getMFrom() {
        return mMFrom;
    }

    public void setmMFrom(String mMFrom) {
        this.mMFrom = mMFrom;
    }

    @Override
    public String toString() {
        return "MessageDataModel{" +
                "mMTitle='" + mMTitle + '\'' +
                ", mMMessage='" + mMMessage + '\'' +
                ", mMDateTime='" + mMDateTime + '\'' +
                ", mMFrom='" + mMFrom + '\'' +
                '}';
    }
}
