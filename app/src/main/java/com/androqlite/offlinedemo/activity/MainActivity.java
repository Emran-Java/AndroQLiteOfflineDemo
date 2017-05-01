package com.androqlite.offlinedemo.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.androqlite.offlinedemo.R;
import com.androqlite.offlinedemo.adapter.MessageAdapter;
import com.androqlite.offlinedemo.datamodels.MessageDataModel;
import com.androqlite.offlinedemo.networkoperation.VolleyController;
import com.androqlite.offlinedemo.sqlite.DbConstants;
import com.androqlite.offlinedemo.sqlite.DbController;
import com.androqlite.offlinedemo.utility.DataParser;
import com.androqlite.offlinedemo.utility.NetworkUtility;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*for application*/
    private Context mContext;
    private Activity mActivity;

    /*for server*/
    final private String HOST = "192.169.0.105";
    final private String SERVER_PATH = "/emran/androQLiteBackend/";
    final private String API_SEND_MESSAGE = "insertValue.php";

    private String mAppServerUrl = "http://" + HOST + SERVER_PATH;
    final static public String[] INSERT_PARAM_KEY = {"title", "message"};

    //Get all Data
    final private String API_GET_ALL_MESSAGE = "getAll.php";
    final private String JSON_INDEX_GET_ALL_MESSAGE = "getall";
    final static public String[] GETALL_DATA_PARAM_KEY = {"title", "message", "date_time"};

    /*layout elements*/
    private Button btnSend;
    private EditText edtTxtTitle, edtTxtMsg;
    /*ForProgressUnit*/
    private RelativeLayout rlvLoutProgress;
    private TextView tvProgressTxt;

    /*Data list*/
    private LinearLayout mLinLstNavUser;
    private RecyclerView mRecycleVDrawerMenu;
    private GridLayoutManager mLayoutManager;
    private RecyclerView.Adapter mDataAdapter;
    private ArrayList<?> mResponseData;


    /*others variables*/
    private String mTitle, mMessage;

    /*###################################*/
    /*#### Activity Override Methods ####*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInitialValue();
        setLayoutView();
        setListener();
        if (NetworkUtility.isNetworkAvailable(mContext)) {
            loadOnlineMessage();
        } else {
            loadOfflineMessage();
        }

    }
    /*------------------------------------------------------------------*/

    /*##################################*/
    /*#### Activity Helping Methods ####*/
    private void setInitialValue() {
        mContext = this;
        mActivity = this;
    }

    private void setLayoutView() {
        setContentView(R.layout.activity_main);

        btnSend = (Button) findViewById(R.id.btnSend);
        edtTxtTitle = (EditText) findViewById(R.id.edtTxtTitle);
        edtTxtMsg = (EditText) findViewById(R.id.edtTxtMsg);

        rlvLoutProgress = (RelativeLayout) findViewById(R.id.rlvLoutProgress);
        tvProgressTxt = (TextView) findViewById(R.id.tvProgressTxt);

        mRecycleVDrawerMenu = (RecyclerView) findViewById(R.id.rvList);
        mLayoutManager = new GridLayoutManager(mActivity, 1);
        mRecycleVDrawerMenu.setLayoutManager(mLayoutManager);
        mResponseData = new ArrayList<>();
        mDataAdapter = new MessageAdapter(mContext, (ArrayList<MessageDataModel>) mResponseData);
        mRecycleVDrawerMenu.setAdapter(mDataAdapter);

    }

    private void setListener() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMessageInApp();
                if (NetworkUtility.isNetworkAvailable(mContext)) {
                    sendToServer();
                }
                loadOfflineMessage();
            }
        });

        ((MessageAdapter) mDataAdapter).setOnItemClickListener(new MessageAdapter.ListItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(mContext, mResponseData.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*------------------------------------------------------------------*/


    /*#####################*/
    /*#### Manage data ####*/

    /*Online methods*/
    private void sendToServer() {
        if (getValue()) {
            if (NetworkUtility.isNetworkAvailable(mContext)) {
                showLoader(mContext.getString(R.string.pleaseWait));
                String[] values = {mTitle, mMessage};
                VolleyController volleyController = new VolleyController(mContext);
                volleyController.setServerIngo(mAppServerUrl + API_SEND_MESSAGE, Request.Method.POST);
                volleyController.sendValueByVolley(INSERT_PARAM_KEY, values);
                volleyController.getServerResponse(new VolleyController.OnResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        hideLoader();
                        Log.d("response", "serverResponse: " + response);
                    }

                    @Override
                    public void onErrorResponse(String response) {
                        hideLoader();
                        Log.d("response", "serverResponse: " + response);
                    }
                });
            } else {
                Toast.makeText(mContext, mContext.getString(R.string.noInternet), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.enterAllValues), Toast.LENGTH_LONG).show();
        }
    }

    private void loadOnlineMessage() {
        if (NetworkUtility.isNetworkAvailable(mContext)) {
            showLoader(mContext.getString(R.string.pleaseWait));
            String[] values = {"", ""};
            VolleyController volleyController = new VolleyController(mContext);
            volleyController.setServerIngo(mAppServerUrl + API_GET_ALL_MESSAGE, Request.Method.POST);
            volleyController.sendValueByVolley(INSERT_PARAM_KEY, values);
            volleyController.getServerResponse(new VolleyController.OnResponseListener() {
                @Override
                public void onResponse(String response) {
                    messageDataParsing(response);
                    hideLoader();


                    Log.d("response", "serverResponse: " + response);
                }

                @Override
                public void onErrorResponse(String response) {
                    hideLoader();
                    Log.d("response", "serverResponse: " + response);
                }
            });
        }
    }
    /*-----*/

    /*Offline Methods*/
    private void saveMessageInApp() {
        if (getValue()) {
            MessageDataModel messageDataModel = new MessageDataModel(mTitle, mMessage, "", "mobile");
            DbController dbController = new DbController(mContext);
            int count = dbController.insertMessageData(messageDataModel);
            if (count > 0) {
                Toast.makeText(mContext, "Save in offline", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadOfflineMessage() {

        DbController dbController = new DbController(mContext);

        ArrayList<MessageDataModel> messageDataModels = dbController.getAllMessageData();

        if (messageDataModels != null && messageDataModels.size() > 0) {
            if (mResponseData != null && mResponseData.size() > 0) {
                mResponseData.clear();
            }
            ((ArrayList<MessageDataModel>) mResponseData).addAll(messageDataModels);
            if (mDataAdapter != null) {
                mDataAdapter.notifyDataSetChanged();
            }
        }
        dbController.closeDB();
        Toast.makeText(mContext, "Load offline data", Toast.LENGTH_SHORT).show();
    }

    public void messageDataParsing(String messageInList) {

        DataParser dataParser = new DataParser();
        if (messageInList != null) {
            //Parse value
            ArrayList<String[]> values = dataParser.getCoverData(messageInList, JSON_INDEX_GET_ALL_MESSAGE, GETALL_DATA_PARAM_KEY);
            ArrayList<MessageDataModel> messageDataModels = new ArrayList<>();

            DbController dbController = new DbController(mContext);
            dbController.deleteTbls(DbConstants.TABLE_MESSAGE);

            for (int i = 0; i < values.size(); i++) {
                MessageDataModel messageDataModel = new MessageDataModel(values.get(i));
                messageDataModel.setmMFrom("server");
                messageDataModels.add(messageDataModel);
                //insert in SQLite DB
                dbController.insertMessageData(messageDataModel);
            }
            dbController.closeDB();
            if (messageDataModels.size() > 0) {
                //Set adapter on view
                if (mResponseData != null && mResponseData.size() > 0) {
                    mResponseData.clear();
                }
                ((ArrayList<MessageDataModel>) mResponseData).addAll(messageDataModels);
                if (mDataAdapter != null) {
                    mDataAdapter.notifyDataSetChanged();
                }
            }
        }

    }
    /*------------------------------------------------------------------*/


    /*########################*/
    /*#### Helper methods ####*/
    public boolean getValue() {

        if (edtTxtTitle == null || edtTxtMsg == null) {
            return false;
        }

        mTitle = edtTxtTitle.getText().toString().trim();
        mMessage = edtTxtMsg.getText().toString().trim();

        if (mTitle.isEmpty() || mMessage.isEmpty()) {
            return false;
        }
        return true;
    }

    public void showLoader(String msg) {
        rlvLoutProgress.setVisibility(View.VISIBLE);
        tvProgressTxt.setText(msg);
    }

    public void hideLoader() {
        rlvLoutProgress.setVisibility(View.GONE);
        tvProgressTxt.setText("");
    }
    /*------------------------------------------------------------------*/
}
