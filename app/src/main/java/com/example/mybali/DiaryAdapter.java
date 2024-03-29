package com.example.mybali;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.example.mybali.RoomDataBase.Diary;
import com.example.mybali.RoomDataBase.DiaryDatabase;

import java.text.DateFormat;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder>{

    private Activity activity;
    private Context context;
    private List<Diary> diary;
    private OnItemClickListener onItemClickListener;


    public DiaryAdapter(Context context,List<Diary> diary){
        this.context = context;
        this.diary = diary;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
        }
    }

//================================================================================================//
    //更新資料
    public void refreshView(){
        new Thread(()->{
            List<Diary> data = DiaryDatabase.getInstance(activity).getDiaryDao().displayAll();
            this.diary = data;
            activity.runOnUiThread(()->{
                notifyDataSetChanged();
            });
        }).start();
    }

    //刪除資料
//    public void deleteData(int position){
//        new Thread(()->{
//           DiaryDatabase.getInstance(activity).getDiaryDao().deleteData(diary.get(position).getId());
//           activity.runOnUiThread(()->{
//               notifyItemRemoved(position);
//               refreshView();
//           });
//        }).start();
//    }
//================================================================================================//

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_diary,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.titleOutput.setText(diary.get(position).getTitle());
        holder.descriptionOutput.setText(diary.get(position).getDescription());

        //轉換時間
        String formatTime = DateFormat.getDateTimeInstance().format(diary.get(position).getCreatedTime());
        holder.timeOutput.setText(formatTime);
//
        holder.itemView.setOnClickListener((v)->{
//            onItemClickListener.onItemClick(diary.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return diary.size();
    }

//================================================================================================//
    //建立對外接口
    public interface OnItemClickListener{
        void onItemClick(Diary diary);
    }

}
