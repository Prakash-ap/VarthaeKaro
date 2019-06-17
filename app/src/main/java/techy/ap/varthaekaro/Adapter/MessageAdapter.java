package techy.ap.varthaekaro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import techy.ap.varthaekaro.MessageActivity;
import techy.ap.varthaekaro.Model.Chat;
import techy.ap.varthaekaro.Model.User;
import techy.ap.varthaekaro.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    private Context context;
    private List<Chat> chats;
    private String imageUrl;

    FirebaseUser firebaseUser;


    public MessageAdapter(Context context, List<Chat> chat,String imageUrls) {
        this.context = context;
        this.chats = chat;
        this.imageUrl=imageUrls;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_TYPE_RIGHT) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(v);
        }else{


            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(v);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {

        Chat chat=chats.get(i);
        viewHolder.showmessage.setText(chat.getMessage());

        if(imageUrl.equals("default")){
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(imageUrl).into(viewHolder.imageView);
        }

    }



    @Override
    public int getItemCount() {
        return chats.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView showmessage;
        public ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showmessage=itemView.findViewById(R.id.show_message);
            imageView=itemView.findViewById(R.id.profile_imagelist);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(chats.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
