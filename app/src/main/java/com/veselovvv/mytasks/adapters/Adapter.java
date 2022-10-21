package com.veselovvv.mytasks.adapters;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import com.veselovvv.mytasks.App;
import com.veselovvv.mytasks.R;
import com.veselovvv.mytasks.activities.TaskDetailsActivity;
import com.veselovvv.mytasks.models.Task;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder> {
    private final SortedList<Task> sortedList;

    public Adapter() {
        sortedList = new SortedList<>(Task.class, new SortedList.Callback<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (!o2.done && o1.done) { return 1; }
                if (o2.done && !o1.done) { return -1; }
                
                return (int) (o2.timestamp - o1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Task oldItem, Task newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Task item1, Task item2) {
                return item1.uid == item2.uid;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() { return sortedList.size(); }

    public void setItems(List<Task> tasks) { sortedList.replaceAll(tasks); }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskTextView;
        private final CheckBox completedCheckBox;
        private Task task;
        private boolean silentUpdate;

        public TaskViewHolder(@NonNull final View itemView) {
            super(itemView);

            taskTextView = itemView.findViewById(R.id.task_text);
            completedCheckBox = itemView.findViewById(R.id.completed);
            View deleteView = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskDetailsActivity.start((Activity) itemView.getContext(), task);
                }
            });

            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getTaskDao().delete(task);
                }
            });

            completedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (!silentUpdate) {
                        task.done = checked;
                        App.getInstance().getTaskDao().update(task);
                    }

                    updateStrokeOut();
                }
            });
        }

        public void bind(Task task) {
            this.task = task;
            taskTextView.setText(task.text);
            updateStrokeOut();

            silentUpdate = true;
            completedCheckBox.setChecked(task.done);
            silentUpdate = false;
        }

        public void updateStrokeOut() {
            if (task.done)
                taskTextView.setPaintFlags(taskTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            else
                taskTextView.setPaintFlags(taskTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
