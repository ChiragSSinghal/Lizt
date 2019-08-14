package net.atmode.lizt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import net.atmode.lizt.R;
import net.atmode.lizt.entity.Lizt;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.LiztViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context context;
    private List<Lizt> lizts;
    private OnItemClickListener itemClickListener;

    public CustomAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public CustomAdapter.LiztViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.list_row, viewGroup, false);
        LiztViewHolder liztViewHolder = new LiztViewHolder(itemView);
        return liztViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiztViewHolder liztViewHolder, int i) {
        if (lizts != null) {
            Lizt lizt = lizts.get(i);
            liztViewHolder.setData(lizt.getName(), i);
        }
        else {
            liztViewHolder.displayName.setText("No Bills");
        }
    }

    @Override
    public int getItemCount() {
        if (lizts != null) {
            return lizts.size();
        }
        else {
            return 0;
        }
    }

    public void setLizts(List<Lizt> lizts) {
        this.lizts = lizts;
        notifyDataSetChanged();
    }

    public Lizt getLiztAt(int position) {
        return lizts.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Lizt lizt);
    }

    public class LiztViewHolder extends RecyclerView.ViewHolder {

        private TextView displayName;
        private CheckBox checkBox;

        public LiztViewHolder(@NonNull View itemView) {
            super(itemView);

            displayName = itemView.findViewById(R.id.name);
            checkBox = itemView.findViewById(R.id.isCompleteCheck);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(lizts.get(position));
                    }
                }
            });
        }

        public void setData(String name, int position) {
            displayName.setText(name);
        }
    }
}
