package momocorp.partybus.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import momocorp.partybus.R;
import momocorp.partybus.TouchListeners.EventSelectionListener;


/**
 * Created by Pablo on 9/16/2016.
 */
public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {
    Context mContext;
    String[] event_types;
    public InterestAdapter(Context context){
        this.mContext = context;

    }

    @Override
    public InterestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.interest_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InterestAdapter.ViewHolder holder, int position) {
        holder.toolbar.setTitle(event_types[position]);
        holder.selectionImage.setOnClickListener(new EventSelectionListener(position, holder.selectionImage));




    }

    @Override
    public int getItemCount() {
        Resources resources = mContext.getResources();

        event_types = resources.getStringArray(R.array.event_types);

        return event_types.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView eventImage;
       public  Toolbar toolbar;
        public ImageView selectionImage;

        public ViewHolder(View itemView) {
            super(itemView);
            eventImage = (ImageView) itemView.findViewById(R.id.interest_image);
            toolbar = (Toolbar) itemView.findViewById(R.id.interest_toolbar);
            selectionImage = (ImageView) itemView.findViewById(R.id.selection_image);



        }

    }
}
