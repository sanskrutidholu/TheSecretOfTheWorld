package com.example.sample.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sample.R
import com.example.sample.adapter.ImageListAdapter
import com.example.sample.modelClasses.ImageDetails
import com.example.sample.utils.CustomDialog
import com.example.sample.utils.InternetConnectionDetector
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class GalleryFragment : Fragment() {

    private lateinit var rvImagesList: RecyclerView
    private lateinit var adapter: ImageListAdapter
    private lateinit var listItem: ArrayList<ImageDetails>

    private lateinit var icd: InternetConnectionDetector
    private lateinit var customDialog: CustomDialog

    private val FETCH_URL = "https://app.webandappdevelopment.com/gallery_app/fetch_images.php"
    private lateinit var request: StringRequest
    private lateinit var requestQueue: RequestQueue

    //    private ImageListAdapter.ItemClickListner clickListner;
    private lateinit var idArray: IntArray
    private lateinit var imageArray: Array<String?>

    //    private String titleArray[];
    //    private String descriptionArray[];

    var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_gallery, container, false)

        icd = InternetConnectionDetector(root!!.context)
        customDialog = CustomDialog(root!!.context)

        val gridLayoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        rvImagesList = root!!.findViewById(R.id.gf_rl_image_list)
        rvImagesList.setHasFixedSize(true)
        rvImagesList.layoutManager = gridLayoutManager

        //        clickListner = this;
        loadImages()

        return root
    }

    private fun loadImages() {
        if (icd.isConnected) {
            requestQueue = Volley.newRequestQueue(context)
            requestQueue.cache.clear()
            request = StringRequest(
                Request.Method.POST, FETCH_URL,
                { response ->
                    try {
                        val array = JSONArray(response)
                        idArray = IntArray(array.length())
                        imageArray = arrayOfNulls(array.length())
                        //                        titleArray = new String[array.length()];
                        //                        descriptionArray = new String[array.length()];
                        for (x in 0 until array.length()) {
                            val `object` = array.getJSONObject(x)
                            idArray[x] = `object`.getInt("img_id")
                            imageArray[x] = `object`.getString("img_url")
                            //                            titleArray[x] = object.getString("img_title");
                            //                            descriptionArray[x] = object.getString("description");
                        }
                        listItem = ArrayList()
                        for (x in 0 until array.length()) {
                            val item = ImageDetails(idArray[x], imageArray[x], "0")
                            listItem.add(item)
                        }
                        adapter = ImageListAdapter(requireActivity(), listItem)
                        rvImagesList.adapter = adapter
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(
                            activity,
                            "Unable to load data. Please try again.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            ) { customDialog.serverErrorDialog(activity) }
            requestQueue.add(request)
        } else {
            customDialog.connectionErrorDialog(activity)
        }
    }

//    @Override
//    public void onItemClick(ImageDetails imageDetails) {
//        Fragment fragment = ImageDetailFragment.newInstance(imageDetails.getImageUrl(),imageDetails.getDescription());
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.galleryFragmentContainer, fragment, "detail_fragment");
////        transaction.hide(getActivity().getSupportFragmentManager().findFragmentById("nav_host_fragment"));
////        transaction.add(R.id.nav_host_fragment, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//
//
//
//        Fragment newFragment = ImageDetailFragment.newInstance(imageDetails.getTitle());
//        FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
//
//// Replace whatever is in the fragment_container view with this fragment,
//// and add the transaction to the back stack if needed
//        trans.replace(R.id.galleryFragmentContainer, newFragment,"detail_fragment");
//        trans.addToBackStack(null);
//
//// Commit the transaction
//        trans.commit();
//    }


}