package com.example.depressiondetection

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.depressiondetection.databinding.PatientListItemBinding

class PatientListAdapter(var context: Context,var patientList : MutableList<PatientDetails>) : RecyclerView.Adapter<PatientListAdapter.PatientListViewHolder>() {
    class PatientListViewHolder(var binding : PatientListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (patient : PatientDetails) {
            binding.patient = patient
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientListViewHolder {
        var binding = PatientListItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return PatientListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return patientList.size
    }

    override fun onBindViewHolder(holder: PatientListViewHolder, position: Int) {

        var patient = patientList[position]
        holder.bind(patient)

    }


}