package com.example.shreyas.newdemo;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public ExpandableListAdapter(Context context, List<String> expandableListTitle,
                                 HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);

            if(listPosition==0)
            {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
                TextView expandedListTextView = (TextView) convertView
                        .findViewById(R.id.expandedListItem);
                expandedListTextView.setText(expandedListText);
                Log.d("position", listPosition + " " + expandedListPosition);
            }
            else if(listPosition==1)
            {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
                TextView expandedListTextView = (TextView) convertView
                        .findViewById(R.id.expandedListItem);
                expandedListTextView.setText(expandedListText);
                Log.d("position", listPosition + " " + expandedListPosition);
            }
            else if(listPosition==2 )
            {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
                TextView expandedListTextView = (TextView) convertView
                        .findViewById(R.id.expandedListItem);
                expandedListTextView.setText(expandedListText);
                Log.d("position", listPosition + " " + expandedListPosition);
            }
            else if(listPosition==3 && expandedListPosition==0)
            {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item1, null);

                TextView t1 = (TextView) convertView.findViewById(R.id.t1);
                TextView t2 = (TextView) convertView.findViewById(R.id.t2);
                TextView t3 = (TextView) convertView.findViewById(R.id.t3);
                TextView t4 = (TextView) convertView.findViewById(R.id.t4);
                TextView t5 = (TextView) convertView.findViewById(R.id.t5);
                TextView t6 = (TextView) convertView.findViewById(R.id.t6);

                t1.setText("Temp");
                t2.setText("Humidity");

                t3.setText("expandedListText");
                t4.setText(expandedListText);

                t5.setText(expandedListText);
                t6.setText(expandedListText);


            }


        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}