package com.example.personapiclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonAdapterVH>{
    private List<Person> persons;
    private Context context;
    private ClickedItem clickedItem;

//    public PersonAdapter(ArrayList<Person> persons, MainActivity mainActivity) {
//        this.persons = persons;
//        this.context = mainActivity;
//    }

    //interface for ClickedItem
    public interface ClickedItem
    {
        public void ClickedPerson(Person person);
    }

    public PersonAdapter(ClickedItem clickedItem){
        this.clickedItem = clickedItem;
    }

    public void setData(List<Person> persons)
    {
        this.persons = persons;
        notifyDataSetChanged();
    }

    //inflates the row layout from xml when needed
    @NonNull
    @Override
    public PersonAdapterVH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_persons,parent,false);
        return new PersonAdapterVH(view);
//        return new PersonAdapter.PersonAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_persons,parent,false));
    }

    //binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PersonAdapterVH holder, int position)
    {

        final Person person = persons.get(position);

        holder.personName.setText(person.getName());
        holder.personName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItem.ClickedPerson(person);
            }
        });




    }


    //total number of rows and return something greater than which is persons in this case
    @Override
    public int getItemCount()
    {
        return persons.size();
    }

    //stores and recycles views as they are scrolled off screen
    public class PersonAdapterVH extends RecyclerView.ViewHolder
    {
        //textview from row_persons.xml
        TextView personName;

        PersonAdapterVH(View itemView)
        {
            super(itemView);
            personName = itemView.findViewById(R.id.personName);

        }
    }



//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.custom_item,null);
//
//        TextView txtId = view.findViewById(R.id.txtId);
//        TextView txtName = view.findViewById(R.id.txtName);
//        TextView txtFavorit = view.findViewById(R.id.txtFavorit);
//        TextView txtHairColor = view.findViewById(R.id.txtHairColor);
//        TextView txtAddress = view.findViewById(R.id.txtAddress);
//        TextView txtPhone = view.findViewById(R.id.txtPhone);
//        TextView txtNote = view.findViewById(R.id.txtNote);
//
//        Button btnUpdate = view.findViewById(R.id.btnUpdate);
//        Button btnDelete = view.findViewById(R.id.btnDelete);
//
//        Person person = persons.get(position);
//        txtId.setText(person.getId());
//        txtName.setText(person.getName());
//        txtFavorit.setText(String.valueOf(person.isFavorit()));
//        txtHairColor.setText(person.getHairColor());
//        txtAddress.setText(person.getAddress());
//        txtPhone.setText(person.getPhone());
//        txtNote.setText(person.getNote());
//
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                AlertDialog.Builder alert = new AlertDialog.Builder(context);
//                alert.setTitle("Delete person?");
//                alert.setMessage("Ønsker De at slette den ret?");
//                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton)
//                    {
//                        int posToRemove = position;
//                        Person remove = persons.remove(posToRemove);
//                        notifyDataSetChanged();
//
//                    }
//                });
//                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface dialog, int whichButton)
//                    {
//                    }
//                });
//                alert.show();
//            }
//        });
//
//
//        return view;
//    }
}
