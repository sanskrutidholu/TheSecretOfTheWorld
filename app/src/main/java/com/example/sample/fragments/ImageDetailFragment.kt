package com.example.sample.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sample.R
import com.example.sample.utils.CustomDialog
import com.example.sample.utils.InternetConnectionDetector
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject


class ImageDetailFragment : Fragment() {

    private var icd: InternetConnectionDetector? = null
    private val customDialog: CustomDialog? = null
    private val FETCH_URL = "https://app.webandappdevelopment.com/gallery_app/fetch_details.php"
    private var request: StringRequest? = null
    private var requestQueue: RequestQueue? = null
    private var tvTitle: TextView? = null
    private  var tvDescription:TextView? = null
    private var ivImage: ImageView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_image_detail, container, false)
        icd = InternetConnectionDetector(root.context)
        tvTitle = root.findViewById(R.id.id_title)
        ivImage = root.findViewById(R.id.id_image)
        tvDescription = root.findViewById(R.id.id_description)
        loadImages()
//
////        TextView tvTitle = root.findViewById(R.id.id_title);
////        tvTitle.setText(mParam1);
//        ImageView ivImage = root.findViewById(R.id.id_image);
////        ivImage.setImageURI(Uri.parse(mParam2));
//        new ImageLoad(mParam2,ivImage).execute();
//        TextView tvDescription = root.findViewById(R.id.id_description);
//        tvDescription.setText(mParam3);
        //
////        TextView tvTitle = root.findViewById(R.id.id_title);
////        tvTitle.setText(mParam1);
//        ImageView ivImage = root.findViewById(R.id.id_image);
////        ivImage.setImageURI(Uri.parse(mParam2));
//        new ImageLoad(mParam2,ivImage).execute();
//        TextView tvDescription = root.findViewById(R.id.id_description);
//        tvDescription.setText(mParam3);
        return root
    }

    fun loadImages() {
        if (icd!!.isConnected) {
            requestQueue = Volley.newRequestQueue(context)
            requestQueue!!.cache.clear()
            request = StringRequest(
                Request.Method.POST, FETCH_URL,
                { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        if (jsonObject.names()[0] == "img_id") {
                            tvTitle!!.text = jsonObject.getString("img_title")
                            tvDescription!!.text = jsonObject.getString("img_description")
                            Picasso.get().load(jsonObject.getString("img_url"))
                                .placeholder(R.mipmap.ic_launcher)
                                .into(ivImage)
                        }


                        //                        JSONArray array = new JSONArray(response);
                        //                        idArray = new int[array.length()];
                        //                        imageArray = new String[array.length()];
                        //                        titleArray = new String[array.length()];
                        //                        descriptionArray = new String[array.length()];
                        //                        JSONObject object = array.getJSONObject(x);

                        //                        for (int x = 0 ; x<array.length(); x++){
                        //                            JSONObject object = array.getJSONObject(x);
                        //                            idArray[x] = object.getInt("img_id");
                        //                            imageArray[x] = object.getString("img_url");
                        //                            titleArray[x] = object.getString("img_title");
                        //                            descriptionArray[x] = object.getString("description");
                        //                        }
                        //                        listItem = new ArrayList<>();
                        //                        for (int x=0 ; x<array.length() ; x++){
                        //                            ImageDetails item = new ImageDetails(idArray[x],imageArray[x],"0");
                        //                            listItem.add(item);
                        //                        }
                        //                        adapter = new ImageListAdapter(getActivity(), listItem);
                        //                        rvImagesList.setAdapter(adapter);
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(
                            activity,
                            "Unable to load data. Please try again.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            ) { customDialog!!.serverErrorDialog(activity) }
            requestQueue!!.add(request)
        } else {
            customDialog!!.connectionErrorDialog(activity)
        }
    }


}