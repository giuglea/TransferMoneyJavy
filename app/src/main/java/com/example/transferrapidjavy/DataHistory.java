package com.example.transferrapidjavy;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DataHistory extends ArrayAdapter<DataModel> {

    private Activity context;
    private List<DataModel> dataList;





    public DataHistory(Context context, List<DataModel> dataList){
        super(context, R.layout.list_layout,dataList);

        this.context = (Activity)context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView receiverText = (TextView)listViewItem.findViewById(R.id.listSenderText);
        TextView ammountText = (TextView)listViewItem.findViewById(R.id.listAmountText);
        TextView dateText = (TextView)listViewItem.findViewById(R.id.listTextDate);
        DataModel dataModel;
        Integer n = dataList.size();
        if( (n-position-1) >= 0 ) {
             dataModel = dataList.get(n - position - 1);
        }else{
             dataModel = dataList.get(position);
        }

        receiverText.setText(dataModel.getReceiver().toString());
        ammountText.setText(dataModel.getAmmount().toString()+"€");
        dateText.setText(dataModel.getDate().toString());


        return listViewItem;
    }

}
