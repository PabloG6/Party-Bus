package momocorp.partybus.TouchListeners;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import momocorp.partybus.R;

/**
 * Created by Pablo on 9/24/2016.
 */
public class EventSelectionListener implements View.OnClickListener {
    int position;
    ImageView selectionImage;

    //// TODO: 9/24/2016 create a better matching algorithm to match the cards
    public EventSelectionListener(int position, ImageView selectionImage){
        this.position = position;
        this.selectionImage = selectionImage;

    }

    @Override
    public void onClick(View view) {
        selectionImage.setImageResource(R.drawable.ic_done_black_24dp);

    }
}
