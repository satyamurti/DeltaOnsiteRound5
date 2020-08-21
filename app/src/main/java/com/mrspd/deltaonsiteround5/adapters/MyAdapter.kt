package com.mrspd.deltaonsiteround5.adapters

import android.content.Context
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mrspd.deltaonsiteround5.MainActivity
import com.mrspd.deltaonsiteround5.R
import kotlinx.coroutines.*
import java.io.File
import java.util.*

class MyAdapter(var context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Default + parentJob)

    var files = ArrayList<String>()
    var files2 = ArrayList<String>()
    var files3 = ArrayList<String>()
    fun addAllFiles(file: ArrayList<String>?) {
        files.addAll(file!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    private var recyclerView2: RecyclerView? = null
    private var mMyAdapter: MyAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val files = File(files[position]).listFiles()
        getFiles3(files, holder)

        var nameOfFIle = getProperNameOfFIle(this.files[position])
        d("gghh", " 1 :---- $nameOfFIle ")
        holder.tvFileName.text = nameOfFIle
        holder.setItemClickListener(object : MainActivity.ITemClickListener {
            override fun OnClick(view: View?, position: Int) {
                Log.w("QWE", "ASD")
                if (holder.IvRight.visibility == View.GONE && holder.ivDown.visibility == View.GONE) {
                } else if (holder.IvRight.visibility == View.VISIBLE) {
                    holder.IvRight.visibility = View.GONE
                    holder.ivDown.visibility = View.VISIBLE
                    val files =
                        File(this@MyAdapter.files[position]).listFiles()
                    getFiles4(files, holder, view)

                } else {
                    holder.ivDown.visibility = View.GONE
                    holder.IvRight.visibility = View.VISIBLE
                    files2.clear()
                    mMyAdapter = MyAdapter(context)
                    mMyAdapter!!.addAllFiles(files2)
                    recyclerView2 =
                        view?.findViewById<View>(R.id.recyclerView2) as RecyclerView
                    recyclerView2.apply {
                        view.findViewById<View>(R.id.recyclerView2) as RecyclerView
                        this!!.setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = mMyAdapter

                    }

                }
            }
        })
    }
    private fun getFiles3(
        files: Array<File>?,
        holder: MyViewHolder
    ) {
        coroutineScope.async(Dispatchers.Default) {
            files3.clear()
            d("gghh","This is coroutine scope bhailog")

            if (files != null) {
                for (file in files) {
                    files3.add(file.path)
                    holder.IvRight.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun getFiles4(
        files: Array<File>?,
        holder: MyViewHolder,
        view: View?
    ) {
        coroutineScope.async(Dispatchers.Default) {
            d("gghh","This is coroutine scope bhailog")
            files2.clear()
            if (files != null) {
                for (file in files) {
                    files2.add(file.path)
                }
                withContext(Dispatchers.Main) {
                    recyclerView2 =
                        view?.findViewById<View>(R.id.recyclerView2) as RecyclerView
                    recyclerView2!!.setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    recyclerView2!!.layoutManager = layoutManager
                    mMyAdapter = MyAdapter(context)
                    mMyAdapter!!.addAllFiles(files2)
                    recyclerView2!!.adapter = mMyAdapter
                }


            }
        }
    }

    private fun getProperNameOfFIle(nameOfFIdle: String): String {
        var nameOfFIle = nameOfFIdle
        val mStringBuilder = StringBuilder(nameOfFIle)
        mStringBuilder.reverse()
        nameOfFIle = mStringBuilder.toString()
        d("gghh", "2 :---- $nameOfFIle")

        val indexOfFile = nameOfFIle.indexOf('/')
        nameOfFIle = nameOfFIle.substring(0, indexOfFile)
        d("gghh", "3:---- $nameOfFIle")

        val mStringBuilder2 = StringBuilder(nameOfFIle)
        mStringBuilder2.reverse()
        nameOfFIle = mStringBuilder2.toString()
        d("gghh", ":---- $nameOfFIle")
        return nameOfFIle
    }

    override fun getItemCount(): Int {
        return files.size
    }

    inner class MyViewHolder(itemView: View) : ViewHolder(itemView),
        View.OnClickListener {
        var tvFileName: TextView
        var IvRight: ImageView
        var ivDown: ImageView
        lateinit var ITemClickListener: MainActivity.ITemClickListener
        fun setItemClickListener(ITemClickListener: MainActivity.ITemClickListener?) {
            this.ITemClickListener = ITemClickListener!!
        }

        override fun onClick(v: View) {
            ITemClickListener.OnClick(v, adapterPosition)
        }

        init {
            tvFileName = itemView.findViewById(R.id.tvFileName)
            IvRight = itemView.findViewById(R.id.Ivright)
            ivDown = itemView.findViewById(R.id.Ivdown)
            itemView.setOnClickListener(this)
        }
    }

}