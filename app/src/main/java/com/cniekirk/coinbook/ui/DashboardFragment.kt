package com.cniekirk.coinbook.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.cniekirk.coinbook.R
import com.cniekirk.coinbook.activites.HomeActivity
import com.cniekirk.coinbook.di.Injectable
import com.cniekirk.coinbook.utils.CustomInterpolator
import com.cniekirk.coinbook.utils.Failure
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject


class DashboardFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var dashboardViewModel: DashboardViewModel

    companion object {
        fun newInstance(): DashboardFragment {
            val instance = lazy { DashboardFragment() }
            return instance.value
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dashboardViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DashboardViewModel::class.java)

        dashboardViewModel.exchanges.observe(this, Observer {  Toast.makeText(activity, "Success: ${it.keys.elementAt(0)}", Toast.LENGTH_SHORT).show() })
        dashboardViewModel.failure.observe(this, Observer {
            when(it::class) {
                // When a failure is a network error, inform the user
                Failure.NetworkConnection::class -> {

                    // Custom Snackbar implementation due to bugs in Material Components library
                    floatingSnackbar.visibility = View.VISIBLE
                    floatingSnackbarText.text = getString(R.string.internet_warning)

                    val distance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85f, resources.displayMetrics)
                    floatingSnackbar.animate().translationY(-distance)

                    val closeSnackbar = Runnable {
                        floatingSnackbar.animate().translationY(0f)
                        floatingSnackbar.visibility = View.GONE
                    }
                    Handler().postDelayed(closeSnackbar, 4000)

                }
            }

        })
        dashboardViewModel.loadExchanges()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        asset_chart.setViewPortOffsets(0f, 0f,0f,0f)
        asset_chart.setBackgroundColor(Color.TRANSPARENT)
        asset_chart.description.isEnabled = false
        asset_chart.setDrawGridBackground(false)

        asset_chart.xAxis.isEnabled = false
        asset_chart.axisLeft.isEnabled = false
        asset_chart.axisRight.isEnabled = false
        asset_chart.legend.isEnabled = false
        asset_chart.animateXY(1000, 2000)

        setData(20, 90f)

        asset_chart.invalidate()

        anim_view.setOnClickListener {

            (activity as HomeActivity).nextFragmentAnim()

        }
    }

    /**
    * Method to generate random plot data to give a feel of the UI
     * TODO: Replace with genuine database query/network request
    * */
    private fun setData(count: Int, range: Float) {

        val yVals = ArrayList<Entry>()

        for (i in 0 until count) {
            val mult = range + 1
            val value = (Math.random() * mult).toFloat() + 20// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(Entry(i.toFloat(), value))
        }

        val set1: LineDataSet

        if (asset_chart.getData() != null && asset_chart.data.dataSetCount > 0) {
            set1 = asset_chart.getData().getDataSetByIndex(0) as LineDataSet
            set1.values = yVals
            asset_chart.data.notifyDataChanged()
            asset_chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(yVals, "DataSet 1")

            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.setDrawFilled(true)
            set1.cubicIntensity = 0.2f
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            set1.setCircleColor(Color.WHITE)
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.color = Color.WHITE
            set1.fillDrawable = ContextCompat.getDrawable(activity!!, R.drawable.chart_gradient)
            set1.fillAlpha = 102
            set1.setDrawHorizontalHighlightIndicator(false)
            set1.fillFormatter = IFillFormatter { _, _ -> -10f }

            // create a data object with the datasets
            val data = LineData(set1)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            // set data
            asset_chart.data = data
        }
    }

}