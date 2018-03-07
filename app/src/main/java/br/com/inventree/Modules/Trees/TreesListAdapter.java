package br.com.inventree.Modules.Trees;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import br.com.inventree.R;
import br.com.inventree.Storage.SQLiteDb.Dao;

public class TreesListAdapter extends ArrayAdapter<Tree> {
    ArrayList<Tree> treesList = new ArrayList<>();
    //private DecimalFormat df;

    public TreesListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Tree> trees) {
        super(context, resource, trees);
        treesList = trees;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        View v = view;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_tree_list, null);


        //df = new DecimalFormat("#.####");
        //df.setRoundingMode(RoundingMode.CEILING);

        TextView tvTreeName = (TextView) v.findViewById(R.id.tv_tree_name);
        TextView tvTreeHeight = (TextView) v.findViewById(R.id.tv_tree_height);
        TextView tvTreeCap = (TextView) v.findViewById(R.id.tv_tree_cap);
        TextView tvTreeWoodVolume = (TextView) v.findViewById(R.id.tv_tree_wood_volume);
        TextView tvTreeDap = (TextView) v.findViewById(R.id.tv_tree_dap);
        TextView tvTreeNotes = (TextView) v.findViewById(R.id.tv_tree_notes);
        tvTreeName.setText(treesList.get(position).getName());
        tvTreeHeight.setText(String.valueOf(treesList.get(position).getHeight()));
        tvTreeCap.setText(String.valueOf(treesList.get(position).getCap()));
        tvTreeWoodVolume.setText(String.valueOf(treesList.get(position).getWoodVolume()));
        tvTreeDap.setText(String.valueOf(treesList.get(position).getDap()));
        tvTreeNotes.setText(treesList.get(position).getNotes());

        Button btEdit = (Button)v.findViewById(R.id.bt_edit);
        Button btDelete = (Button) v.findViewById(R.id.bt_delete);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editTree = new Intent(getContext(), AddTreeActivity.class);
                editTree.putExtra("tree_id", String.valueOf(treesList.get(position).getId()));
                editTree.putExtra("tree_parcel_id", String.valueOf(treesList.get(position).getParcelId()));
                editTree.putExtra("tree_inventory_id", String.valueOf(treesList.get(position).getInventoryId()));
                editTree.putExtra("tree_name", String.valueOf(treesList.get(position).getName()));
                editTree.putExtra("tree_height", String.valueOf(treesList.get(position).getHeight()));
                editTree.putExtra("tree_cap", String.valueOf(treesList.get(position).getCap()));
                editTree.putExtra("tree_dap", String.valueOf(treesList.get(position).getDap()));
                editTree.putExtra("tree_notes", String.valueOf(treesList.get(position).getNotes()));
                v.getContext().startActivity(editTree);
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dao dao = new Dao(getContext());
                dao.deleteTree(treesList.get(position), getContext());
            }
        });

        return v;
    }
}

